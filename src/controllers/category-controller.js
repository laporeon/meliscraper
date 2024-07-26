import { prisma } from '../database/prisma.js';
import { logger } from '../utils/logger.js';
import { slugify } from '../utils/slugify.js';

export class CategoryController {
  async create(name, scrapingId) {
    if (await this.isPresent(name)) {
      await prisma.category.update({
        where: { name },
        data: {
          scrapings: {
            connect: {
              id: scrapingId,
            },
          },
        },
      });

      return;
    }

    await prisma.category.create({
      data: {
        name,
        slug: slugify(name),
        scrapings: {
          connect: {
            id: scrapingId,
          },
        },
      },
    });

    logger.info(`Category ${name.toUpperCase()} created!`);

    return;
  }

  async isPresent(name) {
    const category = await prisma.category.findFirst({ where: { name } });

    if (category) {
      logger.warn(
        `Category ${name.toUpperCase()} is already registered. Skipping creation...`,
      );

      return true;
    }

    return false;
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
            'No categories available. Please, perform your first scraping using /scraper route and try this operation again.',
        });

      return res.status(200).json({ categories });
    } catch (err) {
      logger.error({
        message: 'Error from /categories request',
        error: err,
      });
      return res.json({ error: err.message });
    }
  }

  async findId(name) {
    const { id } = await prisma.category.findFirst({
      where: {
        name,
      },
    });

    return { id };
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
    } catch (err) {
      logger.error({
        message: 'Error from /categories/:id/products request',
        error: err,
        params: req.params,
      });
      return res.status(500).json({ status: 'ERROR', message: err.message });
    }
  }
}
