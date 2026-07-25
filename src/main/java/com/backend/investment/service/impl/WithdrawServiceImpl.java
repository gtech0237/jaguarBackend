package com.backend.investment.service.impl;

import com.backend.investment.constants.CurrencyUtil;
import com.backend.investment.dto.WithdrawRequestDto;
import com.backend.investment.dto.WithdrawResponseDto;
import com.backend.investment.entity.User;
import com.backend.investment.entity.WithdrawHistory;
import com.backend.investment.repository.UserRepository;
import com.backend.investment.repository.WithdrawRepository;
import com.backend.investment.service.IWithdrawService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WithdrawServiceImpl implements IWithdrawService {

    private final WithdrawRepository withdrawRepository;
    private final UserRepository userRepository;
    private final CurrencyUtil currencyUtil;

    @Override
    @Transactional
    public WithdrawResponseDto requestWithdraw(WithdrawRequestDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (request.getAmount() == null
                || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Invalid withdraw amount.");

        }

        BigDecimal requestedUsdt;
        BigDecimal requestedInr;

        String paymentMethod;
        String accountDetails;
        String currency;

        BigDecimal serviceFee;
        BigDecimal payableAmount;

        if ("ACCOUNT".equalsIgnoreCase(
                request.getWithdrawType())) {

            if (user.getAccountHolderName() == null
                    || user.getBankName() == null
                    || user.getAccountNumber() == null
                    || user.getIfscCode() == null) {

                throw new RuntimeException(
                        "Please add your bank account details first.");

            }



            requestedInr = request.getAmount();
            requestedUsdt =
                    currencyUtil.inrToUsdt(requestedInr);

            // Wallet is stored in USDT
            if (user.getBalance()
                    .compareTo(requestedUsdt) < 0) {

                throw new RuntimeException(
                        "Insufficient wallet balance.");

            }

            paymentMethod = "BANK";

            currency = "INR";

            accountDetails =
                    "Holder : " + user.getAccountHolderName()
                            + ", Bank : " + user.getBankName()
                            + ", Account : " + user.getAccountNumber()
                            + ", IFSC : " + user.getIfscCode();

            serviceFee =
                    requestedInr.multiply(
                            BigDecimal.valueOf(0.10));

            payableAmount =
                    requestedInr.subtract(serviceFee);

        }

        else if ("MANUAL".equalsIgnoreCase(
                request.getWithdrawType())) {

            if (request.getPaymentMethod() == null
                    || request.getPaymentMethod().isBlank()) {

                throw new RuntimeException(
                        "Please select payment method.");

            }

            if (request.getAccountDetails() == null
                    || request.getAccountDetails().isBlank()) {

                throw new RuntimeException(
                        "Please enter account details.");

            }

            paymentMethod =
                    request.getPaymentMethod();

            accountDetails =
                    request.getAccountDetails();

            if ("UPI".equalsIgnoreCase(
                    paymentMethod)) {

                requestedInr =
                        request.getAmount();

                requestedUsdt =
                        currencyUtil.inrToUsdt(
                                requestedInr);

                if (user.getBalance()
                        .compareTo(requestedUsdt) < 0) {

                    throw new RuntimeException(
                            "Insufficient wallet balance.");

                }

                currency = "INR";

                serviceFee =
                        requestedInr.multiply(
                                BigDecimal.valueOf(0.10));

                payableAmount =
                        requestedInr.subtract(
                                serviceFee);

            }

            else if ("USDT".equalsIgnoreCase(
                    paymentMethod)) {

                requestedUsdt =
                        request.getAmount();

                if (user.getBalance()
                        .compareTo(requestedUsdt) < 0) {

                    throw new RuntimeException(
                            "Insufficient wallet balance.");

                }

                requestedInr =
                        currencyUtil.usdtToInr(
                                requestedUsdt);

                currency = "USDT";

                serviceFee =
                        requestedUsdt.multiply(
                                BigDecimal.valueOf(0.10));

                payableAmount =
                        requestedUsdt.subtract(
                                serviceFee);

            }

            else {

                throw new RuntimeException(
                        "Invalid payment method.");

            }
        }

        else {

            throw new RuntimeException(
                    "Invalid withdraw type.");

        }

        WithdrawHistory withdraw = WithdrawHistory.builder()

                .user(user)

                // Amount deducted from wallet (USDT)
                .requestedAmountUsdt(requestedUsdt)

                // Equivalent INR
                .requestedAmountInr(requestedInr)

                // Fee (INR for Bank/UPI, USDT for Crypto)
                .serviceFee(serviceFee)

                // Final payable amount
                .payableAmount(payableAmount)

                // INR / USDT
                .currency(currency)

                // ACCOUNT / MANUAL
                .withdrawType(request.getWithdrawType())

                // BANK / UPI / USDT
                .paymentMethod(paymentMethod)

                // Bank Details / Wallet Address / UPI
                .accountDetails(accountDetails)

                .status("PENDING")

                .createdOn(LocalDateTime.now())

                .build();

        withdrawRepository.save(withdraw);

        return WithdrawResponseDto.builder()

                .id(withdraw.getId())

                .requestedAmountUsdt(
                        withdraw.getRequestedAmountUsdt())

                .requestedAmountInr(
                        withdraw.getRequestedAmountInr())

                .serviceFee(
                        withdraw.getServiceFee())

                .payableAmount(
                        withdraw.getPayableAmount())

                .currency(
                        withdraw.getCurrency())

                .withdrawType(
                        withdraw.getWithdrawType())

                .paymentMethod(
                        withdraw.getPaymentMethod())

                .accountDetails(
                        withdraw.getAccountDetails())

                .status(
                        withdraw.getStatus())

                .remarks(
                        withdraw.getRemarks())

                .createdOn(
                        withdraw.getCreatedOn())

                .message(
                        "Withdraw request submitted successfully.")

                .build();

    }

    @Override
    public List<WithdrawResponseDto> withdrawHistory(Long userId) {
        return withdrawRepository

                .findByUserIdOrderByCreatedOnDesc(userId)

                .stream()

                .map(withdraw ->

                        WithdrawResponseDto.builder()

                                .id(
                                        withdraw.getId())

                                .requestedAmountUsdt(
                                        withdraw.getRequestedAmountUsdt())

                                .requestedAmountInr(
                                        withdraw.getRequestedAmountInr())

                                .serviceFee(
                                        withdraw.getServiceFee())

                                .payableAmount(
                                        withdraw.getPayableAmount())

                                .currency(
                                        withdraw.getCurrency())

                                .withdrawType(
                                        withdraw.getWithdrawType())

                                .paymentMethod(
                                        withdraw.getPaymentMethod())

                                .accountDetails(
                                        withdraw.getAccountDetails())

                                .status(
                                        withdraw.getStatus())

                                .remarks(
                                        withdraw.getRemarks())

                                .createdOn(
                                        withdraw.getCreatedOn())

                                .build()

                )

                .toList();

    }

}