# Sales Analytics – Test Scenarios

Scope: filtering (Date range, Region, Category) and sorting on the Sales
Analytics table, and the underlying `GET /api/transactions` endpoint.

---

## API Test Scenarios — `GET /api/transactions`

### NF-01 — No filters applied
**Type:** single case
**Steps:**
1. Send `GET /api/transactions?dateRange=none&region=none&category=none&sort=date_asc`
2. Verify the response

**Expected:** 200; `meta.returned=10`; table in this order:
- #1001 (2025-01-05, Europe, Furniture, 1200)
- #1002 (2025-01-12, North America, Appliances, 850)
- #1003 (2025-01-18, Asia, Lighting, 640)
- #1004 (2025-02-03, Europe, Appliances, 2300)
- #1005 (2025-02-14, North America, Furniture, 1550)
- #1006 (2025-02-20, Asia, Furniture, 980)
- #1007 (2025-03-01, Europe, Lighting, 760)
- #1008 (2025-03-11, North America, Appliances, 1430)
- #1009 (2025-03-18, Asia, Lighting, 2100)
- #1010 (2025-03-25, Europe, Furniture, 1680)

### PW-01 — Applied combined filters (parametrized)
**Request template:** `?dateRange={dateRange}&region={region}&category={category}&sort={sort}`


| Set | dateRange | region | category | sort | Expected result (full data, in order) |
|---|---|---|---|---|---|
| 1 | none | none | none | date_asc | Same as NF-01 |
| 2 | none | Europe | Furniture | date_desc | #1010 (2025-03-25, Europe, Furniture, 1680); #1001 (2025-01-05, Europe, Furniture, 1200) |
| 3 | none | North America | Appliances | rev_asc | #1002 (2025-01-12, North America, Appliances, 850); #1008 (2025-03-11, North America, Appliances, 1430) |
| 4 | none | Asia | Lighting | rev_desc | #1009 (2025-03-18, Asia, Lighting, 2100); #1003 (2025-01-18, Asia, Lighting, 640) |
| 5 | 7 | none | Furniture | rev_asc | #1010 (2025-03-25, Europe, Furniture, 1680) |
| 6 | 7 | Europe | none | rev_desc | #1010 (2025-03-25, Europe, Furniture, 1680) |
| 7 | 7 | North America | Lighting | date_asc | `meta.returned=0`; `data=[]` (No data available) |
| 8 | 7 | Asia | Appliances | date_desc | `meta.returned=0`; `data=[]` (No data available) |
| 9 | 30 | none | Appliances | rev_desc | #1008 (2025-03-11, North America, Appliances, 1430) |
| 10 | 30 | Europe | Lighting | rev_asc | #1007 (2025-03-01, Europe, Lighting, 760) |
| 11 | 30 | North America | none | date_desc | #1008 (2025-03-11, North America, Appliances, 1430) |
| 12 | 30 | Asia | Furniture | date_asc | `meta.returned=0`; `data=[]` (No data available) |
| 13 | 90 | none | Lighting | date_desc | #1009 (2025-03-18, Asia, Lighting, 2100); #1007 (2025-03-01, Europe, Lighting, 760); #1003 (2025-01-18, Asia, Lighting, 640) |
| 14 | 90 | Europe | Appliances | date_asc | #1004 (2025-02-03, Europe, Appliances, 2300) |
| 15 | 90 | North America | Furniture | rev_desc | #1005 (2025-02-14, North America, Furniture, 1550) |
| 16 | 90 | Asia | none | rev_asc | #1003 (2025-01-18, Asia, Lighting, 640); #1006 (2025-02-20, Asia, Furniture, 980); #1009 (2025-03-18, Asia, Lighting, 2100) |

### SF-01 — Applied Date range filter only (parametrized)
**Request template:** `?dateRange={dateRange}&region=none&category=none&sort=date_asc`

