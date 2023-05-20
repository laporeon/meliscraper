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

      await categoryController.mapCategories(data.categories);

      return res.json({
        status: 'success',
        message: 'Scraping successfully performed.',
        data,
      });
    } catch (err) {
      logger.error({
        message:
          'There was an error while trying to perform requested operation,',
        error: err,
      });
      return res.json({ error: err.message });
    }
  }
}
