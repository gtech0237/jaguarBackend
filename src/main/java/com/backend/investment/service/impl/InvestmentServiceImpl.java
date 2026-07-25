package com.backend.investment.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.investment.Exception.BadRequestException;
import com.backend.investment.Exception.ResourceNotFoundException;
import com.backend.investment.dto.InvestmentRequestDto;
import com.backend.investment.dto.InvestmentResponseDto;
import com.backend.investment.entity.Product;
import com.backend.investment.entity.User;
import com.backend.investment.entity.UserInvestment;
import com.backend.investment.entity.WalletTransaction;
import com.backend.investment.repository.ProductRepository;
import com.backend.investment.repository.UserInvestmentRepository;
import com.backend.investment.repository.UserRepository;
import com.backend.investment.repository.WalletTransactionRepository;
import com.backend.investment.service.InvestmentService;
import com.backend.investment.constants.CurrencyUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final UserInvestmentRepository investmentRepository;

    private final WalletTransactionRepository walletRepository;

    @Override
    @Transactional
    public InvestmentResponseDto invest(InvestmentRequestDto request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Investment",
                                "UserId",
                                request.getUserId().toString()
                        ));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Investment",
                                "ProductId",
                                request.getProductId().toString()
                        ));

        if (!"ACTIVE".equalsIgnoreCase(product.getStatus())) {
            throw new BadRequestException("This investment plan is not available.");
        }

        /*
         * Product price is stored in INR.
         * Convert to USDT before deducting wallet.
         */
        BigDecimal investmentUsdt =
                CurrencyUtil.inrToUsdt(product.getInvestmentAmount());

        if (user.getBalance().compareTo(investmentUsdt) < 0) {

            throw new BadRequestException(
                    "Insufficient wallet balance."
            );

        }

        BigDecimal openingBalance = user.getBalance();

        BigDecimal closingBalance =
                openingBalance.subtract(investmentUsdt);

        user.setBalance(closingBalance);

        userRepository.save(user);


        UserInvestment investment = new UserInvestment();

        investment.setUser(user);

        investment.setProduct(product);

        investment.setInvestmentAmount(
                product.getInvestmentAmount()
        );

        investment.setDailyIncome(
                product.getDailyIncome()
        );

        investment.setDurationDays(
                product.getDurationDays()
        );

        LocalDate today = LocalDate.now();

        investment.setStartDate(today);

        investment.setEndDate(
                today.plusDays(product.getDurationDays())
        );

        investment.setStatus("ACTIVE");

        investment = investmentRepository.save(investment);

        /*
         * Wallet Transaction
         * Wallet always stores USDT.
         */

        WalletTransaction wallet = new WalletTransaction();

        wallet.setUser(user);

        wallet.setTransactionType("INVESTMENT");

        wallet.setAmount(investmentUsdt);

        wallet.setOpeningBalance(openingBalance);

        wallet.setClosingBalance(closingBalance);

        wallet.setReferenceId(investment.getId());

        wallet.setReferenceType("INVESTMENT");

        wallet.setRemarks(
                "Investment Purchase ("
                        + product.getInvestmentAmount()
                        + " INR = "
                        + investmentUsdt
                        + " USDT)"
        );

        walletRepository.save(wallet);

        InvestmentResponseDto dto =
                new InvestmentResponseDto();

        dto.setInvestmentId(investment.getId());

        dto.setUserId(user.getId());

        dto.setProductId(product.getId());

        dto.setProductName(product.getProductName());

        /*
         * Return INR to frontend.
         */

        dto.setInvestmentAmount(
                product.getInvestmentAmount()
        );

        dto.setDailyIncome(
                product.getDailyIncome()
        );

        dto.setDurationDays(
                product.getDurationDays()
        );

        dto.setStartDate(
                investment.getStartDate()
        );

        dto.setEndDate(
                investment.getEndDate()
        );

        dto.setStatus(
                investment.getStatus()
        );

        return dto;

    }

    @Override
    public List<InvestmentResponseDto> myInvestments(Long userId) {

        return investmentRepository
                .findByUserId(userId)
                .stream()
                .map(investment -> {

                    InvestmentResponseDto dto =
                            new InvestmentResponseDto();

                    dto.setInvestmentId(investment.getId());

                    dto.setUserId(
                            investment.getUser().getId()
                    );

                    dto.setProductId(
                            investment.getProduct().getId()
                    );

                    dto.setProductName(
                            investment.getProduct().getProductName()
                    );

                    /*
                     * Investment remains INR.
                     */

                    dto.setInvestmentAmount(
                            investment.getInvestmentAmount()
                    );

                    dto.setDailyIncome(
                            investment.getDailyIncome()
                    );

                    dto.setDurationDays(
                            investment.getDurationDays()
                    );

                    dto.setStartDate(
                            investment.getStartDate()
                    );

                    dto.setEndDate(
                            investment.getEndDate()
                    );

                    dto.setStatus(
                            investment.getStatus()
                    );

                    return dto;

                }).toList();

    }

}