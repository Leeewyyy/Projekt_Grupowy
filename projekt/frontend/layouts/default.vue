<template>
  <div class="Layout">
    <Header />
    <nuxt />

    <Authors class="Layout_authors"/>

    <client-only>
      <notifications classes="main-notification" position="bottom left" width="370px" />
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
  --color-text: 51, 51, 51;
  --color-main: 162, 190, 223;
  --color-side: 138, 169, 206;
  --color-detail-1: 106, 249, 137;
  --color-detail-2: 237, 75, 75;
}

html {
  scroll-behavior: smooth;
}

@media screen and (max-width: $desktop_breakpoint) {
  .Layout_authors {
    display: none;
  }
}
</style>