| Set | dateRange | Expected result (full data, in order) |
|---|---|---|
| 1 | 7 (Last 7 days) | #1010 (2025-03-25, Europe, Furniture, 1680) |
| 2 | 30 (Last 30 days) | #1007 (2025-03-01, Europe, Lighting, 760); #1008 (2025-03-11, North America, Appliances, 1430); #1009 (2025-03-18, Asia, Lighting, 2100); #1010 (2025-03-25, Europe, Furniture, 1680) |
| 3 | 90 (Last 90 days) | Same as NF-01 (all 10 rows) |

### SF-02 — Applied Region filter only (parametrized)
**Request template:** `?dateRange=none&region={region}&category=none&sort=date_asc`

| Set | region | Expected result (full data, in order) |
|---|---|---|
| 1 | Europe | #1001 (2025-01-05, Europe, Furniture, 1200); #1004 (2025-02-03, Europe, Appliances, 2300); #1007 (2025-03-01, Europe, Lighting, 760); #1010 (2025-03-25, Europe, Furniture, 1680) |
| 2 | North America | #1002 (2025-01-12, North America, Appliances, 850); #1005 (2025-02-14, North America, Furniture, 1550); #1008 (2025-03-11, North America, Appliances, 1430) |
| 3 | Asia | #1003 (2025-01-18, Asia, Lighting, 640); #1006 (2025-02-20, Asia, Furniture, 980); #1009 (2025-03-18, Asia, Lighting, 2100) |

### SF-03 — Applied Category filter only (parametrized)
**Request template:** `?dateRange=none&region=none&category={category}&sort=date_asc`

| Set | category | Expected result (full data, in order) |
|---|---|---|
| 1 | Furniture | #1001 (2025-01-05, Europe, Furniture, 1200); #1005 (2025-02-14, North America, Furniture, 1550); #1006 (2025-02-20, Asia, Furniture, 980); #1010 (2025-03-25, Europe, Furniture, 1680) |
| 2 | Appliances | #1002 (2025-01-12, North America, Appliances, 850); #1004 (2025-02-03, Europe, Appliances, 2300); #1008 (2025-03-11, North America, Appliances, 1430) |
| 3 | Lighting | #1003 (2025-01-18, Asia, Lighting, 640); #1007 (2025-03-01, Europe, Lighting, 760); #1009 (2025-03-18, Asia, Lighting, 2100) |

### CL-01 — Clear all filters
**Steps:**
1. Send `GET /api/transactions?region=Asia&sort=rev_desc` (pre-condition: filter + non-default sort applied)
2. Verify the response returns #1009 (2025-03-18, Asia, Lighting, 2100); #1006 (2025-02-20, Asia, Furniture, 980); #1003 (2025-01-18, Asia, Lighting, 640)
3. Send `GET /api/transactions?dateRange=none&region=none&category=none&sort=rev_desc` — the request the **Clear** action performs (all filters cleared, sort preserved)

**Expected:** all 10 rows ordered by revenue descending:
- #1004 (2025-02-03, Europe, Appliances, 2300)
- #1009 (2025-03-18, Asia, Lighting, 2100)
- #1010 (2025-03-25, Europe, Furniture, 1680)
- #1005 (2025-02-14, North America, Furniture, 1550)
- #1008 (2025-03-11, North America, Appliances, 1430)
- #1001 (2025-01-05, Europe, Furniture, 1200)
- #1006 (2025-02-20, Asia, Furniture, 980)
- #1002 (2025-01-12, North America, Appliances, 850)
- #1007 (2025-03-01, Europe, Lighting, 760)
- #1003 (2025-01-18, Asia, Lighting, 640)

---

## UI Test Scenarios

### NF-01 — No filters applied
**Type:** single case
**Steps:**
1. Open the app
2. Verify all 3 filters show "Not selected" and Sort by = "Date: Oldest → Newest"
3. Verify the table content

