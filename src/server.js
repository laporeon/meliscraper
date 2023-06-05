import { readFileSync } from 'node:fs';

import 'dotenv/config';
import cors from 'cors';
import express from 'express';
import swaggerUi from 'swagger-ui-express';

import {
  scrapRoutes,
  categoriesRoutes,
  productsRoutes,
} from './routes/index.js';
import { logger } from './utils/logger.js';

const PORT = process.env.PORT | 3000;
const swaggerDocs = JSON.parse(readFileSync('./docs/swagger.json'));

const corsOptions = {
  origin: ['http://localhost:${PORT}', 'https://meliscraper.onrender.com/'],
  methods: 'GET,HEAD,POST',
  optionsSuccessStatus: 204,
};

const app = express();

app.use(cors(corsOptions));

app.use('/documentation', swaggerUi.serve, swaggerUi.setup(swaggerDocs));

app.use('/scrapings', scrapRoutes);
app.use('/categories', categoriesRoutes);
app.use('/products', productsRoutes);

app.get('/', async (req, res) => {
  res.json({
    message:
      'Hello! Welcome to Meliscraper API. To see full documentation, please go to /documentation route.',
  });
});

app.listen(PORT, () => {
  logger.info(`Server is running on http://localhost:${PORT}`);
});
