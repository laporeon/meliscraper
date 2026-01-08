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
    - [Configuring](#configuring)
        - [.env](#env)
- [Usage](#usage)
    - [Starting](#starting)
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

### Configuring

#### **.env**

Using Docker, `.env` file is optional since default values exist in [Docker Compose file](./docker-compose.yml). For
local development **without Docker**, you must create the PostgreSQL database manually and set `POSTGRES_USER` and
`POSTGRES_PASSWORD` environment variables for PostgreSQL authentication. Other parameters have sensible defaults.

Rename  `.env.example` to `.env` and modify variables according to your needs.

| Variable          | For Docker                        | For Local Development             | Description         |
|-------------------|-----------------------------------|-----------------------------------|---------------------|
| PORT              | Optional (Default: "8080")        | Optional (Default: "8080")        | Server port         |
| POSTGRES_USER     | Optional (Default: "admin")       | **Required**                      | PostgreSQL username |
| POSTGRES_PASSWORD | Optional (Default: "password")    | **Required**                      | PostgreSQL password |
| POSTGRES_DB       | Optional (Default: "meliscraper") | Optional (Default: "meliscraper") | PostgreSQL database |

## Usage

### **Starting**

For the fastest setup, it is recommended to use Docker Compose to start the app and its services:

```bash
# Run docker compose command to start all services
$ docker compose up -d --build
```

Access the application at `http://localhost:8080/docs` (or the port you configured).

### **Routes**

| Route | HTTP Method | Params                                                                                                                                                                                                                                                            | Description | Auth Method |
|-------------------------------------|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------|-------------|
| `/docs` | GET | -                                                                                                                                                                                                                                                                 | Swagger documentation | None |
| `/api/v1/snapshots` | GET | **Query Parameters:**<br> • `page` - Page number (default: 0)<br> • `size` - Page size (default: 10)                                                                                                               | Get all snapshots summary sorted by date in ascending order | None |
| `/api/v1/snapshots/today` | GET | -                                                                                                                                                                                                                                                                 | Get today's snapshot | None |
| `/api/v1/snapshots/:date` | GET | **Path Parameters:**<br> • `date` - Snapshot date in yyyy-MM-dd format                                                                                                                                                                                            | Find snapshot by specific date | None |
| `/api/v1/snapshots/:date` | DELETE | **Path Parameters:**<br> • `date` - Snapshot date in yyyy-MM-dd format                                                                                                                                                                                            | Delete snapshot by date | None |
| `/api/v1/categories` | GET | **Query Parameters:**<br> • `page` - Page number (default: 0)<br> • `size` - Page size (default: 10)                                                                                                                                                              | Get list of categories (name and slug only) sorted by name in ascending order | None |
| `/api/v1/categories/:slug/products` | GET | **Path Parameters:**<br> • `slug` - Category slug identifier<br> **Query Parameters:**<br> • `page` - Page number (default: 0)<br> • `size` - Page size (default: 10)<br> • `orderBy` - Sort field (default: "name")<br> • `direction` - Sort direction: ASC/DESC (default: "ASC") | List products by category slug | None |

[⬆ Back to the top](#-meliscraper)
