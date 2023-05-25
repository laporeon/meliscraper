import { logger } from '../utils/logger.js';
import { scraper } from '../utils/scraper.js';

import { CategoryController } from './category-controller.js';

const categoryController = new CategoryController();

export class ScrapController {
  async create(req, res) {
    try {
      logger.info('New scraping requested.');

      const data = await scraper();

      logger.info('Scraping successfully performed.');

      categoryController.mapCategories(data.categories);

      return res.status(200).json({
        status: 'success',
        message: 'Scraping successfully performed.',
        data,
      });
    } catch (err) {
      logger.error({
        message: 'Error from scraping request',
        error: err,
      });
      return res.status(500).json({ error: err.message });
    }
  }
}
