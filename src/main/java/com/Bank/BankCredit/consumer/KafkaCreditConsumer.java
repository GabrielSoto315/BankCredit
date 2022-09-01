package com.Bank.BankCredit.consumer;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.EventMessage;
import com.Bank.BankCredit.Service.CreditConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class KafkaCreditConsumer {

    @Autowired
    private CreditConsumerService creditConsumerService;
    private static final Logger log = LoggerFactory.getLogger(KafkaCreditConsumer.class);

    @KafkaListener(topics = "TOPIC-CREDIT", groupId = "GROUP")
    public void consume(String message) throws JsonProcessingException {
        log.info("Received : " + message);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        EventMessage<?> dataEvent = objectMapper.readValue(message, new TypeReference<EventMessage<?>>() {});
        log.info("[INI] Process {}", dataEvent.getAction());

        if (dataEvent.getAction().equals("UPDATE")){
            log.info(dataEvent.getData().toString());
            Credit credit = objectMapper
                            .readValue(message, new TypeReference<EventMessage<Credit>>() {})
                            .getData();
            log.info(credit.toString());
            creditConsumerService.UpdateDao(credit.getIdCredit(), credit);
        }


       // creditConsumerService.UpdateDao(credit.getIdCredit(),credit);
    }
}