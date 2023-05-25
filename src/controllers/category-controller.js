import { prisma } from '../database/prisma.js';
import { logger } from '../utils/logger.js';

import { ProductController } from './product-controller.js';

const productController = new ProductController();

export class CategoryController {
  mapCategories(categories) {
    categories.forEach(async category => {
      const { name, slug, products } = category;

      await this.create({ name, slug });

      const { id } = await this.findCategoryId(name);

      productController.mapProducts(products, id);
    });
  }

  async create(category) {
    const { name, slug } = category;

    if (await this.isPresent(name)) return;

    await prisma.category.create({
      data: {
        name,
        slug,
      },
    });

    logger.info(`Category ${name.toUpperCase()} created!`);
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
          error: 'NOT_FOUND',
          message:
            'No categories available. Please, perform your first scraping using /scraper route and try this operation again.',
        });

      return res.status(200).json({ status: 'OK', data: { categories } });
    } catch (err) {
      return res.json({ error: err.message });
    }
  }

  async findCategoryId(name) {
    const categoryId = await prisma.category.findFirst({
      where: {
        name,
      },
    });

    return categoryId;
  }

  async findProductsById(req, res) {
    try {
      const { id } = req.params;

      const { name } = await prisma.category.findFirst({
        where: {
          id,
        },
      });

      const products = await prisma.product.findMany({
        where: {
          categoryId: id,
        },
        select: {
          name: true,
          image: true,
        },
      });

      if (products.length < 1)
        return res.status(404).json({
          status: 'NOT_FOUND',
          message: `No products found for id. Please, perform your first scraping and try again later.`,
        });

      return res.json({
        status: 'SUCCESS',
        data: { category: name, products },
      });
    } catch (err) {
      logger.error({
        message: 'Error from findProductsById request',
        error: err,
      });
      return res.status(500).json({ status: 'ERROR', message: err.message });
    }
  }
}
