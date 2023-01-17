<template>
  <main class="AppPage" :class="`AppPage--${name}`">
    <slot></slot>
  </main>
</template>

<script>
export default {
  props: {
    name: {
      type: String,
    },
  },
};
</script>

<style lang="scss">
  .AppPage {
    display: flex;
    flex-direction: column;
    min-height: 100vh;

    @media screen and (max-width: $desktop_breakpoint) {
      min-height: unset;
    }
  }
</style>
