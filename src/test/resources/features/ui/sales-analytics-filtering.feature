@ui
Feature: Sales Analytics table filtering and sorting

  As a sales analyst
  I want to filter the transactions table by region and category and choose a sort order
  So that I can quickly analyze the subset of data

  Scenario Outline: Combined filters and sort show expected rows and footer
    Given the Sales Analytics page is open
    When I apply date range "<dateRange>", region "<region>", category "<category>", and sort "<sort>"
    Then the UI table should match salesTransaction "<expectedSalesTransaction>"
    And the footer should show "<footer>"

    Examples:
      | dateRange    | region        | category     | sort                  | expectedSalesTransaction | footer                          |
      | Not selected | Europe        | Furniture    | Date: Newest → Oldest | europe-furniture-desc    | Showing 2 of 10 transactions    |
      | Last 7 days  | Asia          | Appliances   | Date: Newest → Oldest | no-data                  | Showing 0 of 10 transactions    |
      | Last 90 days | Not selected  | Lighting     | Date: Newest → Oldest | lighting-date-desc       | Showing 3 of 10 transactions    |
      | Last 90 days | Asia          | Not selected | Revenue: Low → High   | asia-revenue-asc         | Showing 3 of 10 transactions    |
