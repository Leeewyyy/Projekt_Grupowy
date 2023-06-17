<template>
  <section class="BoxSection">
    <header class="BoxSection_header">
      <slot name="header"></slot>
    </header>
    <div
      class="BoxSection_body"
      :class="{ 'body--with-back-button': showBottomButtons }"
    >
      <slot name="body"></slot>
      <BottomButtons
        v-if="showBottomButtons"
        class="body_back-button"
        :show-submit="showSubmit"
        @back="onBack"
        @submit="onSubmit"
      />
    </div>
  </section>
</template>

<script>
import BottomButtons from '@/components/BottomButtons';

export default {
  components: {
    BottomButtons,
  },

  props: {
    showBottomButtons: {
      type: Boolean,
      default: false,
    },
    showSubmit: {
      type: Boolean,
      default: false,
    },
  },

  methods: {
    onBack() {
      this.$emit('onBack');
    },

    onSubmit() {
      this.$emit('onSubmit');
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
