package com.salesanalytics.qa.data;

import com.salesanalytics.qa.model.SalesTransaction;

import java.util.List;

/**
 * Expected transaction datasets for the named scenarios used in
 * {@code features/ui/*.feature} and {@code features/api/*.feature}.
 * Both UI and API step definitions assert against the same underlying
 * data here rather than each keeping their own copy.
 */
public final class SalesTransactionData {

    private SalesTransactionData() {
    }

    public static List<SalesTransaction> forSalesTransaction(String salesTransaction) {
        return switch (salesTransaction) {
            case "europe-furniture-desc" -> List.of(
                    new SalesTransaction("#1010", "2025-03-25", "Europe", "Furniture", 1680),
                    new SalesTransaction("#1001", "2025-01-05", "Europe", "Furniture", 1200)
            );
            case "no-data" -> List.of();
            case "lighting-date-desc" -> List.of(
                    new SalesTransaction("#1009", "2025-03-18", "Asia", "Lighting", 2100),
                    new SalesTransaction("#1007", "2025-03-01", "Europe", "Lighting", 760),
                    new SalesTransaction("#1003", "2025-01-18", "Asia", "Lighting", 640)
            );
            case "asia-revenue-asc" -> List.of(
                    new SalesTransaction("#1003", "2025-01-18", "Asia", "Lighting", 640),
                    new SalesTransaction("#1006", "2025-02-20", "Asia", "Furniture", 980),
                    new SalesTransaction("#1009", "2025-03-18", "Asia", "Lighting", 2100)
            );
            default -> throw new IllegalArgumentException("Unknown expected sales transaction: " + salesTransaction);
        };
    }
}
