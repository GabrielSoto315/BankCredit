package com.Bank.BankCredit.Repository;

import com.Bank.BankCredit.Models.Documents.Credit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICreditRepositoryDao extends MongoRepository<Credit, String> {
}
