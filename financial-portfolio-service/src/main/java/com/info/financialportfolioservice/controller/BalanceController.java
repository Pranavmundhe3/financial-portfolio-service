package com.info.financialportfolioservice.controller;

import com.info.financialportfolioservice.Entity.Balance;
import com.info.financialportfolioservice.service.BalanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class BalanceController extends BaseController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping(value = "/balance/year/{year}")
    public ResponseEntity<List<Balance>> getBalanceByYear(@PathVariable String year) {
        log.info("Get Monthly and Cumulative balance details for year- {} ", year);
        List<Balance> response = balanceService.getBalanceByYear(year);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
