package com.backend.investment.mapper;

import com.backend.investment.dto.UserResponseDto;
import com.backend.investment.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    /**
     * Entity -> DTO
     */
    public static UserResponseDto userToUserResponseDto(User user) {
        if (user == null) {
            return null;
        }
        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setPhone(user.getPhone());
        dto.setReferralCode(user.getReferralCode());
        dto.setBalance(user.getBalance());
        dto.setTotalIncome(user.getTotalIncome());
        dto.setTotalRecharge(user.getTotalRecharge());
        dto.setTotalWithdraw(user.getTotalWithdraw());
        dto.setStatus(user.getStatus());
        dto.setCreatedOn(user.getCreatedOn());
        dto.setAccountHolderName(user.getAccountHolderName());
        dto.setAccountNumber(user.getAccountNumber());
        dto.setBankName(user.getBankName());
        dto.setIfscCode(user.getIfscCode());
        return dto;
    }

    /**
     * DTO -> Entity
     */
    public static User userResponseDtoToUser(UserResponseDto dto) {

        if (dto == null) {
            return null;
        }

        User user = new User();

        user.setId(dto.getId());
        user.setPhone(dto.getPhone());
        user.setReferralCode(dto.getReferralCode());
        user.setBalance(dto.getBalance());
        user.setTotalIncome(dto.getTotalIncome());
        user.setTotalRecharge(dto.getTotalRecharge());
        user.setTotalWithdraw(dto.getTotalWithdraw());
        user.setStatus(dto.getStatus());
        user.setLocation(dto.getLocation());
        user.setIp_address(dto.getIpAddress());
        dto.setAccountHolderName(user.getAccountHolderName());
        dto.setAccountNumber(user.getAccountNumber());
        dto.setIfscCode(user.getIfscCode());
        dto.setBankName(user.getBankName());
        return user;
    }

}