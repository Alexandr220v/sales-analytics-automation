package com.salesanalytics.qa.hooks;

import com.salesanalytics.qa.config.AppConfig;
import com.salesanalytics.qa.ui.BrowserManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.After;
import io.cucumber.java.Before;

/**
 * Owns the Playwright lifecycle for every scenario tagged {@code @ui}.
 * A fresh Browser/BrowserContext/Page is created before each scenario and
 * torn down afterwards, so scenarios never leak state into one another.
 * API-only scenarios (tagged {@code @api}) never trigger these hooks, so
 * they run without paying for a browser launch.
 */
public class Hooks {

    private final BrowserManager browserManager;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;

    public Hooks(BrowserManager browserManager) {
        this.browserManager = browserManager;
    }

    @Before("@ui")
    public void launchBrowser() {
        playwright = Playwright.create();
        boolean headless = false;
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        browserContext = browser.newContext();
        browserManager.setPage(browserContext.newPage());
    }

    @After("@ui")
    public void closeBrowser() {
        if (browserContext != null) {
            browserContext.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
