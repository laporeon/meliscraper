import { prisma } from '../database/prisma.js';
import { logger } from '../utils/logger.js';

export class ProductController {
  mapProducts(products, id) {
    products.forEach(product => {
      const { position, name, image, price, link } = product;

      this.create({ position, name, image, price, link, categoryId: id });
    });
  }

  async create(product) {
    const { position, name, image, price, link, categoryId } = product;

    await prisma.product.create({
      data: {
        position,
        name,
        image,
        price,
        link,
        categoryId,
      },
    });

    logger.info(`Product ${name.toUpperCase()} created!`);
  }

  async get(req, res) {
    try {
      const products = await prisma.product.findMany({
        select: {
          name: true,
          price: true,
          position: true,
          link: true,
          image: true,
        },
      });

      if (products.length < 1)
        return res.json({
          error: 'NOT_FOUND',
          message:
            'No products available. Please, perform your first scraping using /scraper route and try this operation again.',
        });

      return res.json({ status: 'OK', data: { products } });
    } catch (err) {
      return res.json({ error: err.message });
    }
  }
}
