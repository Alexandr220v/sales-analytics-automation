package com.salesanalytics.qa.stepdefinitions.api;

import com.salesanalytics.qa.api.client.TransactionsClient;
import com.salesanalytics.qa.api.dto.TransactionResponse;
import com.salesanalytics.qa.api.dto.TransactionsResponse;
import com.salesanalytics.qa.data.SalesTransactionData;
import com.salesanalytics.qa.model.SalesTransaction;
import com.salesanalytics.qa.enums.CategoryFilter;
import com.salesanalytics.qa.enums.DateRangeFilter;
import com.salesanalytics.qa.enums.RegionFilter;
import com.salesanalytics.qa.enums.SortOrder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiTransactionsSteps {

    private final TransactionsClient transactionsClient = new TransactionsClient();
    private Response response;
    private TransactionsResponse responseBody;

    @Given("the Sales Analytics API is available")
    public void theSalesAnalyticsApiIsAvailable() {
    }

    @When("I request transactions with date range {dateRangeFilter}, region {regionFilter}, category {categoryFilter}, sorted by {sortOrder}")
    public void iRequestTransactionsWithDateRangeRegionCategoryAndSort(DateRangeFilter dateRange, RegionFilter region, CategoryFilter category, SortOrder sort) {
        response = transactionsClient.getTransactions(
                dateRange.value(),
                region.value(),
                category.value(),
                sort.value()
        );
        responseBody = response.as(TransactionsResponse.class);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        assertThat(response.statusCode()).isEqualTo(expectedStatus);
    }

    @Then("the response should report {int} of {int} transactions")
    public void theResponseShouldReportOfTransactions(int returned, int total) {
        assertThat(responseBody.meta().returned()).isEqualTo(returned);
        assertThat(responseBody.meta().total()).isEqualTo(total);
    }

    @Then("the API response should match salesTransaction {string}")
    public void theApiResponseShouldMatchSalesTransaction(String expectedSalesTransaction) {
        assertTransactionsMatch(SalesTransactionData.forSalesTransaction(expectedSalesTransaction));
    }

    private void assertTransactionsMatch(List<SalesTransaction> expectedRows) {
        List<SalesTransaction> actualRows = responseBody.data().stream()
                .map(TransactionResponse::toSalesTransaction)
                .toList();

        assertThat(actualRows).as("transactions returned").containsExactlyElementsOf(expectedRows);
    }
}
