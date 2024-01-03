import { prisma } from '../database/prisma.js';
import { logger } from '../utils/logger.js';

export class ProductController {
  async create(products, categoryId, category) {
    products.map(async product => {
      await prisma.product.create({
        data: {
          ...product,
          categoryId,
        },
      });
    });

    logger.info(
      `${products.length} products were created for category ${category}!`,
    );

    return;
  }
}
