<h1 align=center> Meliscraper

![java](https://img.shields.io/static/v1?label=java&message=21.0.9&labelColor=2d3748&color=grey&logo=openjdk&logoColor=white&style=flat)
![spring boot](https://img.shields.io/static/v1?label=spring%20boot&message=4.0.0&labelColor=2d3748&color=grey&logo=springboot&logoColor=white&style=flat)
![postgresql](https://img.shields.io/static/v1?label=postgres&message=18.0.0&labelColor=2d3748&color=grey&logo=postgresql&logoColor=white&style=flat)
![docker](https://img.shields.io/static/v1?label=docker&message=29.1.2&labelColor=2d3748&color=grey&logo=docker&logoColor=white&style=flat)
![swagger](https://img.shields.io/static/v1?label=swagger&message=2.8.13&labelColor=2d3748&color=grey&logo=swagger&logoColor=white&style=flat)

</h1>

## Table of Contents

- [About](#about)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
  - [Running with Docker (Recommended)](#running-with-docker-recommended)
  - [Running Locally (Without Docker)](#running-locally-without-docker)
  - [Environment Variables Reference](#environment-variables-reference)
- [Usage](#usage)
    - [Routes](#routes)

## About

Meliscraper is a web scraping API designed to monitor and track Mercado Libre's best-selling products. It captures a
complete picture of the best sellers across all categories, storing them in a structured database.

**Key features:**

- **Flexible Snapshot Retrieval**: Get today's full snapshot or list all available snapshots summary.
- **Category-based Navigation**: List all available categories and retrieve products filtered by category slug.
- **Historical Data Tracking**: Query snapshots by specific dates.
- **OpenAPI Documentation**: Full Swagger UI documentation with examples and error responses.

## Requirements:

**For Docker (Recommended):**

- Docker & Docker Compose

**For Local Development:**

- Java 21+
- Maven 3.9+
- PostgreSQL

## Getting Started

### Running with Docker (Recommended)

This is the simplest setup. Docker Compose will automatically build and start all required services, using default values in the [Compose file](./docker-compose.yml).

```bash
docker compose up -d --build
```

Then access the application at `http://localhost:8080/` (or the port you configured).

> [!NOTE]
> **Optionally**, you can override any environment variables with your own settings.

### Running Locally (Without Docker)

1. Create a PostgreSQL database 
2. Set required environment variables:
```bash
export POSTGRES_USER=<your-user-here>  
export POSTGRES_PASSWORD=<your-password-here>  
export POSTGRES_DB=<database-name>
```
3. (Optional) Set server port
```bash
export PORT=8081
```
4. Start the application: 
```bash
mvn spring-boot:run
```
5. Access at `http://localhost:8080/` (or the port you configured).

### Environment Variables Reference

| Variable          | For Docker                        | For Local Development         | Description                   |
|-------------------|-----------------------------------|------------------------------|-------------------------------|
| PORT              | Optional (Default: "8080")        | Optional (Default: "8080")   | Server port                   |
| POSTGRES_USER     | Optional (Default: "admin")       | **Required**                 | PostgreSQL username           |
| POSTGRES_PASSWORD | Optional (Default: "password")    | **Required**                 | PostgreSQL password           |
| POSTGRES_DB       | Optional (Default: "meliscraper") | **Required** (must match the manually created database) | PostgreSQL database name      |


## Usage

Once the application is running, you can interact via Swagger UI or directly through HTTP requests.

### **Routes**

| Route                               | HTTP Method | Params                                                                                                                                                                                                                                                                             | Description                                                                   | Auth Method |
|-------------------------------------|-------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------|-------------|
| `/docs`                             | GET         | -                                                                                                                                                                                                                                                                                  | Swagger documentation                                                         | None        |
| `/api/v1/snapshots`                 | GET         | **Query Parameters:**<br> • `page` - Page number (default: 0)<br> • `size` - Page size (default: 10)                                                                                                                                                                               | Get all snapshots summary sorted by date in ascending order                   | None        |
| `/api/v1/snapshots/today`           | GET         | -                                                                                                                                                                                                                                                                                  | Get today's snapshot                                                          | None        |
| `/api/v1/snapshots/:date`           | GET         | **Path Parameters:**<br> • `date` - Snapshot date in yyyy-MM-dd format                                                                                                                                                                                                             | Find snapshot by specific date                                                | None        |
| `/api/v1/snapshots/:date`           | DELETE      | **Path Parameters:**<br> • `date` - Snapshot date in yyyy-MM-dd format                                                                                                                                                                                                             | Delete snapshot by date                                                       | None        |
| `/api/v1/categories`                | GET         | **Query Parameters:**<br> • `page` - Page number (default: 0)<br> • `size` - Page size (default: 10)                                                                                                                                                                               | Get list of categories (name and slug only) sorted by name in ascending order | None        |
| `/api/v1/categories/:slug/products` | GET         | **Path Parameters:**<br> • `slug` - Category slug identifier<br> **Query Parameters:**<br> • `page` - Page number (default: 0)<br> • `size` - Page size (default: 10)<br> • `orderBy` - Sort field (default: "name")<br> • `direction` - Sort direction: ASC/DESC (default: "ASC") | List products by category slug                                                | None        |


[⬆ Back to the top](#-meliscraper)