**Expected:** Table shows, in this order:
- #1001 (2025-01-05, Europe, Furniture, 1200)
- #1002 (2025-01-12, North America, Appliances, 850)
- #1003 (2025-01-18, Asia, Lighting, 640)
- #1004 (2025-02-03, Europe, Appliances, 2300)
- #1005 (2025-02-14, North America, Furniture, 1550)
- #1006 (2025-02-20, Asia, Furniture, 980)
- #1007 (2025-03-01, Europe, Lighting, 760)
- #1008 (2025-03-11, North America, Appliances, 1430)
- #1009 (2025-03-18, Asia, Lighting, 2100)
- #1010 (2025-03-25, Europe, Furniture, 1680)


### PW-01 — Applied combined filters (parametrized)
**Steps template:** Select Date range={dateRange}, Region={region}, Category={category}, Sort by={sort}, click **Apply**

| Set | dateRange | region | category | sort | Expected result (full data, in order) |
|---|---|---|---|---|---|
| 1 | Not selected | Not selected | Not selected | Date: Oldest → Newest | Same full table as NF-01 |
| 2 | Not selected | Europe | Furniture | Date: Newest → Oldest | #1010 (2025-03-25, Europe, Furniture, 1680); #1001 (2025-01-05, Europe, Furniture, 1200); footer "Showing 2 of 10 transactions" |
| 3 | Not selected | North America | Appliances | Revenue: Low → High | #1002 (2025-01-12, North America, Appliances, 850); #1008 (2025-03-11, North America, Appliances, 1430); footer "Showing 2 of 10" |
| 4 | Not selected | Asia | Lighting | Revenue: High → Low | #1009 (2025-03-18, Asia, Lighting, 2100); #1003 (2025-01-18, Asia, Lighting, 640); footer "Showing 2 of 10" |
| 5 | Last 7 days | Not selected | Furniture | Revenue: Low → High | #1010 (2025-03-25, Europe, Furniture, 1680); footer "Showing 1 of 10" |
| 6 | Last 7 days | Europe | Not selected | Revenue: High → Low | #1010 (2025-03-25, Europe, Furniture, 1680); footer "Showing 1 of 10" |
| 7 | Last 7 days | North America | Lighting | Date: Oldest → Newest | **"No data available"**; footer "Showing 0 of 10" |
| 8 | Last 7 days | Asia | Appliances | Date: Newest → Oldest | **"No data available"**; footer "Showing 0 of 10" |
| 9 | Last 30 days | Not selected | Appliances | Revenue: High → Low | #1008 (2025-03-11, North America, Appliances, 1430); footer "Showing 1 of 10" |
| 10 | Last 30 days | Europe | Lighting | Revenue: Low → High | #1007 (2025-03-01, Europe, Lighting, 760); footer "Showing 1 of 10" |
| 11 | Last 30 days | North America | Not selected | Date: Newest → Oldest | #1008 (2025-03-11, North America, Appliances, 1430); footer "Showing 1 of 10" |
| 12 | Last 30 days | Asia | Furniture | Date: Oldest → Newest | **"No data available"**; footer "Showing 0 of 10" |
| 13 | Last 90 days | Not selected | Lighting | Date: Newest → Oldest | #1009 (2025-03-18, Asia, Lighting, 2100); #1007 (2025-03-01, Europe, Lighting, 760); #1003 (2025-01-18, Asia, Lighting, 640); footer "Showing 3 of 10" |
| 14 | Last 90 days | Europe | Appliances | Date: Oldest → Newest | #1004 (2025-02-03, Europe, Appliances, 2300); footer "Showing 1 of 10" |
| 15 | Last 90 days | North America | Furniture | Revenue: High → Low | #1005 (2025-02-14, North America, Furniture, 1550); footer "Showing 1 of 10" |
| 16 | Last 90 days | Asia | Not selected | Revenue: Low → High | #1003 (2025-01-18, Asia, Lighting, 640); #1006 (2025-02-20, Asia, Furniture, 980); #1009 (2025-03-18, Asia, Lighting, 2100); footer "Showing 3 of 10" |

