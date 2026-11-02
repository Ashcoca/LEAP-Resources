## SQL Prompts

Asking AI to simply, "write me a SQL query," produces generic results. If you want better SQL, give better **context**.

**The quality of AI-generated SQL depends far more on the context you provide than the model you use!!!**

Here are some SQL prompts and advice to use that will help you produce much more reliable results.


### 1. Query Generation

You are helping me write SQL for [Postgres / Snowflake / BigQuery / SQL Server].  

Schema: [Paste the relevant CREATE TABLE statements or list of columns with data types.]  

Business Question: [Describe exactly what you need, including business rules and edge cases.]  

Write the SQL query.  

After writing the query, explain every assumption you made about the schema, relationships, and business logic that I should verify before using it. 

### 2. Explain Legacy SQL

Explain this SQL query in plain English.  Break it down step by step.  

For every CTE, subquery, JOIN, CASE statement, and window function, explain: 
1. What it does 
2. Why it exists 
3. What data it produces  

Then identify anything fragile, redundant, or risky. 

SQL: [Paste query] 

### 3. SQL Performance Optimization

I'm using [Database].  Below is my SQL query and its EXPLAIN (or EXPLAIN ANALYZE) output.  

Identify the actual performance bottleneck.  Recommend the smallest change with the biggest impact and explain the trade-offs.  

Query: [Paste query]  

Execution Plan: [Paste EXPLAIN output] 

### 4. Production Safety Check

Before I run this SQL in production, review it like a senior database engineer.  

Check for: 1. Cartesian joins 2. Duplicate rows 3. Missing WHERE clause 4. Incorrect JOIN conditions 5. NULL handling 6. Timezone issues 7. Performance risks  

SQL: [Paste query] 

### 5. SQL Dialect Conversion

Convert the following SQL from [Source Database] to [Target Database].  

Preserve the business logic exactly.  

Explain any syntax or functions that don't have a direct equivalent.  

SQL: [Paste code] 

### 6. Generate Documentation

Using the schema and SQL below, generate technical documentation.  

Include: 1. Table purpose 2. Column descriptions 3. Business rules 4. Transformation logic 5. Important joins 6. Filtering conditions 7. Assumptions 8. Data quality concerns  

Schema: [Paste schema]  

SQL: [Paste query] 


### 7. Data Quality Test Generation

Given this schema and these business rules, generate SQL-based validation checks for: null violations, duplicate/uniqueness violations, referential integrity, row-count reconciliation between source and target, and any business-rule-specific edge cases. 

For each check, explain what a failure would actually indicate and how severe it is.