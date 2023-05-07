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

      const mappedProducts = products.map((product, index) => {
        //TODO find a better way to deal with prices
        const value = product
          .querySelector(CONSTANTS.productMainPriceClass)
          .innerHTML.replace('R$ ', '');
        const decimal =
          product.querySelector(CONSTANTS.productDecimalPriceClass)
            ?.innerHTML || 0;

        const price = `${value}.${decimal}`;

        return {
          position: index + 1,
          name: product.querySelector(CONSTANTS.productNameClass).textContent,
          image: product
            .querySelector(CONSTANTS.productImageClass)
            .getAttribute('src'),
          price: +price,
          link: product.parentElement.getAttribute('href'),
        };
      });
      return {
        name: category.textContent,
        length: products.length,
        products: mappedProducts,
      };
    });
  }, CONSTANTS);

  console.log({ data });

  await fs.writeFile(CONSTANTS.filePath, JSON.stringify(data));

  await browser.close();
};