### SF-01 — Applied Date range filter only (parametrized)
**Steps template:** Select Date range={dateRange}, click **Apply**

| Set | dateRange | Expected result (full data, in order) |
|---|---|---|
| 1 | Last 7 days | #1010 (2025-03-25, Europe, Furniture, 1680); footer "Showing 1 of 10" |
| 2 | Last 30 days | #1007 (2025-03-01, Europe, Lighting, 760); #1008 (2025-03-11, North America, Appliances, 1430); #1009 (2025-03-18, Asia, Lighting, 2100); #1010 (2025-03-25, Europe, Furniture, 1680); footer "Showing 4 of 10" |
| 3 | Last 90 days | Same full table as NF-01 (all 10 rows); footer "Showing 10 of 10" |

### SF-02 — Applied Region filter only (parametrized)
**Steps template:** Select Region={region}, click **Apply**

| Set | region | Expected result (full data, in order) |
|---|---|---|
| 1 | Europe | #1001 (2025-01-05, Europe, Furniture, 1200); #1004 (2025-02-03, Europe, Appliances, 2300); #1007 (2025-03-01, Europe, Lighting, 760); #1010 (2025-03-25, Europe, Furniture, 1680); footer "Showing 4 of 10" |
| 2 | North America | #1002 (2025-01-12, North America, Appliances, 850); #1005 (2025-02-14, North America, Furniture, 1550); #1008 (2025-03-11, North America, Appliances, 1430); footer "Showing 3 of 10" |
| 3 | Asia | #1003 (2025-01-18, Asia, Lighting, 640); #1006 (2025-02-20, Asia, Furniture, 980); #1009 (2025-03-18, Asia, Lighting, 2100); footer "Showing 3 of 10" |

### SF-03 — Applied Category filter only (parametrized)
**Steps template:** Select Category={category}, click **Apply**

| Set | category | Expected result (full data, in order) |
|---|---|---|
| 1 | Furniture | #1001 (2025-01-05, Europe, Furniture, 1200); #1005 (2025-02-14, North America, Furniture, 1550); #1006 (2025-02-20, Asia, Furniture, 980); #1010 (2025-03-25, Europe, Furniture, 1680); footer "Showing 4 of 10" |
| 2 | Appliances | #1002 (2025-01-12, North America, Appliances, 850); #1004 (2025-02-03, Europe, Appliances, 2300); #1008 (2025-03-11, North America, Appliances, 1430); footer "Showing 3 of 10" |
| 3 | Lighting | #1003 (2025-01-18, Asia, Lighting, 640); #1007 (2025-03-01, Europe, Lighting, 760); #1009 (2025-03-18, Asia, Lighting, 2100); footer "Showing 3 of 10" |

### CL-01 — Clear all filters
**Steps:**
1. Set Sort by = "Revenue: High → Low"
2. Set Region = Asia
3. Click **Apply**
4. Verify the table shows #1009 (2025-03-18, Asia, Lighting, 2100); #1006 (2025-02-20, Asia, Furniture, 980); #1003 (2025-01-18, Asia, Lighting, 640)
5. Click **Clear**

**Expected:** Region reverts to "Not selected" (Date range/Category also "Not selected"); Sort by still shows "Revenue: High → Low" (not reset); table shows all 10 rows ordered by revenue descending:
- #1004 (2025-02-03, Europe, Appliances, 2300)
- #1009 (2025-03-18, Asia, Lighting, 2100)
- #1010 (2025-03-25, Europe, Furniture, 1680)
- #1005 (2025-02-14, North America, Furniture, 1550)
- #1008 (2025-03-11, North America, Appliances, 1430)
- #1001 (2025-01-05, Europe, Furniture, 1200)
- #1006 (2025-02-20, Asia, Furniture, 980)
- #1002 (2025-01-12, North America, Appliances, 850)
- #1007 (2025-03-01, Europe, Lighting, 760)
- #1003 (2025-01-18, Asia, Lighting, 640)

---

