package com.info.financialportfolioservice.service;

import com.info.financialportfolioservice.Entity.Balance;

import java.util.List;

public interface BalanceService {

    List<Balance> getBalanceByYear(String year);

}
