import { prisma } from '../database/prisma.js';
import { logger } from '../utils/logger.js';

import { ProductController } from './product-controller.js';

const productController = new ProductController();

export class CategoryController {
  mapCategories(categories) {
    categories.forEach(async category => {
      const { name, slug, products } = category;

      this.create({ name, slug });

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
          name: true,
          slug: true,
        },
      });

      if (categories.length < 1)
        return res.json({
          error: 'NOT_FOUND',
          message:
            'No categories available. Please, perform your first scraping using /scraper route and try this operation again.',
        });

      return res.json({ status: 'OK', data: { categories } });
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
}
