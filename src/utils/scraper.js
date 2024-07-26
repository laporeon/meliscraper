/*eslint no-undef: "off"*/

import puppeteer from 'puppeteer';

import { CategoryController } from '../controllers/category-controller.js';
import { ProductController } from '../controllers/product-controller.js';
import { prisma } from '../database/prisma.js';

import { CONSTANTS } from './constants.js';

const categoryController = new CategoryController();
const productController = new ProductController();

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
      ).slice(0, 5);

      return {
        name: categoryName,
        products: products.map((product, index) => {
          //TODO find a better way to deal with prices
          const value = product
            .querySelector(CONSTANTS.productMainPriceClass)
            .innerText.replace('R$ ', '')
            .replace('.', '');
          const decimal =
            product.querySelector(CONSTANTS.productDecimalPriceClass)
              ?.innerText || 0;

          return {
            position: index + 1,
            name: product.querySelector(CONSTANTS.productNameClass).textContent,
            image: product
              .querySelector(CONSTANTS.productImageClass)
              .getAttribute('src'),
            price: Number(`${value}.${decimal}`),
            link: product
              .querySelector(CONSTANTS.productLinkClass)
              .getAttribute('href'),
          };
        }),
      };
    });
  }, CONSTANTS);

  await browser.close();

  const { id: scrapingId } = await prisma.scraping.create({ data: {} });

  categories.map(async category => {
    const { name, products } = category;
    await categoryController.create(name, scrapingId);
    const { id: categoryId } = await categoryController.findId(name);
    await productController.create(products, categoryId, name);
  });

  return { id: scrapingId, categories };
};
