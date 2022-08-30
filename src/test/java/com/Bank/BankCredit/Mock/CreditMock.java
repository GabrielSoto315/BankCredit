package com.Bank.BankCredit.Mock;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.Response.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CreditMock {

    public static Credit randomAccount(){
        Credit credit = new Credit();
        credit.setId_credit_number("25210000000004");
        credit.setClient_number("1910000000003");
        credit.setActive(true);
        credit.setRegister_date(new Date());
        credit.setAmount(BigDecimal.valueOf(42000));
        credit.setBalance(BigDecimal.valueOf(400));
        credit.setLast_transaction(new Date());

        Product product = new Product();
        product.setName("Credit Account");
        product.setClientType("Person");

        credit.setProduct(product);

        return credit;
    }

    public static Credit randomCard(){
        Credit credit = new Credit();
        credit.setId_credit_number(UUID.randomUUID().toString());
        credit.setClient_number(UUID.randomUUID().toString());
        credit.setActive(true);
        credit.setRegister_date(new Date());
        credit.setAmount(BigDecimal.valueOf(9000));
        credit.setBalance(BigDecimal.valueOf(9000));
        credit.setLast_transaction(new Date());

        Product product = new Product();
        product.setName("Credit Card");
        product.setClientType("Person");

        credit.setProduct(product);

        return credit;
    }
}
