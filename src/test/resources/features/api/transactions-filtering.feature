@api
Feature: Transactions API filtering and sorting

  As a client of the Sales Analytics API
  I want to filter transactions by region and category and sort the result
  So that I only retrieve the data relevant to my analysis, in the order I need

  Scenario Outline: Combined filters and sorting return the expected ordered dataset
    Given the Sales Analytics API is available
    When I request transactions with date range "<dateRange>", region "<region>", category "<category>", sorted by "<sort>"
    Then the response status should be 200
    And the response should report <returned> of 10 transactions
    And the API response should match salesTransaction "<expectedSalesTransaction>"

    Examples:
      | dateRange    | region        | category     | sort                  | returned | expectedSalesTransaction |
      | Not selected | Europe        | Furniture   | Date: Newest → Oldest | 2        | europe-furniture-desc    |
      | Last 7 days  | Asia          | Appliances   | Date: Newest → Oldest | 0        | no-data                  |
      | Last 90 days | Not selected  | Lighting     | Date: Newest → Oldest | 3        | lighting-date-desc       |
      | Last 90 days | Asia          | Not selected | Revenue: Low → High   | 3        | asia-revenue-asc         |
