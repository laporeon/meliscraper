import { pino } from 'pino';

const transport = pino.transport({
  targets: [
    {
      target: 'pino-pretty',
      options: {
        translateTime: 'SYS:standard',
        ignore: 'pid,hostname',
      },
    },
  ],
});

export const logger = pino(transport);
