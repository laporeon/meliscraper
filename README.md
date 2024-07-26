<h1 align="center"> Meliscraper</h1>

![node](https://img.shields.io/static/v1?label=node&message=18.14.0&color=2d3748&logo=node.js&style=flat-square)
![postgres](https://img.shields.io/static/v1?label=postgres&message=12.15&color=2d3748&logo=postgresql&style=flat-square)
![mongodb](https://img.shields.io/static/v1?label=mongodb&message=6.0.6&color=2d3748&logo=mongodb&style=flat-square)
![prisma](https://img.shields.io/static/v1?label=prisma&message=4.13.0&color=2d3748&logo=prisma&style=flat-square)
[![eslint](https://img.shields.io/badge/eslint-8.31.0-4b32c3?style=flat-square&logo=eslint)](https://eslint.org/)
[![MIT License](https://img.shields.io/badge/license-MIT-green?style=flat-square)](https://raw.githubusercontent.com/daspeon/meliscraper/main/LICENSE)

Meliscraper is a web scraper for Mercado Livre best sellers. This isn't a very complex project, but it is my first most complete project. I tried to implement simple things that I never used before, such as linters, set up CORS and a better way to deal with logs. I also decide to use two databases: PostgreSQL for relational data and MongoDB for application logs.

## Table of Contents

- [Technologies](#technologies)
- [Requirements](#requirements)
- [Installing](#installing)
  - [Configuring](#configuring)
    - [.env](#env)
    - [Migrations](#migrations)
- [Usage](#usage)
  - [Starting](#starting)
  - [Routes](#routes)
    <br/>

## Technologies

![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![Express](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![Prisma](https://img.shields.io/badge/Prisma-3982CE?style=for-the-badge&logo=Prisma&logoColor=white)

## **Requirements:**

- [NodeJS](https://nodejs.org/en) v.18.x.x or higher

## **Installing:**

Yarn:

```bash
$ yarn
```

NPM:

```bash
$ npm i
```

### **Configuring**

Since the application uses two different databases, you'll need to create a [PostgreSQL](https://www.postgresql.org/) database and a [MongoDB](https://www.mongodb.com/) database as well.

#### **.env**

Rename the `.env.example` file to `.env` and update the variables with your settings.

| key               | description                              | default                                                        |
| ----------------- | ---------------------------------------- | -------------------------------------------------------------- |
| PORT              | Port number where the app will run.      | `3000`                                                         |
| DATABASE_URL      | Your PostgreSQL database connection URL. | `postgresql://<username>:<password>@localhost:5432/<database>` |
| LOGS_DATABASE_URL | Your MongoDB database connection URL.    | `mongodb://localhost:27017/<database>`                         |

#### **Migrations**

Run the following script to create database migrations:

```bash
$ npm run prisma:migrate
```

### **Starting**

```bash
$ npm start
```

> Project will start at http://localhost:3000/

### **Routes**

| route                         | HTTP method | params  |                    description                    |
| :---------------------------- | :---------: | :-----: | :-----------------------------------------------: |
| `/documentation`              |     GET     |    -    |              Swagger documentation.               |
| `/scrapings`                  |    POST     |    -    |             Performs a new scraping.              |
| `/scrapings/{date}`           |     GET     | `:date` |    Searches scraping by date, e.g. YYYY-MM-DD     |
| `/categories`                 |     GET     |    -    |               List all categories.                |
| `/categories/{slug}/products` |     GET     | `:slug` | A list of all products related a single category. |

<br/>

[⬆ Back to the top](#-meliscraper)
