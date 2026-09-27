package com.salesanalytics.qa.ui.component;

import com.salesanalytics.qa.model.SalesTransaction;
import com.salesanalytics.qa.ui.BrowserManager;
import com.microsoft.playwright.Locator;
import org.assertj.core.api.SoftAssertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

/**
 * Reusable table component: row/cell reading, results-count footer, plus
 * matching the rendered rows against expected {@link SalesTransaction}s.
 * Any page that renders this table (currently only the Sales Analytics
 * page) can compose it instead of duplicating table locators and
 * parsing/assertion logic.
 */
public class Table extends BaseComponent {

    private final Locator rows;
    private final Locator footer;

    public Table(BrowserManager browserManager) {
        super(browserManager);
        this.rows = getPage().locator("#rows tr");
        this.footer = getPage().locator("#count");
    }

    /**
     * @return every visible table row as a list of cell texts, in the
     * column order rendered by the page.
     */
    public List<List<String>> rows() {
        List<List<String>> result = new ArrayList<>();
        int rowCount = rows.count();
        for (int r = 0; r < rowCount; r++) {
            Locator cells = rows.nth(r).locator("td");
            List<String> row = new ArrayList<>();
            int cellCount = cells.count();
            for (int c = 0; c < cellCount; c++) {
                row.add(cells.nth(c).innerText());
            }
            result.add(row);
        }
        return result;
    }

    public String footerText() {
        return footer.innerText();
    }

    /**
     * Asserts every visible row matches {@code expectedRows}, in order,
     * collecting all mismatches via {@link SoftAssertions} instead of
     * failing on the first one.
     */
    public void assertMatches(List<SalesTransaction> expectedRows) {
        List<List<String>> actualRows = rows();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actualRows).as("number of rows displayed").hasSize(expectedRows.size());
        int rowCount = Math.min(actualRows.size(), expectedRows.size());
        IntStream.range(0, rowCount).forEach(i -> {
            SalesTransaction expected = expectedRows.get(i);
            List<String> actual = actualRows.get(i);
            softly.assertThat(cellAt(actual, 0)).as("row %d date", i).isEqualTo(expected.date());
            softly.assertThat(cellAt(actual, 1)).as("row %d region", i).isEqualTo(expected.region());
            softly.assertThat(cellAt(actual, 2)).as("row %d category", i).isEqualTo(expected.category());
            softly.assertThat(cellAt(actual, 3)).as("row %d revenue", i).isEqualTo(formatRevenue(expected.revenue()));
            softly.assertThat(cellAt(actual, 4)).as("row %d id", i).isEqualTo(expected.id());
        });
        softly.assertAll();
    }

    private static String formatRevenue(int revenue) {
        return String.format(Locale.US, "%,d", revenue);
    }

    /**
     * @return the cell at {@code index}, or {@code null} if the row has fewer
     * cells than expected, instead of throwing {@link IndexOutOfBoundsException}
     * and aborting the remaining soft assertions.
     */
    private static String cellAt(List<String> row, int index) {
        return index < row.size() ? row.get(index) : null;
    }
}
