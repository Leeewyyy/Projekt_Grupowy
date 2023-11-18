<template>
  <section
    class="BoxSection"
    :class="{
      'BoxSection--no-overflow': noOverflow,
    }"
  >
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
    noOverflow: {
      type: Boolean,
      default: true,
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
  background: rgb(var(--color-white));

  &.BoxSection--no-overflow {
    overflow: hidden;
  }

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

  @media screen and (min-width: $desktop_breakpoint) {
    border-radius: 10px;
    box-shadow: 0px 2px 4px rgba(var(--color-black), 0.125);
  }
}
</style>
