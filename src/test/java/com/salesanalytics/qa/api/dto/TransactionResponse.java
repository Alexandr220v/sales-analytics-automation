package com.salesanalytics.qa.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salesanalytics.qa.model.SalesTransaction;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionResponse(String id, String date, String region, String category, int revenue) {

    public SalesTransaction toSalesTransaction() {
        return new SalesTransaction(id, date, region, category, revenue);
    }
}
