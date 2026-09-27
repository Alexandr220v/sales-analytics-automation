package com.salesanalytics.qa.ui.component;

import com.salesanalytics.qa.ui.BrowserManager;
import com.microsoft.playwright.Page;

/**
 * Base class for reusable UI components (table, filters bar, etc.). Owns
 * the {@link BrowserManager} so subclasses can build their own locators and
 * be reused across multiple page objects.
 */
public abstract class BaseComponent {

    protected final BrowserManager browserManager;

    protected BaseComponent(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    protected Page getPage() {
        return browserManager.getPage();
    }
}
