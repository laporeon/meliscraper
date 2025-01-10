import 'dotenv/config';

import cors from 'cors';
import express from 'express';

import routes from './routes/index.js';
import { logger } from './utils/logger.js';

const PORT = process.env.PORT | 3000;

const corsOptions = {
  origin: 'http://localhost:${PORT}',
  methods: 'GET,HEAD,POST',
  optionsSuccessStatus: 204,
};

const app = express();

app.use(cors(corsOptions));

app.use(routes);

app.listen(PORT, () => {
  logger.info(`Server is running on http://localhost:${PORT}`);
});
