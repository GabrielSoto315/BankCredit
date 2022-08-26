package com.Bank.BankCredit.Models.Entities.Response;

import lombok.Data;

@Data
public class ClientCompanyResponse {
    private String message;
    private String status;
    private ClientCompany data;
}

