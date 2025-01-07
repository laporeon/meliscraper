import prettier from 'eslint-plugin-prettier';
import importHelpers from 'eslint-plugin-import-helpers';
import prettierConfig from 'eslint-config-prettier';

export default [
  {
    files: ['src/**/*.js'],
    languageOptions: {
      ecmaVersion: 'latest',
      sourceType: 'module',
      globals: {
        process: 'readonly',
        console: 'readonly',
      },
    },
    plugins: {
      prettier,
      'import-helpers': importHelpers,
    },
    rules: {
      ...prettierConfig.rules,
      'prettier/prettier': 'error',
      'no-console': 'off',
      'import-helpers/order-imports': [
        'warn',
        {
          newlinesBetween: 'always',
          groups: ['/^node:/', 'module', ['parent', 'sibling', 'index']],
          alphabetize: {
            order: 'asc',
            ignoreCase: true,
          },
        },
      ],
    },
    ignores: ['**/*.config.js', 'node_modules'],
  },
];
