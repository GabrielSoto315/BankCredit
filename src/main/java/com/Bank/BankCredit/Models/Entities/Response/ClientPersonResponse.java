package com.Bank.BankCredit.Models.Entities.Response;

import com.Bank.BankCredit.Models.Entities.Response.ClientPerson;
import lombok.Data;

@Data
public class ClientPersonResponse {
    private String message;
    private String status;
    private ClientPerson data;
}
