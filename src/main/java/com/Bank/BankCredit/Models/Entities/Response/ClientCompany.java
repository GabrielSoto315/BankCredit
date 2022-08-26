package com.Bank.BankCredit.Models.Entities.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCompany {

    @Id
    private String id_client;
    private String id_number;
    private String name;

}
