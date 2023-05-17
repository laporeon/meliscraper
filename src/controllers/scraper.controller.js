import { scraper } from '../functions/scraper.js';

export class ScrapController {
  async create(req, res) {
    try {
      const data = await scraper();
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
