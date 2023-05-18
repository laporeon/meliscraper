import express from 'express';
import swaggerUi from 'swagger-ui-express';

import swaggerDocs from '../docs/swagger.json' assert { type: "json" };

import { scrapRoutes, categoriesRoutes } from './routes/index.js';

const app = express();

app.use(scrapRoutes, categoriesRoutes);

app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

app.get('/', async (req, res) => {
  res.json({
    message:
      'Hello! Welcome to Meliscraper API. To see full documentation, please go to /api-docs',
  });
});

app.listen(3333, () => {
  console.log(`Server is running on port 3333`);
});
