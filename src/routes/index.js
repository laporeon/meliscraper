import { Router } from 'express';

import { categoriesRoutes } from './categories-routes.js';
import { productsRoutes } from './products-routes.js';
import { scrapRoutes } from './scraper-routes.js';
import { swaggerRoutes } from './swagger-routes.js';

const routes = Router();

routes.use('/scrapings', scrapRoutes);
routes.use('/categories', categoriesRoutes);
routes.use('/products', productsRoutes);
routes.use('/', swaggerRoutes);

export default routes;
