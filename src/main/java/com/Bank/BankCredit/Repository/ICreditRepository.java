package com.Bank.BankCredit.Repository;

import com.Bank.BankCredit.Models.Documents.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ICreditRepository extends ReactiveMongoRepository<Credit, String> {
}