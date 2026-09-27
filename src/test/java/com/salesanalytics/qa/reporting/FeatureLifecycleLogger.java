package com.salesanalytics.qa.reporting;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestRunFinished;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Cucumber plugin (registered via the {@code plugin} configuration parameter
 * on {@link com.salesanalytics.qa.runners.RunCucumberTest}) that logs
 * feature-level start/finish boundaries, separate from the per-step logging
 * done inside the step definition classes. A new feature "starts" the first
 * time one of its scenarios is picked up, and "finishes" once the next
 * feature's first scenario starts (or the whole run ends).
 */
public class FeatureLifecycleLogger implements ConcurrentEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeatureLifecycleLogger.class);

    private final AtomicReference<URI> currentFeatureUri = new AtomicReference<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onTestCaseStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::onTestRunFinished);
    }

    private void onTestCaseStarted(TestCaseStarted event) {
        URI featureUri = event.getTestCase().getUri();
        URI previousFeatureUri = currentFeatureUri.getAndSet(featureUri);
        if (!featureUri.equals(previousFeatureUri)) {
            logFeatureFinished(previousFeatureUri);
            LOGGER.info("=== FEATURE START: {} ===", featureName(featureUri));
        }
    }

    private void onTestRunFinished(TestRunFinished event) {
        logFeatureFinished(currentFeatureUri.getAndSet(null));
    }

    private void logFeatureFinished(URI featureUri) {
        if (featureUri != null) {
            LOGGER.info("=== FEATURE FINISHED: {} ===", featureName(featureUri));
        }
    }

    private static String featureName(URI uri) {
        String path = uri.getPath() != null ? uri.getPath() : uri.getSchemeSpecificPart();
        int slash = path.lastIndexOf('/');
        return slash >= 0 ? path.substring(slash + 1) : path;
    }
}
