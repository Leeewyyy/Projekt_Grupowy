<template>
  <i
    :class="['Icon', { 'material-icons': !isOutlined, 'material-icons-outlined': isOutlined }]"
    :style="`font-size: ${size}px; color: ${color}`"
  >
    {{ name }}
  </i>
</template>

<script>
export default {
  props: {
    name: {
      type: String,
      required: true,
    },
    size: {
      type: Number,
      default: 16,
    },
    color: {
      type: String,
      default: '#000',
    },
    isOutlined: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style lang="scss">
.Icon {
  display: inline-block;

  &.material-icons {
    font-size: inherit;
    font-weight: inherit;
    font-style: inherit;
    color: inherit;
  }
}
</style>
