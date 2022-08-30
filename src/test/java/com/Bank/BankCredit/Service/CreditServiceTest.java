package com.Bank.BankCredit.Service;

import com.Bank.BankCredit.Mock.CreditMock;
import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.ResponseHandler;
import com.Bank.BankCredit.Repository.ICreditRepository;
import com.Bank.BankCredit.Service.Implements.CreditServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@ExtendWith(SpringExtension.class)
public class CreditServiceTest {

        @InjectMocks
        private CreditServiceImp creditServiceImp;

        @Mock
        private ICreditRepository creditRepository;

        @Test
        void findAllTest() {
            Credit credit = CreditMock.randomAccount();
            Mockito.when(creditRepository.findAll()).thenReturn(Flux.empty());
            Mono<ResponseHandler> responseHandlerMono = creditServiceImp.findAll();
            StepVerifier.create(responseHandlerMono)
                    .expectNextMatches(response -> response.getData() !=null)
                    .verifyComplete();
        }

        @Test
        void createTest() {

            Credit credit = CreditMock.randomAccount();

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(credit);
            Mockito.when(creditRepository.save(credit)).thenReturn(Mono.just(credit));
        }


        @Test
        void findTest() {

            Credit debitCard = CreditMock.randomAccount();

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(debitCard);

            Mockito.when(creditRepository.existsById("25210000000004"))
                    .thenReturn(Mono.just(true));

            Mockito.when(creditRepository.findById("25210000000004"))
                    .thenReturn(Mono.just(debitCard));

            creditServiceImp.find("25210000000004")
                    .map(response -> StepVerifier.create(Mono.just(response))
                            .expectNextMatches(x -> x.getData() != null)
                            .expectComplete()
                            .verify());
        }

        @Test
        void updateTest() {

            Credit client = CreditMock.randomAccount();

            Mockito.when(creditRepository.existsById("25210000000004"))
                    .thenReturn(Mono.just(true));

            Mockito.when(creditRepository.save(client))
                    .thenReturn(Mono.just(client));

            creditServiceImp.update("25210000000004", client)
                            .map(response -> StepVerifier.create(Mono.just(response))
                                    .expectNextMatches(x -> x.getData() != null)
                                    .expectComplete()
                                    .verify());
        }

        @Test
        void deleteTest() {
            ResponseHandler responseHandler2 = new ResponseHandler();

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(null);

            Mockito.when(creditRepository.existsById("25210000000004"))
                    .thenReturn(Mono.just(true));

            Mockito.when(creditRepository.deleteById("25210000000004")).thenReturn(Mono.empty());

            creditServiceImp.delete("25210000000004")
                    .map(response -> StepVerifier.create(Mono.just(response))
                            .expectNextMatches(x -> x.getMessage().equals("Done"))
                            .expectComplete()
                            .verify());
        }

        @Test
        void updateFoundTest() {

            Credit credit = CreditMock.randomAccount();

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(credit);

            Mockito.when(creditRepository.existsById("25210000000004"))
                    .thenReturn(Mono.just(false));

            Mono<ResponseHandler> responseHandlerMono = creditServiceImp
                    .update("25210000000004", credit);

            StepVerifier.create(responseHandlerMono)
                    .expectNextMatches(response -> response.getMessage().equals("Not found"))
                    .verifyComplete();
        }

        @Test
        void deleteFoundTest() {

            ResponseHandler responseHandler = new ResponseHandler();
            responseHandler.setMessage("Ok");
            responseHandler.setStatus(HttpStatus.OK);
            responseHandler.setData(null);

            Mockito.when(creditRepository.existsById("25210000000001"))
                    .thenReturn(Mono.just(false));

            Mono<ResponseHandler> responseHandlerMono = creditServiceImp
                    .delete("25210000000001");

            StepVerifier.create(responseHandlerMono)
                    .expectNextMatches(response -> response.getMessage().equals("Not found"))
                    .verifyComplete();
        }
}
