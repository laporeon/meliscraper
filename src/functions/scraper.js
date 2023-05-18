import puppeteer from 'puppeteer';

import { CONSTANTS } from '../utils/constants.js';

export const scraper = async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();

  await page.goto(CONSTANTS.appURL, {
    waitUntil: 'networkidle2',
    timeout: 60000,
  });

  const data = await page.evaluate(async CONSTANTS => {
    const categories = Array.from(
      document.querySelectorAll(CONSTANTS.categoriesSectionClass),
    );

    return categories.map(category => {
      const element = category.querySelector(CONSTANTS.categoryTitleClass);
      const elementSibling = element.parentElement.nextElementSibling;

      const name = element.textContent;

      const slug = element.textContent
        .normalize('NFD')
        .replace(/[\u0300-\u036f]/g, '')
        .toLowerCase()
        .replace(/ /g, '-')
        .replace(/[^\w-]+/g, '');

      const products = Array.from(
        elementSibling.querySelectorAll(CONSTANTS.productCardClass),
      );

      return {
        name,
        slug,
        products: products.map((product, index) => {
          //TODO find a better way to deal with prices
          const value = product
            .querySelector(CONSTANTS.productMainPriceClass)
            .innerHTML.replace('R$ ', '')
            .replace('.', '');
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
        }),
      };
    });
  }, CONSTANTS);

  await browser.close();

  return { categories: data };
};
