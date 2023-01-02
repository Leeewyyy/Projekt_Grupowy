<template>
  <button
    ref="button"
    :class="[variant, { disabled }]"
    :disabled="disabled"
    @click="$emit('click')"
  >
    <slot></slot>
  </button>
</template>

<script>
export default {
  props: {
    variant: {
      type: String,
      default: 'dark',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style lang="scss" scoped>
button {
  border-radius: 10px;
  padding: 18px 30px;
  font-size: 1em;
  cursor: pointer;
  transition: all 0.1s ease-in;
  display: block;

  &.disabled {
    pointer-events: none;
    cursor: not-allowed !important;
  }
}

.dark {
  background-color: #333333;
  color: #ffffff;

  &:hover,
  &:focus {
    background-color: #262626;
  }
}

.light {
  background-color: #ddd;
  color: #333333;

  &:hover,
  &:focus {
    background-color: #cdcdcd;
  }
}
</style>
