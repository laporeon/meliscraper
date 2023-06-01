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

  async get(req, res) {
    try {
      const products = await prisma.product.findMany({
        select: {
          name: true,
          price: true,
          link: true,
        },
      });

      if (products.length < 1)
        return res.json({
          error: 'NOT_FOUND',
          message:
            'No products available. Please, perform your first scraping using /scraper route and try this operation again.',
        });

      return res.status(200).json({ status: 'OK', data: { products } });
    } catch (err) {
      return res.status(500).json({ error: err.message });
    }
  }
}
