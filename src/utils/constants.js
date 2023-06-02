import { date } from './date.js';

export const CONSTANTS = {
  appURL: 'https://www.mercadolivre.com.br/mais-vendidos',
  filePath: `./src/data/${date}.json`,
  categoriesSectionClass: '.dynamic__carousel',
  categoryTitleClass: '.dynamic__carousel-title',
  productCardClass: '.dynamic-carousel__item-container',
  productPositionClass:
    '.dynamic-carousel__pill-container--text.dynamic-carousel__pill-container--text-best-seller',
  productNameClass: '.dynamic-carousel__title',
  productImageClass: '.dynamic-carousel__img',
  productMainPriceClass: '.dynamic-carousel__price > span',
  productDecimalPriceClass: '.dynamic-carousel__price > sup',
  productLinkClass: '.dynamic-carousel__item-container > a',
};
