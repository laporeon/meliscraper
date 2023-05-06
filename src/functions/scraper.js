import fs from 'node:fs/promises';

import { CONSTANTS } from '../utils/constants.js';

import puppeteer from 'puppeteer';

export const scrap = async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();

  await page.goto(CONSTANTS.appURL, {
    waitUntil: 'networkidle2',
    timeout: 120000,
  });

  const data = await page.evaluate(CONSTANTS => {
    const categories = Array.from(
      document.querySelectorAll(CONSTANTS.categoryTitleClass),
    );

    return categories.map(category => {
      const products = Array.from(
        category.parentElement.nextElementSibling.querySelectorAll(
          CONSTANTS.productCardClass,
        ),
      );

      const mappedProducts = products.map(product => {
        return {
          name: product.querySelector(CONSTANTS.productNameClass).textContent,
          image: product
            .querySelector(CONSTANTS.productImageClass)
            .getAttribute('src'),
          price: product.querySelector(CONSTANTS.productPriceClass).textContent,
        };
      });
      return {
        name: category.textContent,
        lenght: products.length,
        products: mappedProducts,
      };
    });
  }, CONSTANTS);

  console.log({ data });

  await fs.writeFile(CONSTANTS.filePath, JSON.stringify(data));

  await browser.close();
};
