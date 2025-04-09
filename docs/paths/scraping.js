import { Scraping } from '../schemas/Scraping.js';

export const scrapingPaths = {
  '/scrapings': {
    post: {
      tags: ['Scraper'],
      summary: 'Performs a new scraping.',
      description:
        'Performs a new scraping, saving fetched data into database and returning the result.',
      responses: {
        201: {
          description: 'Created',
          content: {
            'application/json': {
              schema: Scraping,
            },
          },
        },
        409: {
          description: 'Conflict',
          content: {
            'application/json': {
              schema: {
                type: 'object',
                properties: {
                  message: {
                    type: 'string',
                    example:
                      'A scraping for 2025-03-08 was already found at database.',
                  },
                },
              },
            },
          },
        },
      },
    },
  },
  '/scrapings/{date}': {
    get: {
      tags: ['Scraper'],
      summary: 'Get scraping by date.',
      description:
        'Searches and returns the result of scraping for a specific date. Date format must be YYYY-MM-DD.',
      parameters: [
        {
          name: 'date',
          in: 'path',
          required: true,
          type: 'string',
        },
      ],
      responses: {
        200: {
          description: 'Success',
          content: {
            'application/json': {
              schema: {
                type: 'object',
                properties: {
                  scraping: {
                    type: 'object',
                    properties: {
                      date: {
                        type: 'string',
                        format: 'date-time',
                      },
                      products: {
                        type: 'array',
                        items: {
                          type: 'object',
                          properties: {
                            name: {
                              type: 'string',
                            },
                            link: {
                              type: 'string',
                              format: 'uri',
                            },
                            price: {
                              type: 'number',
                            },
                            Category: {
                              type: 'object',
                              properties: {
                                name: {
                                  type: 'string',
                                },
                              },
                            },
                          },
                        },
                      },
                    },
                  },
                },
              },
            },
          },
        },
        404: {
          description: 'Not Found',
          content: {
            'application/json': {
              schema: {
                type: 'object',
                properties: {
                  message: {
                    type: 'string',
                    example: 'No scraping was found for date 2025-03-09.',
                  },
                },
              },
            },
          },
        },
      },
    },
  },
};
