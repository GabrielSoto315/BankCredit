package com.Bank.BankCredit.Controllers;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.ResponseHandler;
import com.Bank.BankCredit.Repository.ICreditRepository;
import com.Bank.BankCredit.Service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/Credit/")
public class CreditController {

    @Autowired
    private CreditService creditService;

    /**
     * Lista todos los resultados
     * @return
     */
    @GetMapping()
    public Mono<ResponseHandler> GetAll(){
        return creditService.findAll();
    }

    /**
     * Obtener resultado por id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseHandler> FindbyId(@PathVariable("id") String id){
        return creditService.find(id);
    }

    /**
     * Actualizar datos de credito
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseHandler> update(@PathVariable("id") String id, @RequestBody Credit credit) {
        return creditService.update(id, credit);
    }

    /**
     * Borrar datos por id
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseHandler> DeletebyId(@PathVariable("id") String id){
        return creditService.delete(id);
    }

    /**
     * Guardar nueva cuenta de credito
     * @param oCredit
     * @return
     */
   @PostMapping("CreditAccount/")
   @CircuitBreaker(name="sequence", fallbackMethod = "fallBackSequence")
    public Mono<ResponseHandler> SaveCreditAccount(@RequestBody Credit oCredit){
       Credit credit = oCredit;
       return creditService.create(credit, "Credit Account");
    }

    /**
     * Guardar nueva tarjeta de credito
     * @param oCredit
     * @return
     */
    @PostMapping("CreditCard/")
    @CircuitBreaker(name="sequence", fallbackMethod = "fallBackSequence")
    public Mono<ResponseHandler> SaveCreditCard(@RequestBody Credit oCredit){
        Credit credit = oCredit;
        return creditService.create(credit, "Credit Card");
    }

    /**
     * Guardar nuevo credito
     * @param oCredit
     * @return
     */
    @PostMapping()
    @CircuitBreaker(name="sequence", fallbackMethod = "fallBackSequence")
    public Mono<ResponseHandler> SaveCredit(@RequestBody Credit oCredit){
        return creditService.create(oCredit, oCredit.getProduct().getName());
    }

    public Mono<ResponseHandler> fallBackSequence(RuntimeException runtimeException){
        return Mono.just(new ResponseHandler("Microservice not available", HttpStatus.BAD_REQUEST,runtimeException.getMessage()));
    }

}