import { prisma } from '../database/prisma.js';
import { date } from '../utils/date.js';
import { logger } from '../utils/logger.js';
import { scraper } from '../utils/scraper.js';

export class ScrapController {
  async create(req, res) {
    try {
      logger.info('New scraping requested.');

      const hasDate = await prisma.scraping.findFirst({
        where: {
          date: {
            equals: new Date(date),
          },
        },
      });

      if (hasDate) {
        return res.status(409).json({
          status: 'error',
          message: `A scraping for ${date} was already found at database. To see results, please go to /scrapings/{date}.`,
        });
      }

      const scraping = await scraper();

      logger.info(
        'Scraping successfully performed and data saved into database.',
      );

      return res.status(201).json({
        status: 'created',
        message:
          'Scraping was successfully performed and current data saved into database.',
        scraping,
      });
    } catch (err) {
      logger.error({
        message: 'Error from /scraping request',
        error: err,
      });
      return res.status(500).json({ error: err.message });
    }
  }

  async getByDate(req, res) {
    try {
      const { date } = req.params;

      const result = await prisma.scraping.findMany({
        where: {
          date: {
            equals: new Date(date),
          },
        },
        include: {
          categories: {
            select: {
              name: true,
              products: {
                select: {
                  name: true,
                  link: true,
                  price: true,
                },
              },
            },
          },
        },
      });

      return res.status(200).json({
        status: 'success',
        result,
      });
    } catch (err) {
      logger.error({
        message: 'Error from /scraping/:date request',
        error: err,
        params: { date },
      });
      return res.status(500).json({ error: err.message });
    }
  }
}
