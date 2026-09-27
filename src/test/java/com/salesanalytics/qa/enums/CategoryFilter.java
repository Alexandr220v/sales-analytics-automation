package com.salesanalytics.qa.enums;

import io.cucumber.java.ParameterType;

import java.util.Arrays;

public enum CategoryFilter {

    NOT_SELECTED("Not selected", "none"),
    FURNITURE("Furniture", "Furniture"),
    APPLIANCES("Appliances", "Appliances"),
    LIGHTING("Lighting", "Lighting");

    private final String label;
    private final String value;

    CategoryFilter(String label, String value) {
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
     * Converts the quoted category value used in feature files directly
     * into its {@link CategoryFilter}. A value that isn't one of the known
     * labels fails fast with a clear error instead of being silently
     * accepted.
     */
    @ParameterType("\"([^\"]*)\"")
    public static CategoryFilter categoryFilter(String label) {
        return Arrays.stream(values())
                .filter(filter -> filter.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown category: " + label));
    }
}
