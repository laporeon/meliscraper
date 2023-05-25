import { Router } from 'express';

import { ProductController } from '../controllers/product-controller.js';

const routes = Router();

const productController = new ProductController();

routes.get('/', productController.get);

export { routes as productsRoutes };
