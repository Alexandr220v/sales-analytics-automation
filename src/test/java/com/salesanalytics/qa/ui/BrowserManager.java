package com.salesanalytics.qa.ui;

import com.microsoft.playwright.Page;

/**
 * Shared per-scenario browser state, created by {@link com.salesanalytics.qa.hooks.Hooks}
 * and injected (via Cucumber-PicoContainer) into step definitions and page objects.
 */
public class BrowserManager {

    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
