package com.salesanalytics.qa.enums;

import io.cucumber.java.ParameterType;

import java.util.Arrays;

public enum SortOrder {

    DATE_ASC("Date: Oldest → Newest", "date_asc"),
    DATE_DESC("Date: Newest → Oldest", "date_desc"),
    REVENUE_ASC("Revenue: Low → High", "rev_asc"),
    REVENUE_DESC("Revenue: High → Low", "rev_desc");

    private final String label;
    private final String value;

    SortOrder(String label, String value) {
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
     * Converts the quoted sort value used in feature files directly into
     * its {@link SortOrder}. A value that isn't one of the known labels
     * fails fast with a clear error instead of being silently accepted.
     */
    @ParameterType("\"([^\"]*)\"")
    public static SortOrder sortOrder(String label) {
        return Arrays.stream(values())
                .filter(order -> order.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown sort order: " + label));
    }
}
