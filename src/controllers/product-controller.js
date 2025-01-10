import { prisma } from '../database/prisma.js';
import { logger } from '../utils/logger.js';

export class ProductController {
  async create(category, categoryId) {
    const products = await category.products.map(product => ({
      position: product.position,
      name: product.name,
      image: product.image,
      price: product.price,
      link: product.link,
      categoryId,
    }));

    return products;
  }
}
