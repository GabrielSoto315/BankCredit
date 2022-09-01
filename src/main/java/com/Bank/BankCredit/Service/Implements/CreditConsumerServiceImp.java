package com.Bank.BankCredit.Service.Implements;

import com.Bank.BankCredit.Models.Documents.Credit;
import com.Bank.BankCredit.Models.Entities.ResponseHandler;
import com.Bank.BankCredit.Repository.ICreditRepository;
import com.Bank.BankCredit.Repository.ICreditRepositoryDao;
import com.Bank.BankCredit.Service.CreditConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CreditConsumerServiceImp implements CreditConsumerService {

    @Autowired
    private ICreditRepositoryDao creditRepositoryDao ;
    Logger log = LoggerFactory.getLogger(CreditConsumerServiceImp.class);
    public ResponseHandler UpdateDao(String id, Credit credit) {
        log.info("Start update " + credit);
        if(creditRepositoryDao.existsById(id)){
            Optional<Credit> updateCredit = creditRepositoryDao.findById(id);
            updateCredit.map(up -> {
                log.info(up.toString());
                up.setBalance(credit.getBalance());
                up.setLastTransaction(new Date());
                up.setUpdateDate(new Date());
                return up;
            });
            log.info("Update data " + updateCredit.get());
            creditRepositoryDao.save(updateCredit.get());
            return new ResponseHandler("Done", HttpStatus.OK, null);
        }else {
            return new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null);
        }

    }
}
