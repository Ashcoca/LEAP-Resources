# Bookshelf - MyBatis demo

This repository contains a small SpringBoot service that answers two HTTP queries from a real SQL database:

- `GET /books/{id}` - one book (annotation-based mapper, single table)
- `GET /authors/{id}/books` - an author's books (XML-based mapper, two-table join)

## Usage

Requires `Java 21` and `Maven.` No database install needed - it uses H2 DB, which is an **embedded SQL database**. It is seeded automatically from `schema.sql` + `data.sql` when you run the following command:

```
mvn spring-boot:run
```

Then:

    curl http://localhost:8080/books/1
    curl -s -o /dev/null -w "%{http_code}\n" http://localhost:8080/books/999   # 404
    curl http://localhost:8080/authors/1/books

#### OPTIONAL: Want to experiment with real PostgreSQL instead

1. Create the database locally.
2. Change three lines in `application.properties`:

    createdb bookshelf  # or: CREATE DATABASE bookshelf; in psql

```
    # spring.datasource.url=jdbc:postgresql://localhost:5432/bookshelf
    # spring.datasource.username=postgres
    # spring.datasource.password=postgres
```

Comment out the three `spring.datasource.*` H2 lines and uncomment these.

Every line of Java stays the same — the code never knew which database it was.

## Where Is the Magic?

| What | Where |
|---|---|
| Wiring: dependency, properties, `@MapperScan` | `pom.xml`, `application.properties`, `BookshelfApplication.java` |
| Annotation mapper (one line of SQL, one table) | `mapper/BookMapper.java` |
| XML mapper (join, alias, ORDER BY) | `mapper/AuthorMapper.java` + `resources/mappers/AuthorMapper.xml` |
| Result classes (plain beans, no annotations) | `model/Book.java`, `model/BookSummary.java` |
| The controller (unchanged Spring MVC) | `controller/BookshelfController.java` |
