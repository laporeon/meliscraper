import { Router } from 'express';

import { CategoryController } from '../controllers/category-controller.js';

const routes = Router();

const categoryController = new CategoryController();

routes.get('/categories', categoryController.get);

export { routes as categoriesRoutes };
