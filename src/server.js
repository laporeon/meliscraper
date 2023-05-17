import express from 'express';

import { scrapRoutes } from './routes/index.js';

const app = express();

app.use(scrapRoutes);

app.get('/', async (req, res) => {
  res.json({
    message:
      'Hello! Welcome to Meliscraper API. To see full documentation, please go to /api-docs',
  });
});

app.listen(3333, () => {
  console.log(`Server is running on port 3333`);
});
