/*eslint no-undef: "off"*/

import puppeteer from 'puppeteer';

import { CategoryController } from '../controllers/category-controller.js';
import { ProductController } from '../controllers/product-controller.js';
import { CONSTANTS } from './constants.js';

export const scraper = async () => {
  const browser = await puppeteer.launch({ headless: true });
  const page = await browser.newPage();

  await page.goto(CONSTANTS.appURL, {
    waitUntil: 'networkidle2',
    timeout: 60000,
  });

  const categories = await page.evaluate(async CONSTANTS => {
    return Array.from(
      document.querySelectorAll(CONSTANTS.categoriesSectionClass),
    ).map(category => {
      const categoryName = category.querySelector(
        CONSTANTS.categoryTitleClass,
      ).textContent;

      const products = Array.from(
        category.querySelectorAll(CONSTANTS.productCardClass),
      ).slice(0, 10);

      return {
        name: categoryName,
        products: products.map((product, index) => {
          return {
            position: index + 1,
            name: product.querySelector(CONSTANTS.productNameClass).textContent,
            image:
              product
                .querySelector(CONSTANTS.productImageClass)
                .getAttribute('data-src') ||
              product
                .querySelector(CONSTANTS.productImageClass)
                .getAttribute('src'),
            price: parseInt(
              product
                .querySelector(CONSTANTS.productMainPriceClass)
                .textContent.replace(/[^\d]/g, '') +
                (product.querySelector(CONSTANTS.productDecimalPriceClass)
                  ?.textContent || '00'),
              10,
            ),
            link: product
              .querySelector(CONSTANTS.productLinkClass)
              .getAttribute('href'),
          };
        }),
      };
    });
  }, CONSTANTS);

  await browser.close();

  return { categories };
};
