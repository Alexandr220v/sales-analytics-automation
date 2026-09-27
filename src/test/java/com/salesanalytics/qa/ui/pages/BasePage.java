package com.salesanalytics.qa.ui.pages;

import com.salesanalytics.qa.ui.BrowserManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Base class for all page objects, providing access to the current page and
 * common navigation/wait interaction helpers.
 */
public abstract class BasePage {

    protected final BrowserManager browserManager;

    protected BasePage(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    protected Page getPage() {
        return browserManager.getPage();
    }

    protected void navigate(String url) {
        getPage().navigate(url);
        waitForPageLoad();
    }

    protected void waitForPageLoad() {
        getPage().waitForLoadState(LoadState.LOAD);
    }

    protected void waitForVisible(String selector) {
        getPage().waitForSelector(
                selector,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE)
        );
    }

    protected void click(String selector) {
        waitForVisible(selector);
        getPage().locator(selector).click();
    }

    protected void scrollIntoView(Locator locator) {
        locator.scrollIntoViewIfNeeded();
    }

    public String getTitle() {
        return getPage().title();
    }

    public String url() {
        return getPage().url();
    }
}
