import { Router } from 'express';

import { ScrapController } from '../controllers/scraper-controller.js';

const routes = Router();

const scrapController = new ScrapController();

routes.post('/scraping', scrapController.create);

export { routes as scrapRoutes };
