package com.Bank.BankCredit.Models.Entities.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientPerson {
    @Id
    private String idClient;
    private String idCard;
    private String firstName;
    private String lastName;
}
