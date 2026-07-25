package com.backend.investment.service;

import com.backend.investment.dto.*;
import com.backend.investment.entity.WithdrawHistory;

import java.util.List;

public interface IAdminService {
    AdminLoginResponse login(AdminLoginRequest request);
    List<PendingRechargeDto> getPendingRechargeRequests();
    String approveRecharge(Long paymentRequestId);
    String rejectRecharge(Long id);
    List<PendingWithdrawDto> getPendingWithdrawRequests();

    String approveWithdraw(Long withdrawId);

    String rejectWithdraw(Long withdrawId);
    List<DailyIncomeDetailsDto> getDailyIncomeDetails();

}
