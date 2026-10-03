# Java-SpringBoot-Ecom

Spring Boot 3 REST API for an e-commerce storefront. Pairs with [Angular-Frontend-Ecom](https://github.com/ahsan-shahbaz/Angular-Frontend-Ecom).

## Features

- JWT authentication (register, login) with Spring Security
- Product catalogue: list, detail, search, categories, featured, admin create/update/delete
- Cart, wishlist and order endpoints
- Admin dashboard statistics
- Centralised error handling with a consistent error response
- SQL Server persistence via Spring Data JPA

## Stack

Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA, SQL Server, JJWT, Maven.

## API overview

Base path `/api` (port 8080).

| Area | Endpoints |
|---|---|
| Auth | `POST /auth/register`, `POST /auth/login` |
| Products | `GET /products`, `/products/{id}`, `/products/search`, `/products/categories`, `/products/featured`, `/products/category/{category}`; admin `PUT`/`DELETE` |
| Cart | `/cart` (add, update and remove items) |
| Wishlist | `/wishlist/{productId}` |
| Orders | `/orders`, `/orders/{id}` |
| Admin | `GET /admin/stats` |

## Run locally

Requires JDK 17+, Maven and a SQL Server database named `EcommerceDB`.

```bash
export DB_USERNAME=sa
export DB_PASSWORD=<your-password>
export JWT_SECRET=<long-random-string>
mvn spring-boot:run
```

Secrets are read from environment variables; see `.env.example`. CORS allows `http://localhost:4200` and `4201` by default (`app.cors.allowed-origins`).

## Tests and CI

`mvn -B package` runs in GitHub Actions on every push (see `.github/workflows/maven.yml`).

## Security

See [SECURITY.md](SECURITY.md). Do not commit credentials.
