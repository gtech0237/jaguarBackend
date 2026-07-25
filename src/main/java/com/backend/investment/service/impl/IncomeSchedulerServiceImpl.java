package com.backend.investment.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.investment.entity.IncomeHistory;
import com.backend.investment.entity.User;
import com.backend.investment.entity.UserInvestment;
import com.backend.investment.entity.WalletTransaction;
import com.backend.investment.repository.IncomeHistoryRepository;
import com.backend.investment.repository.UserInvestmentRepository;
import com.backend.investment.repository.UserRepository;
import com.backend.investment.repository.WalletTransactionRepository;
import com.backend.investment.service.IIncomeSchedulerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeSchedulerServiceImpl
        implements IIncomeSchedulerService {

    private final UserInvestmentRepository investmentRepository;

    private final IncomeHistoryRepository incomeRepository;

    private final UserRepository userRepository;

    private final WalletTransactionRepository walletRepository;

    @Override
    @Transactional
    @Scheduled(cron = "0 1 0 * * *") // Every day at 12:01 AM
    public void generateDailyIncome() {

        List<UserInvestment> investments = investmentRepository.findByStatus("ACTIVE");

        LocalDate today = LocalDate.now();

        for (UserInvestment investment : investments) {
            if (investment.getStartDate().isEqual(today)) {
                continue;
            }
            if (today.isAfter(investment.getEndDate())) {
                investment.setStatus("COMPLETED");
                investmentRepository.save(investment);
                continue;
            }
            if (incomeRepository.existsByInvestmentIdAndIncomeDate(
                    investment.getId(),
                    today)) {

                continue;
            }

            User user = investment.getUser();

            BigDecimal dailyIncome = investment.getDailyIncome();
            if (user.getTotalIncome() == null) {
                user.setTotalIncome(BigDecimal.ZERO);
            }

            if (investment.getTotalIncomeGenerated() == null) {
                investment.setTotalIncomeGenerated(BigDecimal.ZERO);
            }

            user.setTotalIncome(user.getTotalIncome().add(dailyIncome));
            userRepository.save(user);
            investment.setTotalIncomeGenerated(investment.getTotalIncomeGenerated().add(dailyIncome));
            investmentRepository.save(investment);
            IncomeHistory history = new IncomeHistory();
            history.setInvestment(investment);
            history.setUser(user);
            history.setIncomeAmount(dailyIncome);
            history.setIncomeDate(today);
            history.setRemarks("Daily Income Credited (INR)");
            history = incomeRepository.save(history);

            WalletTransaction wallet = new WalletTransaction();
            wallet.setUser(user);
            wallet.setTransactionType("DAILY_INCOME");
            wallet.setAmount(dailyIncome);

            wallet.setOpeningBalance(user.getBalance());
            wallet.setClosingBalance(user.getBalance());
            wallet.setReferenceId(history.getId());
            wallet.setReferenceType("INCOME");
            wallet.setRemarks("Daily Income Generated (INR). Wallet unchanged.");
            walletRepository.save(wallet);
        }
    }
}