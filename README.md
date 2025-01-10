<h1 align="center"> Meliscraper

![node](https://img.shields.io/static/v1?label=node&message=20.18.0&color=2d3748&logo=node.js&style=flat-square)
![postgres](https://img.shields.io/static/v1?label=postgres&message=14.15&color=2d3748&logo=postgresql&style=flat-square)
![prisma](https://img.shields.io/static/v1?label=prisma&message=6.2.0&color=2d3748&logo=prisma&style=flat-square)
[![eslint](https://img.shields.io/badge/eslint-9.17.0-4b32c3?style=flat-square&logo=eslint)](https://eslint.org/)
[![MIT License](https://img.shields.io/badge/license-MIT-green?style=flat-square)](https://raw.githubusercontent.com/laporeon/meliscraper/main/LICENSE)

</h1>

Meliscraper is a web scraper for Mercado Libre's best sellers. It fetches and stores the first 10 best seller products of each category available at the moment of the scraping. At this time, users can only store one scraping per date.

## Table of Contents

- [Requirements](#requirements)
- [Installing](#installing)
  - [Configuring](#configuring)
    - [.env](#env)
    - [Migrations](#migrations)
- [Usage](#usage)
  - [Starting](#starting)
  - [Routes](#routes)
    <br/>

## **Requirements:**

- [NodeJS](https://nodejs.org/en) v.20.x.x or higher

If you use [NVM](https://github.com/nvm-sh/nvm), just run `nvm use` inside of the root folder.

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

For this step, you'll need to create a [PostgreSQL](https://www.postgresql.org/) database to store the data.

#### **.env**

Rename the `.env.example` file to `.env` and update the variables with your settings.

| key          | description                              | default                                                        |
| ------------ | ---------------------------------------- | -------------------------------------------------------------- |
| PORT         | Port number where the app will run.      | `3000`                                                         |
| DATABASE_URL | Your PostgreSQL database connection URL. | `postgresql://<username>:<password>@localhost:5432/<database>` |
|              |

#### **Migrations**

Run the following script to create database migrations:

```bash
$ npm run prisma:migrate
```

## **Usage**

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
