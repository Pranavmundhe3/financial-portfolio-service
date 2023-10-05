package com.info.financialportfolioservice.service.impl;

import com.info.financialportfolioservice.Entity.Balance;
import com.info.financialportfolioservice.Entity.Transaction;
import com.info.financialportfolioservice.service.BalanceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BalanceServiceImpl implements BalanceService {

    private static final String API_TRANSACTIONS = "transactions/";
    private RestTemplate restTemplate;

    @Value("${third-party-team.host.url}")
    private String thirdPartyTeamHostURL;

    @Override
    public List<Balance> getBalanceByYear(String year) {

        // Inter service/Microservice communication handled using RestTemplate
        log.info("Calling external endpoint/API- {} to get transactions", thirdPartyTeamHostURL + API_TRANSACTIONS);
        ResponseEntity<List<Transaction>> responseEntity = restTemplate.exchange(thirdPartyTeamHostURL + API_TRANSACTIONS,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Transaction>>() {
                });
        List<Transaction> transactions = responseEntity.getBody();

        if (CollectionUtils.isEmpty(transactions)) {
            log.info("No transactions found, returning empty response");
            return Collections.emptyList();
        }


        // Get all transactions for the requested year and group them by month.
        // transactionMonthMap - { Jan: [{},{},{}], Feb: [{},{}], March: [{},{}],...... }
        Map<Month, List<Transaction>> transactionMonthMap =
                transactions.stream()
                        .filter(transaction -> transaction.getDate().getYear() == Integer.parseInt(year))
                        .collect(Collectors.groupingBy(transaction -> transaction.getDate().getMonth()));


        // Calculate balance amount for each month and create response as a final result.
        List<Balance> response = new ArrayList<>();
        BigDecimal cumulative = BigDecimal.ZERO;
        for (Map.Entry<Month, List<Transaction>> entry : transactionMonthMap.entrySet()) {
            Month month = entry.getKey();
            List<Transaction> transactionsPerMonth = entry.getValue();
            Balance b = new Balance();

            // Set month
            b.setMonth(month.toString());

            // Compute monthly balance amount
            BigDecimal monthlyAmount = getBalanceAmount(transactionsPerMonth);
            b.setMonthlyAmount(monthlyAmount);

            // Compute cumulative balance amount by adding each monthly balance
            // Similar to->    total = total + current (total and current are int literal)
            cumulative = cumulative.add(monthlyAmount);
            b.setCumulativeAmount(cumulative);

            response.add(b);
        }

        log.info("Get Balance details By Year response- {}", response);
        return response;
    }


    private BigDecimal getBalanceAmount(List<Transaction> transactions) {
        // Get sum of transaction amount for credit type
        BigDecimal creditAmount = getSumOfAmountByType(transactions, Transaction.Type.CREDIT);
        // Get sum of transaction amount for debit type
        BigDecimal debitAmount = getSumOfAmountByType(transactions, Transaction.Type.DEBIT);
        // Final balance is difference of CREDIT & DEBIT
        // creditAmount & debitAmount is always positive, following computation/response can be positive or negative
        return creditAmount.subtract(debitAmount);
    }

    private BigDecimal getSumOfAmountByType(List<Transaction> transactions, Transaction.Type type) {
        return transactions.stream().filter(transaction -> transaction.getType() == type)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
