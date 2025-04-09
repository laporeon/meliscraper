export const Products = {
  type: 'object',
  properties: {
    category: {
      type: 'string',
      example: 'Eletrodomésticos',
    },
    products: {
      type: 'array',
      items: {
        type: 'object',
        properties: {
          name: {
            type: 'string',
            example: 'Liquidificador L-99-fb Turbo Power 2,2 L',
          },
          link: {
            type: 'string',
            example: 'https://www.mercadolivre.com.br/liquidificador',
          },
          image: {
            type: 'string',
            example:
              'https://http2.mlstatic.com/D_Q_NP_649042-MLU78850761472_092024-P.webp',
          },
        },
      },
    },
  },
};
