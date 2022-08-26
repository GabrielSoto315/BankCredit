package com.Bank.BankCredit.Service;

import com.Bank.BankCredit.Models.Entities.Response.ClientCompanyResponse;
import com.Bank.BankCredit.Models.Entities.Response.ClientPersonResponse;
import com.Bank.BankCredit.Models.Entities.Response.ClientTypeResponse;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<ClientCompanyResponse> FindClientCompanyId(String id);

    Mono<ClientPersonResponse> FindClientPersonId(String id);

    Mono<ClientTypeResponse> FindClientTypeId(String id);
}
