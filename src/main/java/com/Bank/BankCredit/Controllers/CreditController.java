package com.Bank.BankCredit.Controllers;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.ResponseHandler;
import com.Bank.BankCredit.Repository.ICreditRepository;
import com.Bank.BankCredit.Service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/Credit/")
public class CreditController {

    @Autowired
    private ICreditRepository oCreditRep;
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
    public Mono<ResponseHandler> SaveCreditAccount(@RequestBody Credit oCredit){
       return creditService.create(oCredit, "Credit Account");
    }

    /**
     * Guardar nueva tarjeta de credito
     * @param oCredit
     * @return
     */
    @PostMapping("CreditCard/")
    public Mono<ResponseHandler> SaveCreditCard(@RequestBody Credit oCredit){
        return creditService.create(oCredit, "Credit Card");
    }
}