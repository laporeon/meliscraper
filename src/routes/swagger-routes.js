import { Router } from 'express';
import swaggerUi from 'swagger-ui-express';

import { swagger } from '../../docs/swagger.js';

const routes = Router();

const options = {
  swaggerOptions: {
    defaultModelsExpandDepth: -1,
    defaultModelExpandDepth: -1,
  },
};

routes.use(
  '/documentation',
  swaggerUi.serve,
  swaggerUi.setup(swagger, options)
);

export { routes as swaggerRoutes };
