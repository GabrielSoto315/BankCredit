package com.Bank.BankCredit.Service.Implements;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.*;
import com.Bank.BankCredit.Models.Entities.Response.Product;
import com.Bank.BankCredit.Repository.ICreditRepository;
import com.Bank.BankCredit.Repository.ICreditRepositoryDao;
import com.Bank.BankCredit.Service.ClientService;
import com.Bank.BankCredit.Service.CreditService;
import com.Bank.BankCredit.Service.SequenceGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


import static com.Bank.BankCredit.Models.Documents.Credit.SEQUENCE_NAME;

@Service
public class CreditServiceImp implements CreditService {

    @Autowired
    private ICreditRepository creditRepository;
    @Autowired
    private SequenceGeneratorService sequenceService;
    @Autowired
    private ClientService clientService;

    private static final Logger log = LoggerFactory.getLogger(CreditServiceImp.class);


    @Override
    public Mono<ResponseHandler> findAll() {
        return creditRepository.findAll()
                .doOnNext(n -> log.info(n.toString()))
                .filter(f -> f.getActive().equals(true))
                .collectList()
                .map(x -> new ResponseHandler("Done", HttpStatus.OK, x))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
    }

    @Override
    public Mono<ResponseHandler> find(String id) {
        return creditRepository.existsById(id).flatMap(exist -> {
            if (exist){
                return creditRepository.findById(id)
                        .doOnNext(credit -> log.info(credit.toString()))
                        .map(res -> {
                            if (res.getActive()){
                                return new ResponseHandler("Done", HttpStatus.OK, res);
                            } else {
                                return new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null);
                            }
                        })
                        .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
            } else {
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));
            }
        });
    }

    @Override
    public Mono<ResponseHandler> create(Credit credit, String product) {
        return clientService.FindClientTypeId(credit.getIdClient()).flatMap(client ->{
            log.info("Client" + client.toString());
           if (client.getData() == null){
               return Mono.just(new ResponseHandler("Client not found",HttpStatus.BAD_REQUEST,null));
           }else{
               log.info("Credit product : " + product);
               Product oProduct = new Product();
               credit.setActive(true);
               credit.setRegisterDate(new Date());
               credit.setLastTransaction(new Date());
               if (product.equals("Credit Account")){
                   if (client.getData().getType().equals("Person")) {
                       return creditRepository.findAll()
                               .filter(x -> x.getActive().equals(true) &&
                                       x.getProduct().getName().equals("Credit Account") &&
                                       x.getIdClient().equals(credit.getIdClient()))
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
                                           credit.setIdCredit(String.format("2521%010d", s));
                                           log.info(credit.toString());
                                           credit.setProduct(oProduct);
                                           credit.setBalance(BigDecimal.ZERO);
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
                           credit.setIdCredit(String.format("2521%010d", s));
                           log.info(credit.toString());
                           credit.setProduct(oProduct);
                           credit.setBalance(BigDecimal.ZERO);
                           log.info(credit.toString());
                           return creditRepository.save(credit).flatMap(z -> Mono.just(new ResponseHandler("Done", HttpStatus.OK, z)));
                       });
                   }
               } else if (product =="Credit Card") {

                   oProduct.setName(product);
                   oProduct.setClientType(client.getData().getType());
                       return sequenceService.getSequenceNumber(SEQUENCE_NAME).flatMap(s -> {
                           credit.setIdCredit(String.format("4521%010d", s));
                           credit.setProduct(oProduct);
                           credit.setBalance(credit.getAmount());
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
        log.info("Start update" + credit);
        return creditRepository.existsById(id).flatMap(check -> {
            log.info("Check result: " +check);
            if (check) {
                return creditRepository.findById(id)
                        .flatMap(x -> {
                            log.info("Update data: " + x);
                            x.setBalance(credit.getBalance());
                            x.setLastTransaction(new Date());
                            x.setUpdateDate(new Date());
                            log.info("Update data: " + x);
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

