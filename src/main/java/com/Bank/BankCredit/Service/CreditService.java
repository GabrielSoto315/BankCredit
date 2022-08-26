package com.Bank.BankCredit.Service;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.ResponseHandler;
import reactor.core.publisher.Mono;

public interface CreditService {

    Mono<ResponseHandler> findAll();

    Mono<ResponseHandler> find(String id);

    Mono<ResponseHandler> create(Credit client, String product);

    Mono<ResponseHandler> update(String id, Credit client);

    Mono<ResponseHandler> delete(String id);

}
