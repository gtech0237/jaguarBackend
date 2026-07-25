package com.backend.investment.dto;

import lombok.Data;

@Data
public class BankAccountDto {

    private String accountHolderName;

    private String bankName;

    private String accountNumber;

    private String ifscCode;

}