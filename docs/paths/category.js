import { Category } from '../schemas/Category.js';
import { Products } from '../schemas/Products.js';

export const categoryPaths = {
  '/categories': {
    get: {
      tags: ['Category'],
      summary: 'List Categories',
      description:
        'Returns a list of all categories stored, including id, name and slug.',
      responses: {
        200: {
          description: 'Success',
          content: {
            'application/json': {
              schema: Category,
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
                  },
                },
              },
            },
          },
        },
      },
    },
  },
  '/categories/{slug}/products': {
    get: {
      tags: ['Category'],
      summary: 'List products of a specific category',
      description:
        'Returns a list of all the products related to the category. CategorySlug is required.',
      parameters: [
        {
          name: 'slug',
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
              schema: Products,
            },
          },
        },
      },
    },
  },
};
