module.exports = {
  root: true,
  env: {
    browser: true,
    node: true,
  },
  parserOptions: {
    parser: '@babel/eslint-parser',
    requireConfigFile: false,
  },
  extends: [
    'airbnb-base',
    'plugin:nuxt/recommended',
  ],
  plugins: [
  ],
  rules: {
    'no-return-await': 'off',
    'import/extensions': 'off',
    'no-console': 'off',
    'no-shadow': 'off',
    'no-param-reassign': 'off',
    'no-empty-pattern': 'off',
    'no-plusplus': 'off',
    'import/no-unresolved': 'off',
    'no-restricted-syntax': 'off',
    'no-trailing-spaces': 'off',
    'no-unused-vars': 'warn',
    'linebreak-style': 'off',
    'spaced-comment': 'warn',
    'max-len': 'off',
  },
};
