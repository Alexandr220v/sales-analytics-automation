# Sales Analytics – Automation Framework

Cucumber (BDD) automation project covering the Sales Analytics
filtering/sorting feature at two layers:

- **UI** – Playwright driving the real page through a Page Object Model.
- **API** – REST Assured calling `GET /api/transactions` directly.

Only **one UI test** and **one API test** are implemented (see
[`docs/TEST_SCENARIOS.md`](docs/TEST_SCENARIOS.md) for what they cover). 

## Stack

- Java 21, Maven
- Cucumber 7 (`cucumber-java`, `cucumber-junit-platform-engine`) run through
  the JUnit 5 Platform (`junit-platform-suite`)
- Playwright (Java) for UI automation
- REST Assured for API automation
- Jackson (`jackson-databind`) to deserialize API responses into DTOs
- AssertJ for assertions
- OWNER (org.aeonbits) for typed configuration mapping


## Prerequisites

- **Java 21** on `PATH` (`java -version`)
- **Maven** on `PATH` (`mvn -v`)
- The Sales Analytics app running locally, e.g. at `http://localhost:3000`
  (see `../sales-analytics-qa-task/README.md` to start it)

## Install dependencies

```powershell
mvn -q dependency:resolve
```

Playwright also needs a browser binary (one-time setup per machine):

```powershell
mvn -q test-compile exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install --with-deps chromium"
```

## Run the tests

The application under test must already be running (default expected at
`http://localhost:3000`). Override the target with `-Dui.base.url=` /
`-Dapi.base.url=` if it runs elsewhere.

```powershell
mvn test                              # everything (API + UI)
mvn test "-Dcucumber.filter.tags=@api"  # API test only (fast, no browser)
mvn test "-Dcucumber.filter.tags=@ui"   # UI test only
mvn test "-Dui.headless=false"          # watch the browser run
```
## Reporting

Each run writes Cucumber reports to `target/cucumber-reports/`: `cucumber.html`
(browsable HTML summary), `cucumber.json`, and `cucumber.xml` (JUnit format for CI).

## Configuration

`src/test/resources/config.properties`:

| Key | Default | Meaning |
|---|---|---|
| `ui.base.url` | `http://localhost:3000` | page under test |
| `api.base.url` | `http://localhost:3000` | API base URI |
| `ui.headless` | `true` | Playwright headless mode |

Any key can be overridden with `-Dkey=value` on the Maven command line.


