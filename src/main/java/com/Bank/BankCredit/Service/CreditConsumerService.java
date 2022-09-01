package com.Bank.BankCredit.Service;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.ResponseHandler;

public interface CreditConsumerService {

    ResponseHandler UpdateDao(String id, Credit credit);

}
