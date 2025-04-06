import { prisma } from '../database/prisma.js';
import { currentDate } from '../utils/date.js';
import { logger } from '../utils/logger.js';
import { scraper } from '../utils/scraper.js';
import { CategoryController } from './category-controller.js';
import { ProductController } from './product-controller.js';

const categoryController = new CategoryController();
const productController = new ProductController();

export class ScrapController {
  async create(req, res) {
    try {
      const hasDate = await prisma.scraping.findFirst({
        where: {
          date: {
            equals: new Date(currentDate),
          },
        },
      });

      if (hasDate) {
        return res.status(409).json({
          message: `A scraping for ${currentDate} was already found at database.`,
        });
      }

      const data = await scraper();

      const { categories } = data;

      const products = await Promise.all(
        categories.map(async category => {
          const categoryId =
            await categoryController.createOrConnectCategory(category);

          return await productController.create(category, categoryId);
        })
      );

      const scraping = await prisma.scraping.create({
        data: {
          products: {
            create: products.flat(),
          },
        },
        include: {
          products: true,
        },
      });

      return res.status(201).json({
        message:
          'Scraping was successfully performed and current data saved into the database.',
        scraping,
      });
    } catch (error) {
      logger.error({
        url: req.originalUrl,
        method: req.method,
        error: error.message,
        stack: error.stack,
      });
      return res.status(500).json({ error: error.message });
    }
  }

  async getByDate(req, res) {
    try {
      const { date } = req.params;

      const scraping = await prisma.scraping.findFirst({
        where: {
          date: {
            equals: new Date(date),
          },
        },
        select: {
          date: true,
          products: {
            select: {
              name: true,
              link: true,
              price: true,
              Category: {
                select: {
                  name: true,
                },
              },
            },
          },
        },
      });

      if (!scraping)
        return res.status(404).json({
          message: `No scraping was found for date ${date}.`,
        });

      return res.status(200).json({
        scraping,
      });
    } catch (error) {
      logger.error({
        url: req.originalUrl,
        method: req.method,
        error: error.message,
        stack: error.stack,
      });
      return res.status(500).json({ error: error.message });
    }
  }
}
