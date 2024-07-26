import { prisma } from '../database/prisma.js';
import { currentDate } from '../utils/date.js';
import { logger } from '../utils/logger.js';
import { scraper } from '../utils/scraper.js';

export class ScrapController {
  async create(req, res) {
    try {
      logger.info('New scraping requested.');

      const hasDate = await prisma.scraping.findFirst({
        where: {
          date: {
            equals: new Date(currentDate),
          },
        },
      });

      if (hasDate) {
        logger.warn(
          `Scraping was not performed because a scraping for ${currentDate} was already found at database.`,
        );
        return res.status(409).json({
          message: `A scraping for ${currentDate} was already found at database. To see results, please go to /scrapings/{date}.`,
        });
      }

      const scraping = await scraper();

      logger.info(
        'Scraping successfully performed and data saved into database.',
      );

      return res.status(201).json({
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

      const result = await prisma.scraping.findFirst({
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

      if (!result)
        return res.status(404).json({
          message: `No scraping was found for date ${date}.`,
        });

      return res.status(200).json({
        result,
      });
    } catch (err) {
      logger.error({
        message: 'Error from /scraping/:date request',
        error: err,
        params: { date: req.params },
      });
      return res.status(500).json({ error: err.message });
    }
  }
}
