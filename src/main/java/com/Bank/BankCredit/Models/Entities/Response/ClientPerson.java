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
    private String id_client;
    private String id_card;
    private String first_name;
    private String last_name;
}
