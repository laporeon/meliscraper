import { categoryPaths } from './paths/category.js';
import { scrapingPaths } from './paths/scraping.js';
import { Category } from './schemas/Category.js';
import { Products } from './schemas/Products.js';
import { Scraping } from './schemas/Scraping.js';

export const swagger = {
  openapi: '3.0.0',
  info: {
    title: 'Meliscraper API',
    version: '1.0.0',
    description:
      "A simple API that scraps data from Mercado Livre's bestsellers page!",
    license: { name: 'MIT', url: 'https://spdx.org/licenses/MIT.html' },
  },
  components: {
    schemas: {
      Category,
      Products,
      Scraping,
    },
  },
  tags: ['Scraper'],
  schemes: ['https', 'http'],
  paths: { ...scrapingPaths, ...categoryPaths },
};
