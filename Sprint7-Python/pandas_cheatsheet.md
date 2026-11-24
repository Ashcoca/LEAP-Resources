# pandas Cheat Sheet

A companion to [pandas_fundamentals.ipynb](pandas_fundamentals.ipynb). It covers every pandas method used in the notebook, grouped in the notebook's order, and each group points to its notebook section. It targets **pandas 3.x**.

Examples use the notebook's `orders`, `customers` and `products` tables. `df` means any DataFrame and `s` means any Series.

```python
import numpy as np
import pandas as pd
```

---

## Contents

1. [Setup & options](#1-setup--options)
2. [Creating Series & DataFrames](#2-creating-series--dataframes) (§2–3)
3. [Inspecting data](#3-inspecting-data) (§3)
4. [Selecting & filtering](#4-selecting--filtering) (§4)
5. [Creating & changing columns](#5-creating--changing-columns) (§5)
6. [Copy-on-Write rules](#6-copy-on-write-rules) (§6)
7. [Missing data](#7-missing-data) (§7)
8. [dtypes & conversion](#8-dtypes--conversion) (§8)
9. [`.str` string methods](#9-str-string-methods) (§8)
10. [`.dt` datetime methods](#10-dt-datetime-methods) (§8, §13)
11. [Sorting, ranking & counting](#11-sorting-ranking--counting) (§9)
12. [Aggregations / reductions](#12-aggregations--reductions)
13. [GroupBy](#13-groupby) (§10)
14. [Combining: merge & concat](#14-combining-merge--concat) (§11)
15. [Reshaping](#15-reshaping) (§12)
16. [Time series](#16-time-series) (§13)
17. [Reading & writing files](#17-reading--writing-files) (§14)
18. [Method chaining](#18-method-chaining) (§15)
19. [Performance & memory](#19-performance--memory) (§16)
20. [NumPy helpers used with pandas](#20-numpy-helpers-used-with-pandas)
21. [Pitfalls quick reference](#21-pitfalls-quick-reference) (§16)
22. [Same query in Polars & DuckDB](#22-same-query-in-polars--duckdb) (§17)

---

## 1. Setup & options

| Method | What it does | Example |
|---|---|---|
| `pd.__version__` | installed pandas version | `print(pd.__version__)` |
| `pd.set_option(name, value)` | change a display or behaviour option | `pd.set_option("display.max_columns", 20)` |
| | round floats in printed output only | `pd.set_option("display.precision", 2)` |

---

## 2. Creating Series & DataFrames

| Method | What it does | Example |
|---|---|---|
| `pd.Series(values, index=, name=)` | 1-D labelled array | `pd.Series([10, 20], index=["a", "b"], name="sales")` |
| `pd.Series(dict)` | the keys become the index | `pd.Series({"a": 1, "b": 2})` |
| `pd.DataFrame(dict_of_lists)` | one column per key | `pd.DataFrame({"x": [1, 2], "y": [3, 4]})` |
| `pd.DataFrame(list_of_dicts)` | one row per dict; missing keys → `NaN` | `pd.DataFrame(records)` |
| `pd.DataFrame(array, columns=)` | from a 2-D NumPy array | `pd.DataFrame(np.arange(6).reshape(2, 3), columns=["x", "y", "z"])` |
| `pd.Categorical(values, categories=, ordered=)` | categorical values with a fixed (optionally ordered) set | `pd.Categorical(["M", "S"], categories=["S", "M", "L"], ordered=True)` |
| `pd.Timestamp(str)` | a single point in time | `pd.Timestamp("2026-01-01")` |
| `pd.to_timedelta(values, unit=)` | durations | `pd.to_timedelta([1, 7], unit="D")` |

### Series anatomy

| Attribute | Gives you |
|---|---|
| `s.values` | the underlying array |
| `s.index` | the row labels |
| `s.dtype` | the single dtype |
| `s.name` | the Series name (becomes the column name in a DataFrame) |
| `s["label"]` | the value for one label |

### Arithmetic with alignment

| Method | What it does | Example |
|---|---|---|
| `s1 + s2` | matches rows **by label**; labels found in only one Series → `NaN` | `jan + feb` |
| `s1.add(s2, fill_value=0)` | same, but treats a missing label as 0 (also `.sub`, `.mul`, `.div`) | `jan.add(feb, fill_value=0)` |

---

## 3. Inspecting data

Run the first six on every new dataset.

| Method / attribute | What it does |
|---|---|
| `df.shape` | `(rows, columns)` |
| `df.info()` | dtypes, non-null counts and memory per column |
| `df.describe()` | count, mean, std, min, quartiles and max for numeric/datetime columns |
| `df.isna().sum()` | missing values per column |
| `df.nunique()` | distinct values per column |
| `df.sample(n, random_state=)` | random rows (often more honest than `head`) |
| `df.head(n)` / `df.tail(n)` | first / last `n` rows (default 5) |
| `df.dtypes` | dtype of each column |
| `df.columns` / `df.columns.tolist()` | column labels / as a Python list |
| `df.index` | row labels |
| `len(df)` | number of rows |
| `s.unique()` | array of distinct values |
| `s.tolist()` | Series → Python list |
| `df1.equals(df2)` | `True` if same shape, values and dtypes |

---

## 4. Selecting & filtering

| Syntax | Selects by | Example |
|---|---|---|
| `df["col"]` | one column name → **Series** | `orders["quantity"]` |
| `df[["a", "b"]]` | list of names → **DataFrame** | `orders[["order_id", "quantity"]]` |
| `df[mask]` | boolean Series → rows | `orders[orders["quantity"] >= 4]` |
| `df.loc[rows, cols]` | **labels**; slices **include** the end | `by_id.loc[1001:1003, ["product_id", "quantity"]]` |
| `df.iloc[rows, cols]` | integer **positions**; slices **exclude** the end | `orders.iloc[-3:, :2]` |
| `df.loc[mask, cols]` | filter rows and pick columns in one step | `orders.loc[orders["discount"] == 0.2, ["order_id", "discount"]]` |
| `df.set_index("col")` | make a column the row index | `orders.set_index("order_id")` |
| `df.reset_index(drop=False)` | turn the index back into a column (`drop=True` discards it) | `summary.reset_index()` |

### Building masks

| Method | What it does | Example |
|---|---|---|
| `&`, `\|`, `~` | and / or / not. **Wrap each condition in parentheses.** | `(df["q"] >= 4) & (df["discount"] > 0)` |
| `s.isin(list)` | membership | `orders["product_id"].isin(["P01", "P04"])` |
| `s.between(lo, hi)` | inclusive range (numbers or dates) | `orders["order_date"].between("2026-03-01", "2026-03-07")` |
| `mask.sum()` | how many rows match (`True` = 1) | `(orders["quantity"] >= 4).sum()` |
| `mask.mean()` | share of rows that match | `mentions.mean()` |
| `df.query("expr")` | SQL-like filter string; `@var` references a Python variable | `orders.query("quantity >= @min_qty and product_id in ['P01', 'P04']")` |

---

## 5. Creating & changing columns

| Method | What it does | Example |
|---|---|---|
| `df["new"] = expr` | add or overwrite a column **in place** | `orders["revenue"] = orders["quantity"] * orders["unit_price"]` |
| `df.assign(new=lambda d: ...)` | return a **new** DataFrame with the added columns (use it in chains) | `orders.assign(is_large=lambda d: d["quantity"] >= 4)` |
| `s.map(dict)` | replace values via a lookup; unmapped values → `NaN` | `orders["discount"].map({0.0: "none", 0.1: "10% off"})` |
| `pd.cut(values, bins=, labels=)` | bin numbers into labelled intervals (right edge inclusive) | `pd.cut(orders["quantity"], bins=[0, 1, 3, 5], labels=["single", "few", "many"])` |
| `df.apply(func, axis=1)` | call a Python function per row. **Slow**: use only when no vectorized option exists | `orders.apply(lambda r: r["a"] * r["b"], axis=1)` |
| `df.rename(columns={old: new})` | rename columns | `orders.rename(columns={"unit_price": "price"})` |
| `df.drop(columns=[...])` | remove columns (`index=[...]` removes rows) | `orders.drop(columns=["tier", "size"])` |
| `s.round(n)` / `df.round(n)` | round values | `wide.round(0)` |

See [§20](#20-numpy-helpers-used-with-pandas) for `np.where` / `np.select` conditional columns.

---

## 6. Copy-on-Write rules

In pandas 3, every indexing result behaves like an independent copy.

```python
df[df["discount"] > 0]["revenue"] = 0        # ❌ changes a temporary copy; ChainedAssignmentError warning
df.loc[df["discount"] > 0, "revenue"] = 0    # ✅ one .loc call on the original
```

| Method | When |
|---|---|
| `df.copy()` | you take a subset and **mean** to modify it separately |
| `df = df.method()` | prefer this to `inplace=True` (it's not faster and doesn't chain) |

---

## 7. Missing data

Missing values show as `NaN` (floats), `NaT` (datetimes) or `pd.NA` (nullable dtypes). Reductions skip them by default.

| Method | What it does | Example |
|---|---|---|
| `s.isna()` / `df.isna()` | `True` where missing (never use `== np.nan`) | `orders["quantity"].isna().sum()` |
| `s.count()` | number of **non-missing** values (`len` counts all) | `orders["quantity"].count()` |
| `df.dropna(subset=[...])` | drop rows with missing values in those columns | `orders.dropna(subset=["quantity"])` |
| `s.fillna(value)` | replace missing with a constant or statistic | `orders["quantity"].fillna(orders["quantity"].median())` |
| `s.ffill()` / `s.bfill()` | carry the last / next known value (replaces the removed `fillna(method=...)`) | `readings.ffill()` |
| `s.interpolate()` | fill numerically between known values | `readings.interpolate()` |
| `s.astype("Int64")` | **nullable** integer that can hold `pd.NA` (also `"Float64"`, `"boolean"`) | `orders["quantity"].fillna(1).astype("Int64")` |

---

## 8. dtypes & conversion

| dtype | Holds |
|---|---|
| `int64`, `float64` | numbers (fast NumPy types; ints can't hold `NaN`) |
| `Int64`, `Float64`, `boolean` | nullable numbers / bools (`pd.NA`) |
| `str` | text (pandas 3 default string dtype) |
| `datetime64[ns]` | timestamps → `.dt` accessor |
| `category` | small set of repeated values |
| `object` | any Python object (slow; usually means "needs cleaning") |

| Method | What it does | Example |
|---|---|---|
| `s.astype(dtype)` | convert to a dtype | `d["status"].str.lower().astype("category")` |
| `pd.to_numeric(s, errors="coerce")` | text → numbers; bad values → `NaN` | `pd.to_numeric(d["price"].str.replace(r"[$,]", "", regex=True), errors="coerce")` |
| `pd.to_datetime(s, errors="coerce")` | text → datetimes; bad values → `NaT` | `pd.to_datetime(d["date"], errors="coerce")` |
| `pd.Series(categorical).sort_values()` | ordered categories sort by their defined order, not alphabetically | `S < M < L` |

---

## 9. `.str` string methods

All are vectorized and keep missing values missing.

| Method | What it does | Example |
|---|---|---|
| `s.str.strip()` | trim surrounding whitespace | `" toronto ".strip()` → `"toronto"` |
| `s.str.lower()` / `s.str.title()` | change case | `"AUSTIN"` → `"austin"` / `"Austin"` |
| `s.str.replace(pat, repl, regex=True)` | regex substitution | `phones.str.replace(r"\D", "", regex=True)` |
| `s.str.contains(pat, case=False)` | boolean: does the pattern match? | `reviews["review_text"].str.contains(r"price\|\$", case=False)` |
| `s.str.len()` | length of each string | `reviews["review_text"].str.len()` |
| `s.str[:n]` | slice each string | `d["review_text"].str[:100]` |

---

## 10. `.dt` datetime methods

| Method / attribute | Gives you |
|---|---|
| `s.dt.year`, `.month`, `.quarter` | integer parts |
| `s.dt.day_of_week` | 0 = Monday … 6 = Sunday (`dayofweek` is deprecated) |
| `s.dt.day_name()` | `"Monday"`, … |
| `s.dt.isocalendar().week` | ISO week number |
| `s.dt.date` | Python `date` (drops the time) |
| `s.dt.to_period("M")` | month period like `2026-03` (great as a group key) |
| `index.day_name()` | same as `.dt.day_name()`, but on a `DatetimeIndex` |

---

## 11. Sorting, ranking & counting

| Method | What it does | Example |
|---|---|---|
| `df.sort_values(col, ascending=)` | sort rows by a column | `orders.sort_values("revenue", ascending=False)` |
| `df.sort_values([c1, c2], ascending=[True, False])` | multi-key sort | `orders.sort_values(["order_date", "revenue"], ascending=[True, False])` |
| `s.sort_index()` | sort by the index labels | `reviews["score"].value_counts().sort_index()` |
| `df.nlargest(n, col)` / `nsmallest` | top / bottom `n` rows (faster than sort + head) | `orders.nlargest(3, "revenue")` |
| `s.value_counts()` | count of each distinct value, descending | `orders["product_id"].value_counts()` |
| `s.value_counts(normalize=True)` | proportions instead of counts | `orders["discount"].value_counts(normalize=True)` |
| `s.rank(ascending=, method="min")` | rank each value (ties share the lowest rank) | `orders["revenue"].rank(ascending=False, method="min")` |
| `s.idxmax()` / `idxmin()` | **label** of the largest / smallest value | `monthly["Furniture"].idxmax()` |

---

## 12. Aggregations / reductions

Each one works on a Series, on a DataFrame (per column), and after a `groupby`.

| Method | | Method | |
|---|---|---|---|
| `sum()` | total | `count()` | non-missing count |
| `mean()` | average | `nunique()` | distinct count |
| `median()` | middle value | `max()` / `min()` | extremes |
| `cumsum()` | running total | `describe()` | summary bundle |

---

## 13. GroupBy

Pattern: `df.groupby(KEY)[COLUMN(S)].AGGREGATION()`

| Method | Output shape | Example |
|---|---|---|
| `df.groupby(key)[col].sum()` | one row per group | `orders.groupby("product_id")["revenue"].sum()` |
| `df.groupby([k1, k2])` | MultiIndex result; follow with `.reset_index()` | `orders.groupby(["product_id", "discount"])["revenue"].sum().reset_index()` |
| `df.groupby(series)` | group by any Series, e.g. a derived month | `orders.groupby(orders["order_date"].dt.to_period("M"))` |
| `.agg(name=(col, func))` | **named aggregation**: several outputs, your column names | see below |
| `.agg(["sum", "mean"])` | several functions on one column | `daily.resample("ME").agg(["sum", "mean", "max"])` |
| `.transform(func)` | **same shape as the input**: a group statistic on every row | `orders.groupby("product_id")["revenue"].transform("sum")` |
| `.filter(lambda g: ...)` | keep or drop **whole groups** → subset of the original rows | `orders.groupby("customer_id").filter(lambda g: len(g) >= 15)` |
| `groupby(..., observed=True)` | only categories that actually appear (the default for categoricals in pandas 3) | `reviews.groupby("sentiment", observed=True)` |
| `groupby(level=0)` | group by the index | `pd.concat(totals).groupby(level=0).sum()` |

```python
orders.groupby("product_id").agg(
    n_orders     = ("order_id", "count"),
    revenue      = ("revenue", "sum"),
    avg_discount = ("discount", "mean"),
    last_order   = ("order_date", "max"),
)
```

---

## 14. Combining: merge & concat

### `merge` = SQL JOIN

```python
orders.merge(customers, on="customer_id", how="left", validate="many_to_one", indicator=True)
```

| Parameter | Meaning |
|---|---|
| `on="key"` | key column(s) present in both tables |
| `left_on=` / `right_on=` | key columns with different names |
| `how="inner"` (default) | keys in both |
| `how="left"` / `"right"` | all rows of one side; `NaN` where there's no match |
| `how="outer"` | everything from both |
| `validate="one_to_one"` / `"many_to_one"` / `"one_to_many"` | raise `pd.errors.MergeError` if the keys break that relationship |
| `indicator=True` | adds `_merge` (`both` / `left_only` / `right_only`) for debugging |
| `suffixes=("_l", "_r")` | rename clashing column names |

### `concat`: stack without matching keys

| Method | What it does | Example |
|---|---|---|
| `pd.concat([df1, df2], ignore_index=True)` | stack rows; columns are unioned; missing → `NaN` | `pd.concat([jan_df, feb_df], ignore_index=True)` |
| `pd.concat(generator)` | stack a folder of files | `pd.concat((pd.read_csv(p) for p in Path("data").glob("*.csv")), ignore_index=True)` |

---

## 15. Reshaping

| Method | Direction | Example |
|---|---|---|
| `df.pivot_table(index=, columns=, values=, aggfunc=)` | long → wide, aggregating duplicates | `full.pivot_table(index="region", columns="category", values="revenue", aggfunc="sum")` |
| `pivot_table(..., margins=True, margins_name="Total")` | add row and column totals | |
| `df.pivot(index=, columns=, values=)` | long → wide **without** aggregation (raises an error on duplicates) | |
| `df.melt(id_vars=, var_name=, value_name=)` | wide → long | `wide.reset_index().melt(id_vars="region", var_name="category", value_name="revenue")` |
| `pd.crosstab(a, b, normalize=)` | frequency table of two columns (`normalize="index"` → row %) | `pd.crosstab(full["region"], full["discount_label"], normalize="index")` |
| `s.unstack()` / `df.stack()` | move an index level to the columns / back | `df.groupby(["a", "b"])["x"].sum().unstack()` |

---

## 16. Time series

These need a `DatetimeIndex`, or `on="date_col"` for `resample`/`rolling`.

| Method | What it does | Example |
|---|---|---|
| `s.asfreq("D", fill_value=0)` | make every calendar day present | `orders.groupby("order_date")["revenue"].sum().asfreq("D", fill_value=0)` |
| `s.resample(rule).agg()` | groupby over time buckets | `daily.resample("W").sum()` |
| `s.rolling(n).mean()` | moving window (n rows) | `ts["revenue"].rolling(7).mean()` |
| `s.shift(n)` | value from `n` rows earlier | `ts["revenue"].shift(1)` |
| `s.diff()` | change from the previous row | `ts["revenue"].diff()` |
| `s.pct_change()` | % change from the previous row | `monthly_rev.pct_change() * 100` |
| `s.cumsum()` | running total | `ts["revenue"].cumsum()` |
| `s.loc["2026-03"]` | partial-string date slicing | `daily.loc["2026-03-01":"2026-03-15"]` |
| `df.plot()` | matplotlib line chart (needs `matplotlib`) | `ts[["revenue", "rolling_7d"]].plot(figsize=(10, 4))` |

**Frequency aliases (pandas 3):** `"D"` day · `"W"` week · `"ME"` month end · `"QE"` quarter end · `"YE"` year end · `"h"` hour. Old `"M"`/`"Q"`/`"Y"`/`"H"` are deprecated.

---

## 17. Reading & writing files

| Read | Write | Notes |
|---|---|---|
| `pd.read_csv(path)` | `df.to_csv(path, index=False)` | universal, untyped |
| `pd.read_excel(path)` | `df.to_excel(path)` | needs `openpyxl` |
| `pd.read_json(path)` | `df.to_json(path)` | |
| `pd.json_normalize(records)` | | flattens nested dicts to `user.address.city` columns |
| `pd.read_sql(query, conn)` | `df.to_sql(name, conn)` | SQLAlchemy / DB-API connection |
| `pd.read_parquet(path)` | `df.to_parquet(path)` | typed, compressed, fast; needs `pyarrow` |

### Useful `read_csv` options

```python
pd.read_csv(
    "file.csv",
    usecols=["order_id", "order_date", "quantity"],     # load only these columns
    dtype={"product_id": "category", "quantity": "Int64"},
    parse_dates=["order_date"],
    index_col="order_id",
    sep=";", encoding="utf-8", na_values=["N/A", "-"],
    thousands=",", skiprows=2, nrows=1000, on_bad_lines="warn",
)
```

| Option | Use |
|---|---|
| `chunksize=n` | returns an iterator of DataFrames for files bigger than memory |
| `io.StringIO(text)` | treat a string as a file (handy for tests and demos) |

```python
totals = [chunk.groupby("product_id")["revenue"].sum()
          for chunk in pd.read_csv("big.csv", chunksize=250_000)]
pd.concat(totals).groupby(level=0).sum()
```

---

## 18. Method chaining

| Method | What it does |
|---|---|
| `( df .a() .b() .c() )` | wrap the chain in parentheses, one step per line |
| `.assign(col=lambda d: ...)` | add columns mid-chain (`d` is the DataFrame at that point) |
| `.query("...")` | filter mid-chain |
| `.pipe(func, **kwargs)` | put your own `func(df, ...) -> df` into the chain |

```python
(orders
    .merge(customers, on="customer_id", how="inner", validate="many_to_one")
    .query("discount > 0")
    .assign(month=lambda d: d["order_date"].dt.to_period("M"))
    .groupby(["region", "month"])["revenue"].sum()
    .groupby("region").mean()
    .sort_values(ascending=False))
```

---

## 19. Performance & memory

**Speed hierarchy (fastest first):** vectorized pandas methods → NumPy functions (`np.where`, `np.select`) → `.map(dict)` → `s.apply(func)` → `df.apply(func, axis=1)` / `iterrows()`.

| Method | What it does |
|---|---|
| `df.iterrows()` | yields `(index, row Series)`. Very slow; avoid it |
| `df.itertuples()` | yields namedtuples. Much faster if you really must loop |
| `df.memory_usage(deep=True)` | bytes per column (`deep` counts string contents) |
| `s.astype("category")` | big memory saving for repeated text values |

---

## 20. NumPy helpers used with pandas

| Function | What it does | Example |
|---|---|---|
| `np.where(cond, a, b)` | two-way conditional column | `np.where(orders["revenue"] >= 100, 0.0, 9.99)` |
| `np.select(conds, choices, default=)` | multi-way conditional (first match wins) | `np.select([rev >= 2000, rev >= 300], ["high", "medium"], default="low")` |
| `np.nan` | the float missing value | `quantity[idx] = np.nan` |
| `np.arange(n).reshape(r, c)` | test arrays | `np.arange(6).reshape(2, 3)` |
| `np.random.default_rng(seed)` | reproducible random generator | `rng.integers(1, 6, size=n)`, `rng.choice([...], size=n)` |

> Watch out: `NaN >= 100` is `False`, so missing values silently fall into the "else" branch of `np.where` / `np.select`.

---

## 21. Pitfalls quick reference

| Symptom | Cause | Fix |
|---|---|---|
| assignment does nothing, `ChainedAssignmentError` | `df[m]["c"] = v` | `df.loc[m, "c"] = v` |
| `ValueError: truth value of a Series is ambiguous` | `and` / `or` in a mask | `&`, `\|`, `~` with parentheses |
| surprise `NaN` after arithmetic | index alignment | check the indexes; `reset_index(drop=True)` |
| row count grows after `merge` | duplicate keys | `validate="many_to_one"` |
| `sum()` concatenates strings | numbers stored as text | `pd.to_numeric(errors="coerce")` |
| can't select a grouped-by column | it became the index | `.reset_index()` or `groupby(..., as_index=False)` |
| `x == np.nan` is always `False` | `NaN` never equals anything | `.isna()` |
| one row too many when slicing | `.loc` includes the end | use `.iloc` for positional slices |
| ints turned into floats | `NaN` in an int column | nullable `"Int64"` |
| `MemoryError` | data too big | `usecols`, `dtype=`, `category`, `chunksize`, Parquet, Polars/DuckDB |

---

## 22. Same query in Polars & DuckDB

The concepts transfer; only the syntax changes.

```python
# pandas
orders.groupby("product_id")["revenue"].sum().sort_values(ascending=False)

# Polars (lazy, multi-threaded)
import polars as pl
(pl.from_pandas(orders).lazy()
   .group_by("product_id").agg(pl.col("revenue").sum())
   .sort("revenue", descending=True)
   .collect())

# DuckDB (SQL that can query a pandas DataFrame by its variable name)
import duckdb
duckdb.sql("SELECT product_id, SUM(revenue) AS revenue FROM orders GROUP BY 1 ORDER BY 2 DESC").df()
```
