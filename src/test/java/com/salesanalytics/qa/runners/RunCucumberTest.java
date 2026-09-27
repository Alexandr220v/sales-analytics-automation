package com.salesanalytics.qa.runners;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * Single entry point for Surefire. Discovers every {@code .feature} file
 * under {@code src/test/resources/features} (API and UI alike) and wires
 * them to the glue (step definitions/hooks) under {@code com.salesanalytics.qa}.
 * <p>
 * Run everything:      {@code mvn test}
 * Run only API tests:  {@code mvn test -Dcucumber.filter.tags="@api"}
 * Run only UI tests:   {@code mvn test -Dcucumber.filter.tags="@ui"}
 * <p>
 * Reports are written to {@code target/cucumber-reports/}: an HTML report
 * ({@code cucumber.html}), a machine-readable JSON report ({@code cucumber.json}),
 * and a JUnit XML report ({@code cucumber.xml}) for CI integration.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.salesanalytics.qa")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, summary, "
        + "html:target/cucumber-reports/cucumber.html, "
        + "json:target/cucumber-reports/cucumber.json, "
        + "junit:target/cucumber-reports/cucumber.xml, "
        + "com.salesanalytics.qa.reporting.FeatureLifecycleLogger")
public class RunCucumberTest {
}
