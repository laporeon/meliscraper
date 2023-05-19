import { scraper } from '../utils/scraper.js';

import { CategoryController } from './category-controller.js';

const categoryController = new CategoryController();

export class ScrapController {
  async create(req, res) {
    try {
      const data = await scraper();

      await categoryController.mapCategories(data.categories);
      return res.json({
        status: 'success',
        message: 'Scraping successfully performed.',
        data,
      });
    } catch (err) {
      return res.json({ error: err.message });
    }
  }
}
