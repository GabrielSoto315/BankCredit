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
        credit.setIdCredit("25210000000004");
        credit.setIdClient("1910000000003");
        credit.setActive(true);
        credit.setRegisterDate(new Date());
        credit.setAmount(BigDecimal.valueOf(42000));
        credit.setBalance(BigDecimal.valueOf(400));
        credit.setLastTransaction(new Date());

        Product product = new Product();
        product.setName("Credit Account");
        product.setClientType("Person");

        credit.setProduct(product);

        return credit;
    }

    public static Credit randomCard(){
        Credit credit = new Credit();
        credit.setIdCredit(UUID.randomUUID().toString());
        credit.setIdClient(UUID.randomUUID().toString());
        credit.setActive(true);
        credit.setRegisterDate(new Date());
        credit.setAmount(BigDecimal.valueOf(9000));
        credit.setBalance(BigDecimal.valueOf(9000));
        credit.setLastTransaction(new Date());

        Product product = new Product();
        product.setName("Credit Card");
        product.setClientType("Person");

        credit.setProduct(product);

        return credit;
    }
}
