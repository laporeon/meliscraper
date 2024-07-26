import { readFileSync } from 'node:fs';

import { Router } from 'express';
import swaggerUi from 'swagger-ui-express';

const swaggerDocs = JSON.parse(readFileSync('./docs/swagger.json'));

const routes = Router();

routes.use('/documentation', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

export { routes as swaggerRoutes };
