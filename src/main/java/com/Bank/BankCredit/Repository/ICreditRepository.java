package com.Bank.BankCredit.Repository;

import com.Bank.BankCredit.Models.Documents.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditRepository extends ReactiveMongoRepository<Credit, String> {
}