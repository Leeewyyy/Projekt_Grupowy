<template>
  <div :class="['Layout', { 'gray-background': $route.name === 'user-panel' }]">
    <Header />
    <nuxt />
    <Authors />

    <client-only>
      <notifications classes="main-notification" width="370px" />
    </client-only>
  </div>
</template>

<script>
import Header from '@/components/Header';
import Authors from '@/components/Authors';

export default {
  components: {
    Header,
    Authors,
  },
};
</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  border: 0;
  outline: 0;
  box-sizing: border-box;
  font-family: inherit;
}

a {
  color: inherit;
  text-decoration: none;
}

body {
  max-width: 100%;

  font-size: 16px;
  font-family: 'Montserrat', sans-serif;

  --color-white: 255, 255, 255;
  --color-black: 0, 0, 0;

  // Layout colors:
  --color-text: 51, 0, 0;
  --color-main: 162, 190, 223;
  --color-side: 138, 169, 206;
  --color-detail-1: 106, 249, 137;
  --color-detail-2: 237, 75, 75;

  .gray-background {
    background: #fafafa !important;
  }
}

html {
  scroll-behavior: smooth;
}
</style>
