<template>
  <section class="BoxSection">
    <header class="BoxSection_header">
      <slot name="header"></slot>
    </header>
    <div
      class="BoxSection_body"
      :class="{ 'body--with-back-button': showBackButton }"
    >
      <slot name="body"></slot>
      <BackButton
        v-if="showBackButton"
        class="body_back-button"
        @back="onBack"
      />
    </div>
  </section>
</template>

<script>
import BackButton from '@/components/BackButton';

export default {
  components: {
    BackButton,
  },

  props: {
    showBackButton: {
      type: Boolean,
      default: false,
    },
  },

  methods: {
    onBack() {
      this.$emit('onBack');
    },
  },
};
</script>

<style lang="scss">
.BoxSection {
  overflow: hidden;
  background: rgb(var(--color-white));
  border-radius: 10px;
  box-shadow: 0px 2px 4px rgba(var(--color-black), 0.125);

  .BoxSection_header {
    position: relative;
    font-family: 'Montserrat', sans-serif;
    font-weight: 600;
    font-size: 1.3rem;
    color: rgb(var(--color-text));
  }

  .BoxSection_body {
    position: relative;
    
    @media screen and (max-width: $desktop_breakpoint) {
      &.body--with-back-button {
        padding-bottom: 1.5rem;
      }
    }
  }

  @media screen and (max-width: $desktop_breakpoint) {
    border-radius: 0 0 10px 10px;
  }
}
</style>
