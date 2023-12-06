<template>
  <button
    class="Button"
    :class="{
      'Button--active': active,
      'active-dark': dark && active,
      'Button--submit': type === 'submit',
    }"
    :type="type"
    :disabled="disabled"
    v-on="$listeners"
  >
    <slot></slot>
  </button>
</template>

<script>
export default {
  props: {
    type: {
      type: String,
      default: null,
    },
    active: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    dark: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style lang="scss" scoped>
.Button {
  padding: 1rem;
  color: $dark-grey;
  background: $extra-light-grey;
  border-radius: 10px;
  cursor: pointer;

  &:hover {
    background: $light-grey;
  }

  &:disabled {
    background: $extra-light-grey;
    cursor: not-allowed;
  }

  &--active {
    background: $light-blue;

    &:hover {
      background: $light-blue-darken;
    }

    &.active-dark {
      background: $dark-grey-lighten;
      color: white !important;

      &:hover {
        background: $dark-grey-darken;
      }
    }

  }

  &--submit {
    color: $white;
    background: $dark-grey;

    &:hover {
      background: $black;
    }

    &:disabled {
      background: $dark-grey-lighten;
    }
  }
}
</style>
