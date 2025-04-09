export const Category = {
  type: 'object',
  properties: {
    categories: {
      type: 'array',
      items: {
        type: 'object',
        properties: {
          id: {
            type: 'string',
            example: 'b006880f-a4e4-4f13-af26-035a83cf0774',
          },
          name: {
            type: 'string',
            example: 'Informática',
          },
          slug: {
            type: 'string',
            example: 'informatica',
          },
        },
      },
    },
  },
};
