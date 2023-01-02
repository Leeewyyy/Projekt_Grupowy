export default {
  head: {
    titleTemplate: '%s - LokalnyLekarz.pl',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  server: {
    host: process.env.HOST || '127.0.0.1',
    port: process.env.PORT || 3000,
  },

  components: false,

  modules: ['@nuxtjs/style-resources', '@nuxtjs/axios', 'nuxt-leaflet', 'cookie-universal-nuxt'],

  buildModules: ['@nuxtjs/eslint-module', '@nuxtjs/google-fonts', '@nuxtjs/device'],

  css: ['~/assets/scss/index.scss'],

  styleResources: {
    scss: ['./assets/scss/index.scss'],
  },

  plugins: [
    { src: '@/plugins/v-tooltip.js' },
    { src: '@/plugins/vuescroll.js' },
    { src: '@/plugins/vue-notification.js', mode: 'client' },
  ],

  axios: {
    proxy: true,
  },

  proxy: {
    '/api': {
      target: process.env.AXIOS_API_URL || 'http://127.0.0.1:8080',
      pathRewrite: { '^/api': '' },
    },
  },

  build: {
    postcss: null,
    extractCSS: {
      ignoreOrder: true,
    },
    extend(config, ctx) {
      if (ctx.dev && ctx.isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/,
        });
      }
    },
  },

  telemetry: false,

  // render: {
  //   static: {
  //     cacheControl: true,
  //     lastModified: true,
  //     etag: true,
  //     maxAge: '1y',
  //   },

  //   http2: {
  //     push: true,
  //   },

  //   asyncScripts: true,
  //   resourceHints: true,
  // },

  googleFonts: {
    display: 'swap',

    prefetch: true,
    preconnect: true,
    preload: true,

    families: {
      Montserrat: true,
      'Material+Icons': true,
      'Material+Icons+Outlined': true,
      Poppins: {
        // Logo only
        wght: [500],
      },
    },
  },
};
