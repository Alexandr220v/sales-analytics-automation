package com.salesanalytics.qa.stepdefinitions.ui;

import com.salesanalytics.qa.config.AppConfig;
import com.salesanalytics.qa.data.SalesTransactionData;
import com.salesanalytics.qa.enums.CategoryFilter;
import com.salesanalytics.qa.enums.DateRangeFilter;
import com.salesanalytics.qa.enums.RegionFilter;
import com.salesanalytics.qa.enums.SortOrder;
import com.salesanalytics.qa.ui.BrowserManager;
import com.salesanalytics.qa.ui.pages.SalesAnalyticsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class UiSalesAnalyticsSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(UiSalesAnalyticsSteps.class);

    private final BrowserManager browserManager;
    private SalesAnalyticsPage salesAnalyticsPage;

    public UiSalesAnalyticsSteps(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @Given("the Sales Analytics page is open")
    public void theSalesAnalyticsPageIsOpen() {
        LOGGER.info("Given the Sales Analytics page is open [url={}]", AppConfig.INSTANCE.uiBaseUrl());
        salesAnalyticsPage = new SalesAnalyticsPage(browserManager).open(AppConfig.INSTANCE.uiBaseUrl());
    }

    @When("I apply date range {dateRangeFilter}, region {regionFilter}, category {categoryFilter}, and sort {sortOrder}")
    public void iApplyDateRangeRegionCategoryAndSort(DateRangeFilter dateRange, RegionFilter region, CategoryFilter category, SortOrder sort) {
        LOGGER.info("When I apply date range={}, region={}, category={}, sort={}", dateRange, region, category, sort);
        salesAnalyticsPage
                .selectDateRange(dateRange.value())
                .selectRegion(region.value())
                .selectCategory(category.value())
                .selectSortByLabel(sort.label())
                .clickApply();
    }

    @Then("the UI table should match salesTransaction {string}")
    public void theUiTableShouldMatchSalesTransaction(String expectedSalesTransaction) {
        LOGGER.info("Then the UI table should match salesTransaction={}", expectedSalesTransaction);
        salesAnalyticsPage.assertTableMatches(SalesTransactionData.forSalesTransaction(expectedSalesTransaction));
    }

    @Then("the footer should show {string}")
    public void theFooterShouldShow(String expectedText) {
        LOGGER.info("Then the footer should show={}", expectedText);
        assertThat(salesAnalyticsPage.footerText()).isEqualTo(expectedText);
    }
}
