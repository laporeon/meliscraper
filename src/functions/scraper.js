import { CONSTANTS } from '../utils/constants.js';

import puppeteer from 'puppeteer';

export const scrap = async () => {
  const browser = await puppeteer.launch();
  const page = await browser.newPage();

  await page.goto(CONSTANTS.appURL, {
    waitUntil: 'networkidle2',
    timeout: 60000,
  });

  const categories = await page.evaluate(async CONSTANTS => {
    const categoriesList = Array.from(
      document.querySelectorAll('.dynamic__carousel'),
    );

    return categoriesList.map(category => {
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
        quantity: products.length,
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

  categories.forEach(async category => {
    const { name, slug, quantity, products } = category;

    console.log({ category });

    products.forEach(async product => {
      const { position, name, image, price, link } = product;

      console.log({ product });
    });
  });

  await browser.close();
};
