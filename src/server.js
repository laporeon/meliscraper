import { readFileSync } from 'node:fs';

import 'dotenv/config';
import express from 'express';
import swaggerUi from 'swagger-ui-express';

import {
  scrapRoutes,
  categoriesRoutes,
  productsRoutes,
} from './routes/index.js';
import { logger } from './utils/logger.js';

const app = express();

const PORT = process.env.PORT | 3000;

const swaggerDocs = JSON.parse(readFileSync('./docs/swagger.json'));

app.use('/scrapings', scrapRoutes);
app.use('/categories', categoriesRoutes);
app.use('/products', productsRoutes);

app.use('/documentation', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

app.get('/', async (req, res) => {
  res.json({
    message:
      'Hello! Welcome to Meliscraper API. To see full documentation, please go to /documentation route.',
  });
});

app.listen(PORT, () => {
  logger.info(`Server is running on http://localhost:${PORT}`);
});
