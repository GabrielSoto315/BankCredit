package com.Bank.BankCredit.Models.Documents;

import com.Bank.BankCredit.Models.Entities.Response.Product;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(value = "Credit")
@Data
public class Credit {
    @Transient
    public static final String SEQUENCE_NAME = "creditSequence";

    @Id
    private String idCredit;
    private Product product;
    private BigDecimal balance;
    private BigDecimal amount;
    private String idClient;
    private Boolean active;
    private Date registerDate;
    private Date updateDate;
    private Date lastTransaction;
}
