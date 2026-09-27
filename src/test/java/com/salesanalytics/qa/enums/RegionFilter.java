package com.salesanalytics.qa.enums;

import io.cucumber.java.ParameterType;

import java.util.Arrays;

public enum RegionFilter {

    NOT_SELECTED("Not selected", "none"),
    EUROPE("Europe", "Europe"),
    NORTH_AMERICA("North America", "North America"),
    ASIA("Asia", "Asia");

    private final String label;
    private final String value;

    RegionFilter(String label, String value) {
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
     * Converts the quoted region value used in feature files directly into
     * its {@link RegionFilter}. A value that isn't one of the known labels
     * fails fast with a clear error instead of being silently accepted.
     */
    @ParameterType("\"([^\"]*)\"")
    public static RegionFilter regionFilter(String label) {
        return Arrays.stream(values())
                .filter(filter -> filter.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown region: " + label));
    }
}
