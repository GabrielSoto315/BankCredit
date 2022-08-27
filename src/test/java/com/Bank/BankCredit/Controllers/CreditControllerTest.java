package com.Bank.BankCredit.Controllers;


import com.Bank.BankCredit.Models.Entities.ResponseHandler;
import com.Bank.BankCredit.Service.CreditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(CreditController.class)
public class CreditControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private CreditService creditService;

    @Test
    void findAllTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);
        when(creditService.findAll()).thenReturn(Mono.just(responseHandler));

        webClient
                .get().uri("/api/Credit/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ResponseHandler.class);

    }

}
