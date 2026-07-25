package com.backend.investment.service.impl;

import com.backend.investment.Exception.ResourceNotFoundException;
import com.backend.investment.constants.CurrencyUtil;
import com.backend.investment.dto.*;
import com.backend.investment.entity.*;
import com.backend.investment.repository.*;
import com.backend.investment.service.IAdminService;
import com.backend.investment.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    private final PaymentRequestRepository paymentRequestRepository;
    private final UserRepository userRepository;
    private final RechargeRepository rechargeRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final WithdrawRepository withdrawRepository;
    private final CurrencyUtil currencyUtil;
    private final UserInvestmentRepository userInvestmentRepository;



    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {

        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin",
                                "username",
                                request.getUsername()));

        if (!encoder.matches(
                request.getPassword(),
                admin.getPassword())) {

            throw new RuntimeException("Invalid Password");
        }

        AdminLoginResponse response =
                new AdminLoginResponse();

        response.setToken(
                jwtService.generateToken(
                        admin.getUsername(),
                        "ADMIN"));

        return response;
    }


    @Override
    public List<PendingRechargeDto> getPendingRechargeRequests() {

        return paymentRequestRepository.findByStatus("PENDING")
                .stream()
                .map(request -> {
                    User user = userRepository.findById(request.getUserId())
                            .orElseThrow(() ->
                                    new RuntimeException("User not found"));
                    PendingRechargeDto dto = new PendingRechargeDto();
                    dto.setId(request.getId());
                    dto.setUserId(request.getUserId());
                    dto.setPhone(user.getPhone());
                    dto.setAmount(request.getAmount());
                    dto.setTransactionId(request.getTransactionId());
                    dto.setPaymentMethod(request.getPaymentMethod());
                    dto.setStatus(request.getStatus());
                    dto.setCreatedOn(request.getCreatedOn());
                    return dto;
                })
                .toList();
    }

    @Override
    @Transactional
    public String approveRecharge(Long paymentRequestId) {

        PaymentRequest paymentRequest =
                paymentRequestRepository.findById(paymentRequestId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "PaymentRequest",
                                        "id",
                                        paymentRequestId.toString()));

        if (!"PENDING".equals(paymentRequest.getStatus())) {
            throw new RuntimeException(
                    "Recharge already processed.");
        }

        User user =
                userRepository.findById(paymentRequest.getUserId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User",
                                        "id",
                                        paymentRequest.getUserId().toString()));



        BigDecimal rechargeUsdt =
                paymentRequest.getAmount();

        BigDecimal rechargeInr =
                currencyUtil.usdtToInr(rechargeUsdt);



        BigDecimal openingBalance =
                user.getBalance() == null
                        ? BigDecimal.ZERO
                        : user.getBalance();

        BigDecimal closingBalance =
                openingBalance.add(rechargeUsdt);

        user.setBalance(closingBalance);



        BigDecimal totalRecharge =
                user.getTotalRecharge() == null
                        ? BigDecimal.ZERO
                        : user.getTotalRecharge();

        user.setTotalRecharge(totalRecharge.add(rechargeInr));

        userRepository.save(user);



        RechargeHistory recharge =
                new RechargeHistory();

        recharge.setUser(user);

        recharge.setAmount(rechargeUsdt);

        recharge.setPaymentMethod(
                paymentRequest.getPaymentMethod());

        recharge.setTransactionId(
                paymentRequest.getTransactionId());

        recharge.setStatus("SUCCESS");

        rechargeRepository.save(recharge);
        WalletTransaction wallet =
                new WalletTransaction();

        wallet.setUser(user);

        wallet.setTransactionType("RECHARGE");

        wallet.setAmount(rechargeUsdt);

        wallet.setOpeningBalance(openingBalance);

        wallet.setClosingBalance(closingBalance);

        wallet.setReferenceId(recharge.getId());

        wallet.setReferenceType("RECHARGE");

        wallet.setRemarks(
                "Recharge Approved : "
                        + rechargeUsdt
                        + " USDT (₹"
                        + rechargeInr
                        + ")");

        walletTransactionRepository.save(wallet);



        paymentRequest.setStatus("SUCCESS");

        paymentRequest.setVerifiedOn(
                LocalDateTime.now());

        paymentRequestRepository.save(paymentRequest);

        return "Recharge Approved Successfully";
    }

    @Override
    @Transactional
    public String rejectRecharge(Long id) {

        PaymentRequest request =
                paymentRequestRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "PaymentRequest",
                                        "id",
                                        id.toString()));

        if (!"PENDING".equals(request.getStatus())) {
            throw new RuntimeException(
                    "Already processed.");
        }

        request.setStatus("REJECTED");

        request.setAdminRemarks(
                "Rejected By Admin");

        request.setVerifiedOn(
                LocalDateTime.now());

        paymentRequestRepository.save(request);

        return "Recharge Rejected Successfully";
    }



    @Override
    public List<PendingWithdrawDto> getPendingWithdrawRequests() {

        return withdrawRepository.findByStatus("PENDING")
                .stream()
                .map(withdraw -> {
                    User user = userRepository.findById(withdraw.getUser().getId())
                            .orElseThrow(() ->
                                    new RuntimeException("User not found"));

                    PendingWithdrawDto dto = new PendingWithdrawDto();
                    dto.setId(withdraw.getId());
                    dto.setUserId(withdraw.getUser().getId());
                    dto.setPhone(user.getPhone());
                    dto.setRequestedAmountUsdt(
                            withdraw.getRequestedAmountUsdt());
                    dto.setRequestedAmountInr(
                            withdraw.getRequestedAmountInr());
                    dto.setServiceFee(
                            withdraw.getServiceFee());
                    dto.setPayableAmount(
                            withdraw.getPayableAmount());
                    dto.setCurrency(
                            withdraw.getCurrency());
                    dto.setWithdrawType(
                            withdraw.getWithdrawType());
                    dto.setPaymentMethod(
                            withdraw.getPaymentMethod());
                    dto.setAccountDetails(
                            withdraw.getAccountDetails());
                    dto.setStatus(
                            withdraw.getStatus());

                    dto.setCreatedOn(
                            withdraw.getCreatedOn());
                    return dto;
                })
                .toList();
    }

    @Override
    @Transactional
    public String approveWithdraw(Long withdrawId) {

        WithdrawHistory withdraw =
                withdrawRepository.findById(withdrawId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Withdraw",
                                        "id",
                                        withdrawId.toString()));

        if (!"PENDING".equals(withdraw.getStatus())) {

            throw new RuntimeException(
                    "Withdraw already processed.");

        }

        User user = withdraw.getUser();



        BigDecimal openingBalance =
                user.getBalance() == null
                        ? BigDecimal.ZERO
                        : user.getBalance();

        BigDecimal requestedUsdt =
                withdraw.getRequestedAmountUsdt();

        if (openingBalance.compareTo(requestedUsdt) < 0) {

            throw new RuntimeException(
                    "Insufficient Wallet Balance");

        }

        BigDecimal closingBalance =
                openingBalance.subtract(requestedUsdt);



        user.setBalance(closingBalance);

        BigDecimal totalWithdraw =
                user.getTotalWithdraw() == null
                        ? BigDecimal.ZERO
                        : user.getTotalWithdraw();

        // Store total withdraw in INR
        user.setTotalWithdraw(
                totalWithdraw.add(
                        withdraw.getRequestedAmountInr()
                ));

        userRepository.save(user);



        WalletTransaction wallet =
                new WalletTransaction();

        wallet.setUser(user);

        wallet.setTransactionType("WITHDRAW");

        wallet.setAmount(requestedUsdt);

        wallet.setOpeningBalance(openingBalance);

        wallet.setClosingBalance(closingBalance);

        wallet.setReferenceId(withdraw.getId());

        wallet.setReferenceType("WITHDRAW");

        wallet.setRemarks(

                "Withdraw Approved | "

                        + requestedUsdt

                        + " USDT (₹"

                        + withdraw.getRequestedAmountInr()

                        + ") -> "

                        + withdraw.getPayableAmount()

                        + " "

                        + withdraw.getCurrency()

        );

        walletTransactionRepository.save(wallet);

        withdraw.setStatus("APPROVED");

        withdraw.setApprovedOn(LocalDateTime.now());

        withdraw.setRemarks(

                "Approved. Pay "

                        + withdraw.getPayableAmount()

                        + " "

                        + withdraw.getCurrency()

                        + " to user."

        );

        withdrawRepository.save(withdraw);

        return "Withdraw Approved Successfully";

    }

    @Override
    @Transactional
    public String rejectWithdraw(Long withdrawId) {

        WithdrawHistory withdraw =
                withdrawRepository.findById(withdrawId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Withdraw",
                                        "id",
                                        withdrawId.toString()));

        if (!"PENDING".equals(withdraw.getStatus())) {
            throw new RuntimeException(
                    "Withdraw already processed.");
        }

        withdraw.setStatus("REJECTED");

        withdraw.setApprovedOn(
                LocalDateTime.now());

        withdraw.setRemarks(
                "Rejected By Admin");

        withdrawRepository.save(withdraw);

        return "Withdraw Rejected Successfully";
    }

    @Override
    public List<DailyIncomeDetailsDto> getDailyIncomeDetails() {

        return userInvestmentRepository.findByStatus("ACTIVE")
                .stream()
                .map(investment -> {

                    User user = investment.getUser();

                    Product product = investment.getProduct();

                    Plan plan = product.getPlan();


                    LocalDate startDate = investment.getStartDate();

                    LocalDate endDate = investment.getEndDate();


                    int completedDays = 0;

                    int daysRemaining =
                            investment.getDurationDays();


                    if (startDate != null) {

                        completedDays =
                                (int) ChronoUnit.DAYS.between(
                                        startDate,
                                        LocalDate.now()
                                );


                        if (completedDays < 0) {
                            completedDays = 0;
                        }


                        if (completedDays >
                                investment.getDurationDays()) {

                            completedDays =
                                    investment.getDurationDays();

                        }


                        daysRemaining =
                                investment.getDurationDays()
                                        - completedDays;


                        if (daysRemaining < 0) {
                            daysRemaining = 0;
                        }

                    }



                    return DailyIncomeDetailsDto.builder()

                            .investmentId(
                                    investment.getId()
                            )

                            .userId(
                                    user.getId()
                            )

                            .phone(
                                    user.getPhone()
                            )

                            .planName(
                                    plan.getPlanName()
                            )

                            .productName(
                                    product.getProductName()
                            )

                            .investmentAmount(
                                    investment.getInvestmentAmount()
                            )

                            .dailyIncome(
                                    investment.getDailyIncome()
                            )

                            .durationDays(
                                    investment.getDurationDays()
                            )

                            .purchaseDate(
                                    startDate
                            )

                            .endDate(
                                    endDate
                            )

                            .completedDays(
                                    completedDays
                            )

                            .daysRemaining(
                                    daysRemaining
                            )

                            .availableBalance(
                                    user.getBalance()
                            )

                            .totalIncomePaid(
                                    investment.getTotalIncomeGenerated()
                            )

                            .status(
                                    investment.getStatus()
                            )

                            .build();

                })
                .toList();
    }


}