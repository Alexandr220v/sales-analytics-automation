package com.salesanalytics.qa.ui.pages;

import com.salesanalytics.qa.model.SalesTransaction;
import com.salesanalytics.qa.ui.BrowserManager;
import com.salesanalytics.qa.ui.component.Table;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;

import java.util.List;

/**
 * Page object for the Sales Analytics table page (filters, sort, table
 * and footer). Tests must not call Playwright APIs directly - they
 * interact with this class only. Table reading/parsing is delegated
 * to the reusable {@link Table} component.
 */
public class SalesAnalyticsPage extends BasePage {

    private final Locator dateRangeSelect;
    private final Locator regionSelect;
    private final Locator categorySelect;
    private final Locator sortSelect;
    private final Locator status;
    private final Table table;

    public SalesAnalyticsPage(BrowserManager browserManager) {
        super(browserManager);
        this.dateRangeSelect = getPage().locator("#dateRange");
        this.regionSelect = getPage().locator("#region");
        this.categorySelect = getPage().locator("#category");
        this.sortSelect = getPage().locator("#sort");
        this.status = getPage().locator("#status");
        this.table = new Table(browserManager);
    }

    public SalesAnalyticsPage open(String baseUrl) {
        navigate(baseUrl);
        waitForResultsLoaded();
        return this;
    }

    public SalesAnalyticsPage selectDateRange(String value) {
        dateRangeSelect.selectOption(value);
        return this;
    }

    public SalesAnalyticsPage selectRegion(String value) {
        regionSelect.selectOption(value);
        return this;
    }

    public SalesAnalyticsPage selectCategory(String value) {
        categorySelect.selectOption(value);
        return this;
    }

    /**
     * Selects a "Sort by" option by its visible label, e.g.
     * {@code "Revenue: High -> Low"} (the arrow in the feature files is
     * the real unicode arrow used by the UI).
     */
    public SalesAnalyticsPage selectSortByLabel(String label) {
        sortSelect.selectOption(new SelectOption().setLabel(label));
        return this;
    }

    public SalesAnalyticsPage clickApply() {
        click("#apply");
        waitForResultsLoaded();
        return this;
    }

    private void waitForResultsLoaded() {
        status.waitFor();
        getPage().waitForFunction("() => !document.getElementById('status').textContent.includes('Loading')");
    }

    /**
     * Asserts every visible table row matches {@code expectedRows}, in order.
     */
    public void assertTableMatches(List<SalesTransaction> expectedRows) {
        table.assertMatches(expectedRows);
    }

    public String footerText() {
        return table.footerText();
    }
}
