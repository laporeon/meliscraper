import { pino } from 'pino';

const transport = pino.transport({
  targets: [
    {
      target: 'pino-pretty',
      options: {
        translateTime: 'SYS:standard',
      },
    },
    {
      target: 'pino-mongodb',
      options: {
        uri: process.env.LOGS_DATABASE_URL,
        database: 'meliscraper',
        collection: 'logs',
      },
    },
  ],
});

export const logger = pino(transport);
