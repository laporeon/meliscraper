import prettier from 'eslint-plugin-prettier';
import importHelpers from 'eslint-plugin-import-helpers';
import prettierConfig from 'eslint-config-prettier';
import typescriptParser from '@typescript-eslint/parser';

export default [
  {
    files: ['**/*.{ts,js,mjs}'],
    languageOptions: {
      parser: typescriptParser,
      parserOptions: {
        ecmaVersion: 'latest',
        sourceType: 'module',
        project: './tsconfig.json',
      },
    },
    plugins: {
      prettier,
      'import-helpers': importHelpers,
    },
    rules: {
      ...prettierConfig.rules,
      'prettier/prettier': 'error',
      'import-helpers/order-imports': [
        'warn',
        {
          newlinesBetween: 'always',
          groups: [
            '/^node:/',
            'module',
            '/^@/',
            ['parent', 'sibling', 'index'],
          ],
          alphabetize: {
            order: 'asc',
            ignoreCase: true,
          },
        },
      ],
      'no-console': 'off',
    },
    ignores: ['eslint.config.mjs', 'node_modules'],
  },
];
