<h1 align="center"> Meliscraper</h1>

## Table of Contents

- [Technologies](#technologies)
- [About](#About)
- [Usage](#usage)
  - [Requirements](#requirements)
  - [Installing](#installing)
  - [Configuring](#configuring)
  - [Starting](#starting)
  - [Routes](#routes)
    <br/>

## Technologies

![NodeJS](https://img.shields.io/badge/node.js-6DA55F?style=for-the-badge&logo=node.js&logoColor=white)
![Express](https://img.shields.io/badge/express.js-%23404d59.svg?style=for-the-badge&logo=express&logoColor=%2361DAFB)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white)
![Prisma](https://img.shields.io/badge/Prisma-3982CE?style=for-the-badge&logo=Prisma&logoColor=white)


## About

Meliscraper is a web scraper for Mercado Livre best sellers. This isn't a very complex project, but it is my first most complete project. I tried to implement simple things that I never used before, such as linters and a better way to deal with logs. I also decide to use two databases: PostgreSQL for relational data and MongoDB for application logs.

## Usage

### **Requirements:**

- [NodeJS](https://nodejs.org/en) v.18.x.x or higher

### **Installing:**

Yarn:

```bash
$ yarn
```

NPM:

```bash
$ npm i
```

### **Configuring**

Rename the `.env.example` file to `.env` and update the variables with your settings.

```env
PORT=
DATABASE_URL=
LOGS_DATABASE_URL=
```

### **Starting**

```bash
$ npm start
```

> Project will start at http://localhost:3000/

### **Routes**

| route            | HTTP method |  params  |         description         |
| :--------------- | :---------: | :------: | :-------------------------: |
| `/documentation`         |     -     |    -     | Swagger documentation. |
| `/scrapings`         |     POST     |    -     | Performs a new scraping. |
| `/scrapings/{date}`         |     GET     |    `:date`    | Searches scraping by date. |
| `/categories` |     GET     | - | List all categories. |
| `/categories/{id}/products` |     GET     | `:id` | A list of all products related a category id.|
| `/products` |     GET     | - | List all products. |


<br/>

[⬆ Back to the top](#-meliscraper)
