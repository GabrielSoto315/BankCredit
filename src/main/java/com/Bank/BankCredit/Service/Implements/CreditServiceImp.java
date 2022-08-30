package com.Bank.BankCredit.Service.Implements;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.*;
import com.Bank.BankCredit.Models.Entities.Response.Product;
import com.Bank.BankCredit.Repository.ICreditRepository;
import com.Bank.BankCredit.Service.ClientService;
import com.Bank.BankCredit.Service.CreditService;
import com.Bank.BankCredit.Service.SequenceGeneratorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;


import static com.Bank.BankCredit.Models.Documents.Credit.SEQUENCE_NAME;

@Service
@RequiredArgsConstructor
public class CreditServiceImp implements CreditService {

    @Autowired
    ICreditRepository creditRepository;
    @Autowired
    SequenceGeneratorService sequenceService;
    @Autowired
    ClientService clientService;

    private static final Logger log = LoggerFactory.getLogger(CreditServiceImp.class);


    @Override
    public Mono<ResponseHandler> findAll() {
        return creditRepository.findAll()
                .doOnNext(n -> log.info(n.toString()))
                .collectList()
                .map(x -> new ResponseHandler("Done", HttpStatus.OK, x))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> find(String id) {
        return creditRepository.findById(id)
                .doOnNext(n -> log.info(n.toString()))
                .map(x -> new ResponseHandler("Done", HttpStatus.OK, x))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> create(Credit credit, String product) {
        return clientService.FindClientTypeId(credit.getClient_number()).flatMap(client ->{
            log.info("Client" + client.toString());
           if (client.getData() == null){
               return Mono.just(new ResponseHandler("Client not found",HttpStatus.BAD_REQUEST,null));
           }else{
               log.info("Credit product : " + product);
               Product oProduct = new Product();
               if (product.equals("Credit Account")){
                   if (client.getData().getType().equals("Person")) {
                       return creditRepository.findAll()
                               .filter(x -> x.getActive().equals(true) &&
                                       x.getProduct().getName().equals("Credit Account") &&
                                       x.getClient_number().equals(credit.getClient_number()))
                               .count()
                               .flatMap(c -> {
                                   if (c >= 1){
                                       return Mono.just (new ResponseHandler( "Client have a credit", HttpStatus.BAD_REQUEST, null));
                                   }else {
                                       log.info("Prepare credit");
                                       oProduct.setName(product);
                                       oProduct.setClientType(client.getData().getType());
                                       log.info(oProduct.toString());
                                       return sequenceService.getSequenceNumber(SEQUENCE_NAME).flatMap(s -> {
                                           credit.setId_credit_number(String.format("2521%010d", s));
                                           log.info(credit.toString());
                                           credit.setActive(true);
                                           credit.setProduct(oProduct);
                                           credit.setBalance(BigDecimal.ZERO);
                                           credit.setRegister_date(new Date());
                                           credit.setLast_transaction(new Date());
                                           log.info(credit.toString());
                                           return creditRepository.save(credit).flatMap(z -> Mono.just(new ResponseHandler("Done", HttpStatus.OK, z)));

                                       });
                                   }
                               });
                   }
                   else {
                       log.info("Prepare credit");
                       oProduct.setName(product);
                       oProduct.setClientType(client.getData().getType());
                       log.info(oProduct.toString());
                       return sequenceService.getSequenceNumber(SEQUENCE_NAME).flatMap(s -> {
                           credit.setId_credit_number(String.format("2521%010d", s));
                           log.info(credit.toString());
                           credit.setActive(true);
                           credit.setProduct(oProduct);
                           credit.setBalance(BigDecimal.ZERO);
                           credit.setRegister_date(new Date());
                           credit.setLast_transaction(new Date());
                           log.info(credit.toString());
                           return creditRepository.save(credit).flatMap(z -> Mono.just(new ResponseHandler("Done", HttpStatus.OK, z)));
                       });
                   }
               } else if (product =="Credit Card") {

                   oProduct.setName(product);
                   oProduct.setClientType(client.getData().getType());
                       return sequenceService.getSequenceNumber(SEQUENCE_NAME).flatMap(s -> {
                           credit.setId_credit_number(String.format("4521%010d", s));
                           credit.setActive(true);
                           credit.setProduct(oProduct);
                           credit.setBalance(credit.getAmount());
                           credit.setRegister_date(new Date());
                           credit.setLast_transaction(new Date());
                           return creditRepository.save(credit).flatMap(z -> Mono.just(new ResponseHandler("Done", HttpStatus.OK, z)));
                       });
               }else {
                   return Mono.just(new ResponseHandler("Invalid product",HttpStatus.BAD_REQUEST,null));
               }
           }
        });
    }

    @Override
    public Mono<ResponseHandler> update(String id, Credit credit) {
        return creditRepository.existsById(id).flatMap(check -> {
            if (check) {
                return creditRepository.findById(id)
                        .flatMap(x -> {
                            x.setBalance(credit.getBalance());
                            x.setLast_transaction(new Date());
                            x.setUpdate_date(new Date());
                            return creditRepository.save(x)
                                    .map(y -> new ResponseHandler("Done", HttpStatus.OK, y))
                                    .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
                        });
            }
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));
        });
    }

    @Override
    public Mono<ResponseHandler> delete(String id) {
        return creditRepository.existsById(id).flatMap(check ->{
            if (check){
                return creditRepository.findById(id).flatMap(x ->{
                    x.setActive(false);
                    return creditRepository.save(x)
                            .then(Mono.just(new ResponseHandler("Done", HttpStatus.OK, null)));
                });
            }
            else {
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND,null));
            }
        });
    }
}

