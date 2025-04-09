export const Scraping = {
  type: 'object',
  properties: {
    message: {
      type: 'string',
      example:
        'Scraping was successfully performed and current data saved into the database.',
    },
    scraping: {
      type: 'object',
      properties: {
        id: {
          type: 'string',
          format: 'uuid',
          example: 'e534ff33-6bb3-4ae6-897a-858a5e7598ba',
        },
        date: {
          type: 'string',
          format: 'date-time',
          example: '2025-04-09T00:00:00.000Z',
        },
        createdAt: {
          type: 'string',
          format: 'date-time',
          example: '2025-04-09T18:49:29.339Z',
        },
        products: {
          type: 'array',
          items: {
            type: 'object',
            properties: {
              id: {
                type: 'string',
                example: '5f2716b1-35bf-4965-b8c0-8122b115c838',
              },
              position: {
                type: 'integer',
                example: '1',
              },
              name: {
                type: 'string',
                example: 'Liquidificador L-99-fb Turbo Power 2,2 L',
              },
              image: {
                type: 'string',
                format: 'uri',
                example:
                  'https://http2.mlstatic.com/D_Q_NP_649042-MLU78850761472_092024-P.webp',
              },
              price: {
                type: 'number',
                example: 7990,
              },
              link: {
                type: 'string',
                format: 'uri',
                example: 'https://www.mercadolivre.com.br/liquidificador',
              },
              categoryId: {
                type: 'string',
                example: '128614e7-5570-467e-b3ea-78a5f36c92c2',
              },
              scrapingId: {
                type: 'string',
                example: 'e534ff33-6bb3-4ae6-897a-858a5e7598ba',
              },
              createdAt: {
                type: 'string',
                format: 'date-time',
                example: '2025-04-09T18:49:29.339Z',
              },
              updatedAt: {
                type: 'string',
                format: 'date-time',
                example: '2025-04-09T18:49:29.339Z',
              },
            },
          },
        },
      },
    },
  },
};
