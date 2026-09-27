package com.salesanalytics.qa.enums;

import io.cucumber.java.ParameterType;

import java.util.Arrays;

public enum DateRangeFilter {

    NOT_SELECTED("Not selected", "none"),
    LAST_7_DAYS("Last 7 days", "7"),
    LAST_30_DAYS("Last 30 days", "30"),
    LAST_90_DAYS("Last 90 days", "90");

    private final String label;
    private final String value;

    DateRangeFilter(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String label() {
        return label;
    }

    public String value() {
        return value;
    }

    /**
     * Converts the quoted date range value used in feature files directly
     * into its {@link DateRangeFilter}. A value that isn't one of the
     * known labels fails fast with a clear error instead of being silently
     * accepted.
     */
    @ParameterType("\"([^\"]*)\"")
    public static DateRangeFilter dateRangeFilter(String label) {
        return Arrays.stream(values())
                .filter(filter -> filter.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown date range: " + label));
    }
}
