package com.Bank.BankCredit.Models.Entities.Response;

import lombok.Data;

@Data
public class ClientTypeResponse {
    private String message;
    private String status;
    private ClientType data;
}
