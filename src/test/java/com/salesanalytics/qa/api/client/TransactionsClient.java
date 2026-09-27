package com.salesanalytics.qa.api.client;

import com.salesanalytics.qa.api.BaseClient;
import com.salesanalytics.qa.api.filter.LoggingFilter;
import com.salesanalytics.qa.config.AppConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Thin wrapper around {@code GET /api/transactions}. New endpoints should
 * get their own {@code *Client} class here rather than inline REST Assured
 * calls in step definitions.
 */
public class TransactionsClient extends BaseClient {

    private static final LoggingFilter LOGGING_FILTER = new LoggingFilter();

    private static final String PATH = "/api/transactions";
    private static final String NOT_SELECTED = "none";
    private static final String DEFAULT_SORT = "date_asc";

    /**
     * @param dateRange one of "none"/"7"/"30"/"90" (defaults to "none")
     * @param region    one of "none"/"Europe"/"North America"/"Asia" (defaults to "none")
     * @param category  one of "none"/"Furniture"/"Appliances"/"Lighting" (defaults to "none")
     * @param sort      one of "date_asc"/"date_desc"/"rev_asc"/"rev_desc" (defaults to "date_asc")
     */
    public Response getTransactions(String dateRange, String region, String category, String sort) {
        return get(transactionsSpec(dateRange, region, category, sort), PATH);
    }

    private RequestSpecification transactionsSpec(String dateRange, String region, String category, String sort) {
        return new RequestSpecBuilder()
                .setBaseUri(AppConfig.INSTANCE.apiBaseUrl())
                .addFilter(LOGGING_FILTER)
                .addQueryParam("dateRange", valueOrDefault(dateRange, NOT_SELECTED))
                .addQueryParam("region", valueOrDefault(region, NOT_SELECTED))
                .addQueryParam("category", valueOrDefault(category, NOT_SELECTED))
                .addQueryParam("sort", valueOrDefault(sort, DEFAULT_SORT))
                .build();
    }

    private static String valueOrDefault(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
