import { prisma } from '../database/prisma.js';
import { logger } from '../utils/logger.js';
import { slugify } from '../utils/slugify.js';

export class CategoryController {
  async createOrConnectCategory(category) {
    const { id } = await prisma.category.upsert({
      where: { name: category.name },
      update: {},
      create: {
        name: category.name,
        slug: slugify(category.name),
      },
    });

    return id;
  }

  async get(req, res) {
    try {
      const categories = await prisma.category.findMany({
        select: {
          id: true,
          name: true,
          slug: true,
        },
      });

      if (categories.length < 1)
        return res.status(404).json({
          message:
            'No categories available. Please, perform your first scraping using /scrapings route and try this operation again.',
        });

      return res.status(200).json({ categories });
    } catch (error) {
      logger.error({
        url: req.originalUrl,
        method: req.method,
        error: error.message,
        stack: error.stack,
      });
      return res.json({ error: error.message });
    }
  }

  async findProducts(req, res) {
    try {
      const { slug } = req.params;

      const { name } = await prisma.category.findFirst({
        where: {
          slug,
        },
      });

      const products = await prisma.product.findMany({
        where: {
          Category: {
            name,
          },
        },
        select: {
          name: true,
          link: true,
          image: true,
        },
      });

      if (products.length < 1)
        return res.status(404).json({
          message: `No products found for category ${slug}. Please, check the slug and try again.`,
        });

      return res.json({
        data: { category: name, products },
      });
    } catch (error) {
      logger.error({
        url: req.originalUrl,
        method: req.method,
        error: error.message,
        stack: error.stack,
      });
      return res.json({ error: error.message });
    }
  }
}
