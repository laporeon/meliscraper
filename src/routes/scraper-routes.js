import { Router } from 'express';

import { ScrapController } from '../controllers/scraper-controller.js';

const routes = Router();

const scrapController = new ScrapController();

routes.post('/', scrapController.create);
routes.get('/:date', scrapController.getByDate);

export { routes as scrapRoutes };
