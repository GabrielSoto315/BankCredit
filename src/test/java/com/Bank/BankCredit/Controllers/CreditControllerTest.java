package com.Bank.BankCredit.Controllers;


import com.Bank.BankCredit.Mock.CreditMock;
import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.ResponseHandler;
import com.Bank.BankCredit.Service.CreditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;
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

    @Test
    void findByIdTest() {
        Credit credit = CreditMock.randomAccount();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(credit);

        Mockito
                .when(creditService.find("25210000000004"))
                .thenReturn(Mono.just(responseHandler));

        webClient.get().uri("/api/Credit/{id}", "25210000000004")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseHandler.class);

        Mockito.verify(creditService, times(1)).find("25210000000004");
    }

    @Test
    void createTest() {

        Credit credit = CreditMock.randomAccount();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(credit);

        Mockito
                .when(creditService.create(credit, "Credit Account")).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/Credit/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(credit))
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void createAccountTest() {

        Credit credit = CreditMock.randomAccount();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(credit);

        Mockito
                .when(creditService.create(credit, "Credit Account")).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/Credit/CreditAccount/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(credit))
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void updateTest() {

        Credit credit = CreditMock.randomAccount();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(credit);

        Mockito
                .when(creditService.update("25210000000004",credit)).thenReturn(Mono.just(responseHandler));

        webClient
                .put()
                .uri("/api/Credit/{id}", "25210000000004")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(credit))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito
                .when(creditService.delete("25210000000004"))
                .thenReturn(Mono.just(responseHandler));

        webClient.delete().uri("/api/Credit/{id}", "25210000000004")
                .exchange()
                .expectStatus().isOk();
    }
}



