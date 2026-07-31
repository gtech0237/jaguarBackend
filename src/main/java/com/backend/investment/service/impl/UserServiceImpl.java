package com.backend.investment.service.impl;

import com.backend.investment.Exception.ResourceNotFoundException;
import com.backend.investment.constants.CurrencyUtil;
import com.backend.investment.dto.*;
import com.backend.investment.entity.User;
import com.backend.investment.entity.UserInvestment;
import com.backend.investment.mapper.UserMapper;
import com.backend.investment.repository.UserInvestmentRepository;
import com.backend.investment.repository.UserRepository;
import com.backend.investment.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserInvestmentRepository userInvestmentRepository;

    @Override
    public UserResponseDto getLoggedInUser(String phone) {

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "phone",
                                phone
                        )
                );

        return UserMapper.userToUserResponseDto(user);
    }
    @Override
    public UserResponseDto saveBankAccount(String phone,
                                           BankAccountDto dto) {

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "phone",
                                phone
                        ));

        user.setAccountHolderName(dto.getAccountHolderName());

        user.setBankName(dto.getBankName());

        user.setAccountNumber(dto.getAccountNumber());

        user.setIfscCode(dto.getIfscCode());

        userRepository.save(user);

        return UserMapper.userToUserResponseDto(user);

    }
    @Override
    public TeamResponseDto getMyTeam(String phone) {

        /*
         * =========================================================
         * 1. FIND LOGGED-IN USER
         * =========================================================
         */

        User loggedInUser =
                userRepository.findByPhone(phone)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User",
                                        "phone",
                                        phone
                                )
                        );


        /*
         * =========================================================
         * 2. LEVEL 1 USERS
         * =========================================================
         */

        List<User> level1Users =
                userRepository.findByReferrerId(
                        loggedInUser.getId()
                );


        /*
         * =========================================================
         * 3. LEVEL 2 USERS
         * =========================================================
         */

        List<User> level2Users =
                new java.util.ArrayList<>();


        for (User level1User : level1Users) {

            level2Users.addAll(
                    userRepository.findByReferrerId(
                            level1User.getId()
                    )
            );
        }


        /*
         * =========================================================
         * 4. LEVEL 3 USERS
         * =========================================================
         */

        List<User> level3Users =
                new java.util.ArrayList<>();


        for (User level2User : level2Users) {

            level3Users.addAll(
                    userRepository.findByReferrerId(
                            level2User.getId()
                    )
            );
        }


        /*
         * =========================================================
         * 5. COMMISSION RATES
         * =========================================================
         */

        BigDecimal level1Rate =
                new BigDecimal("0.15");

        BigDecimal level2Rate =
                new BigDecimal("0.02");

        BigDecimal level3Rate =
                new BigDecimal("0.01");


        /*
         * =========================================================
         * 6. LEVEL 1 STATISTICS
         * =========================================================
         */

        BigDecimal level1Recharge =
                BigDecimal.ZERO;

        BigDecimal level1Investment =
                BigDecimal.ZERO;

        long level1ActiveMembers = 0;


        for (User user : level1Users) {

            if (user.getStatus() != null
                    && user.getStatus()
                    .equalsIgnoreCase("ACTIVE")) {

                level1ActiveMembers++;
            }


            /*
             * Recharge is still displayed as a statistic.
             * It is NOT used for commission.
             */

            if (user.getTotalRecharge() != null) {

                level1Recharge =
                        level1Recharge.add(
                                user.getTotalRecharge()
                        );
            }


            /*
             * Investment
             */

            level1Investment =
                    level1Investment.add(
                            getUserTotalInvestment(
                                    user.getId()
                            )
                    );
        }


        /*
         * =========================================================
         * LEVEL 1 COMMISSION
         *
         * INVESTMENT ONLY
         *
         * 15%
         * =========================================================
         */

        BigDecimal level1Commission =
                level1Investment
                        .multiply(level1Rate)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );


        /*
         * =========================================================
         * 7. LEVEL 2 STATISTICS
         * =========================================================
         */

        BigDecimal level2Recharge =
                BigDecimal.ZERO;

        BigDecimal level2Investment =
                BigDecimal.ZERO;

        long level2ActiveMembers = 0;


        for (User user : level2Users) {

            if (user.getStatus() != null
                    && user.getStatus()
                    .equalsIgnoreCase("ACTIVE")) {

                level2ActiveMembers++;
            }


            /*
             * Recharge is only displayed.
             */

            if (user.getTotalRecharge() != null) {

                level2Recharge =
                        level2Recharge.add(
                                user.getTotalRecharge()
                        );
            }


            /*
             * Investment
             */

            level2Investment =
                    level2Investment.add(
                            getUserTotalInvestment(
                                    user.getId()
                            )
                    );
        }


        /*
         * =========================================================
         * LEVEL 2 COMMISSION
         *
         * INVESTMENT ONLY
         *
         * 2%
         * =========================================================
         */

        BigDecimal level2Commission =
                level2Investment
                        .multiply(level2Rate)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );


        /*
         * =========================================================
         * 8. LEVEL 3 STATISTICS
         * =========================================================
         */

        BigDecimal level3Recharge =
                BigDecimal.ZERO;

        BigDecimal level3Investment =
                BigDecimal.ZERO;

        long level3ActiveMembers = 0;


        for (User user : level3Users) {

            if (user.getStatus() != null
                    && user.getStatus()
                    .equalsIgnoreCase("ACTIVE")) {

                level3ActiveMembers++;
            }


            /*
             * Recharge is only displayed.
             */

            if (user.getTotalRecharge() != null) {

                level3Recharge =
                        level3Recharge.add(
                                user.getTotalRecharge()
                        );
            }


            /*
             * Investment
             */

            level3Investment =
                    level3Investment.add(
                            getUserTotalInvestment(
                                    user.getId()
                            )
                    );
        }


        /*
         * =========================================================
         * LEVEL 3 COMMISSION
         *
         * INVESTMENT ONLY
         *
         * 1%
         * =========================================================
         */

        BigDecimal level3Commission =
                level3Investment
                        .multiply(level3Rate)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );


        /*
         * =========================================================
         * 9. OVERALL STATISTICS
         * =========================================================
         */

        long totalMembers =
                level1Users.size()
                        + level2Users.size()
                        + level3Users.size();


        long activeMembers =
                level1ActiveMembers
                        + level2ActiveMembers
                        + level3ActiveMembers;


        BigDecimal totalRecharge =
                level1Recharge
                        .add(level2Recharge)
                        .add(level3Recharge);


        BigDecimal totalInvestment =
                level1Investment
                        .add(level2Investment)
                        .add(level3Investment);


        BigDecimal totalCommission =
                level1Commission
                        .add(level2Commission)
                        .add(level3Commission);


        /*
         * =========================================================
         * 10. MEMBERS
         * =========================================================
         */

        List<ReferralUserDto> members =
                new java.util.ArrayList<>();


        /*
         * Level 1
         */

        for (User user : level1Users) {

            members.add(
                    mapReferralUser(
                            user,
                            1,
                            level1Rate
                    )
            );
        }


        /*
         * Level 2
         */

        for (User user : level2Users) {

            members.add(
                    mapReferralUser(
                            user,
                            2,
                            level2Rate
                    )
            );
        }


        /*
         * Level 3
         */

        for (User user : level3Users) {

            members.add(
                    mapReferralUser(
                            user,
                            3,
                            level3Rate
                    )
            );
        }


        /*
         * =========================================================
         * 11. LEVEL 1 RESPONSE
         * =========================================================
         */

        LevelTeamDto level1 =
                new LevelTeamDto();


        level1.setTotalMembers(
                level1Users.size()
        );


        level1.setActiveMembers(
                level1ActiveMembers
        );


        level1.setTotalRecharge(
                level1Recharge
        );


        level1.setTotalInvestment(
                level1Investment
        );


        level1.setCommission(
                level1Commission
        );


        level1.setCommissionRate(
                new BigDecimal("15")
        );


        /*
         * =========================================================
         * 12. LEVEL 2 RESPONSE
         * =========================================================
         */

        LevelTeamDto level2 =
                new LevelTeamDto();


        level2.setTotalMembers(
                level2Users.size()
        );


        level2.setActiveMembers(
                level2ActiveMembers
        );


        level2.setTotalRecharge(
                level2Recharge
        );


        level2.setTotalInvestment(
                level2Investment
        );


        level2.setCommission(
                level2Commission
        );


        level2.setCommissionRate(
                new BigDecimal("2")
        );


        /*
         * =========================================================
         * 13. LEVEL 3 RESPONSE
         * =========================================================
         */

        LevelTeamDto level3 =
                new LevelTeamDto();


        level3.setTotalMembers(
                level3Users.size()
        );


        level3.setActiveMembers(
                level3ActiveMembers
        );


        level3.setTotalRecharge(
                level3Recharge
        );


        level3.setTotalInvestment(
                level3Investment
        );


        level3.setCommission(
                level3Commission
        );


        level3.setCommissionRate(
                new BigDecimal("1")
        );


        /*
         * =========================================================
         * 14. FINAL RESPONSE
         * =========================================================
         */

        TeamResponseDto response =
                new TeamResponseDto();


        response.setTotalMembers(
                totalMembers
        );


        response.setActiveMembers(
                activeMembers
        );


        response.setTotalRecharge(
                totalRecharge
        );


        response.setTotalInvestment(
                totalInvestment
        );


        response.setCommission(
                totalCommission
        );


        response.setLevel1(level1);

        response.setLevel2(level2);

        response.setLevel3(level3);

        response.setMembers(members);


        return response;
    }

    @Transactional
    @Override
    public void distributeReferralCommission(User investedUser, BigDecimal investmentAmountInr) {

        if (investedUser == null) {
            return;
        }

        if (investmentAmountInr == null || investmentAmountInr.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }


        BigDecimal level1Rate =
                new BigDecimal("0.15");

        BigDecimal level2Rate =
                new BigDecimal("0.02");

        BigDecimal level3Rate =
                new BigDecimal("0.01");


        /*
         * =========================================================
         * LEVEL 1
         * =========================================================
         */

        if (investedUser.getReferrerId() == null) {
            return;
        }


        User level1User =
                userRepository.findById(
                        investedUser.getReferrerId()
                ).orElse(null);


        if (level1User == null) {
            return;
        }


        BigDecimal level1CommissionInr =
                investmentAmountInr
                        .multiply(level1Rate)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );


        BigDecimal level1CommissionUsdt =
                CurrencyUtil.inrToUsdt(
                        level1CommissionInr
                );


        BigDecimal level1Balance =
                level1User.getBalance() != null
                        ? level1User.getBalance()
                        : BigDecimal.ZERO;


        level1User.setBalance(
                level1Balance.add(
                        level1CommissionUsdt
                )
        );


        userRepository.save(
                level1User
        );


        /*
         * =========================================================
         * LEVEL 2
         * =========================================================
         */

        if (level1User.getReferrerId() == null) {
            return;
        }


        User level2User =
                userRepository.findById(
                        level1User.getReferrerId()
                ).orElse(null);


        if (level2User == null) {
            return;
        }


        BigDecimal level2CommissionInr =
                investmentAmountInr
                        .multiply(level2Rate)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );


        BigDecimal level2CommissionUsdt =
                CurrencyUtil.inrToUsdt(
                        level2CommissionInr
                );


        BigDecimal level2Balance =
                level2User.getBalance() != null
                        ? level2User.getBalance()
                        : BigDecimal.ZERO;


        level2User.setBalance(
                level2Balance.add(
                        level2CommissionUsdt
                )
        );


        userRepository.save(
                level2User
        );


        /*
         * =========================================================
         * LEVEL 3
         * =========================================================
         */

        if (level2User.getReferrerId() == null) {
            return;
        }


        User level3User =
                userRepository.findById(
                        level2User.getReferrerId()
                ).orElse(null);


        if (level3User == null) {
            return;
        }


        BigDecimal level3CommissionInr =
                investmentAmountInr
                        .multiply(level3Rate)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );


        BigDecimal level3CommissionUsdt =
                CurrencyUtil.inrToUsdt(
                        level3CommissionInr
                );


        BigDecimal level3Balance =
                level3User.getBalance() != null
                        ? level3User.getBalance()
                        : BigDecimal.ZERO;


        level3User.setBalance(
                level3Balance.add(
                        level3CommissionUsdt
                )
        );


        userRepository.save(
                level3User
        );
    }


    private BigDecimal getUserTotalInvestment(Long userId) {

        return userInvestmentRepository
                .findByUserId(userId)
                .stream()
                .map(UserInvestment::getInvestmentAmount)
                .filter(amount -> amount != null)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }
    private ReferralUserDto mapReferralUser(
            User user,
            int level,
            BigDecimal rate
    ) {

        ReferralUserDto dto =
                new ReferralUserDto();


        /*
         * =========================================================
         * USER DATA
         * =========================================================
         */

        dto.setId(
                user.getId()
        );


        dto.setPhone(
                user.getPhone()
        );


        dto.setBalance(
                user.getBalance() != null
                        ? user.getBalance()
                        : BigDecimal.ZERO
        );


        dto.setTotalIncome(
                user.getTotalIncome() != null
                        ? user.getTotalIncome()
                        : BigDecimal.ZERO
        );


        dto.setTotalRecharge(
                user.getTotalRecharge() != null
                        ? user.getTotalRecharge()
                        : BigDecimal.ZERO
        );


        /*
         * =========================================================
         * TOTAL INVESTMENT
         * =========================================================
         */

        BigDecimal totalInvestment =
                getUserTotalInvestment(
                        user.getId()
                );


        dto.setTotalInvestment(
                totalInvestment
        );


        /*
         * =========================================================
         * COMMISSION
         *
         * INVESTMENT ONLY
         * =========================================================
         */

        BigDecimal commission =
                totalInvestment
                        .multiply(rate)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );


        dto.setCommission(
                commission
        );


        /*
         * =========================================================
         * LEVEL
         * =========================================================
         */

        dto.setLevel(
                level
        );


        /*
         * =========================================================
         * STATUS
         * =========================================================
         */

        dto.setStatus(
                user.getStatus()
        );


        /*
         * =========================================================
         * CREATED DATE
         * =========================================================
         */

        if (user.getCreatedOn() != null) {

            dto.setCreatedOn(
                    user.getCreatedOn().toString()
            );
        }


        return dto;
    }



}