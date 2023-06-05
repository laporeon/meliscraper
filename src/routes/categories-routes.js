import { Router } from 'express';

import { CategoryController } from '../controllers/category-controller.js';

const routes = Router();

const categoryController = new CategoryController();

routes.get('/', categoryController.get);
routes.get('/:id/products', categoryController.findProducts);

export { routes as categoriesRoutes };
