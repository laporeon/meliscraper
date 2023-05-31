import { prisma } from '../database/prisma.js';
import { logger } from '../utils/logger.js';
import { scraper } from '../utils/scraper.js';

export class ScrapController {
  async create(req, res) {
    try {
      logger.info('New scraping requested.');

      const result = await scraper();

      logger.info(
        'Scraping successfully performed and data saved into database.',
      );

      return res.status(200).json({
        status: 'success',
        message:
          'Scraping was successfully performed and current data saved into database.',
        result,
      });
    } catch (err) {
      logger.error({
        message: 'Error from [[ /scraping ]] request',
        error: err,
      });
      return res.status(500).json({ error: err.message });
    }
  }

  async getByDate(req, res) {
    try {
      const { date } = req.params;

      const result = await prisma.category.findMany({
        where: {
          scrapings: {
            has: new Date(date),
          },
        },
        select: {
          name: true,
          slug: true,
          products: {
            select: {
              name: true,
              link: true,
            },
          },
        },
      });

      return res.status(200).json({
        status: 'success',
        data: {
          date,
          categories: result,
        },
      });
    } catch (err) {
      logger.error({
        message: 'Error from [[ /scraping/:date ]] request',
        error: err,
      });
      return res.status(500).json({ error: err.message });
    }
  }
}
