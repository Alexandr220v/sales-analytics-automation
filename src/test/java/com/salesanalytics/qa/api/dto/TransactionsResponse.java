package com.salesanalytics.qa.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionsResponse(List<TransactionResponse> data, Meta meta) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Meta(int returned, int total) {
    }
}
