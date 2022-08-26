package com.Bank.BankCredit.Service.Implements;

import com.Bank.BankCredit.Models.Entities.Response.*;
import com.Bank.BankCredit.Service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImp implements ClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImp.class);

    public Mono<ClientCompanyResponse> FindClientCompanyId(String id){
        String url = "http://localhost:18081/api/ClientCompany/"+id;
        Mono<ClientCompanyResponse> oClientCompanyMono = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ClientCompanyResponse.class);
        oClientCompanyMono.subscribe(client -> log.info(client.toString()));
        return oClientCompanyMono;
    }

    public Mono<ClientPersonResponse> FindClientPersonId(String id){
        String url = "http://localhost:18081/api/ClientPerson/"+id;
        Mono<ClientPersonResponse> oCLientPersonMono = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ClientPersonResponse.class);
        oCLientPersonMono.subscribe(client -> log.info(client.toString()));
        return oCLientPersonMono;
    }

    public Mono<ClientTypeResponse> FindClientTypeId(String id){
        String url = "http://localhost:18081/api/Client/"+id;
        Mono<ClientTypeResponse> oCLientPersonMono = WebClient.create()
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(ClientTypeResponse.class);
        oCLientPersonMono.subscribe(client -> log.info(client.toString()));
        return oCLientPersonMono;
    }
}




