<template>
  <button
    :class="[
      'icon-button cursor-pointer',
      variant,
      { active: isActive },
    ]"
    type="button"
    @click.stop="$emit('click', !isActive)"
  >
    <Icon 
      v-tooltip="tooltipText" 
      :name="iconName"
      :size="size"
      :color="variant === 'light' ? '#DDD' : '#000'"
    />
  </button>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  props: {
    isActive: {
      type: Boolean,
      default: false,
    },
    iconName: {
      type: String,
      required: true,
    },
    size: {
      type: Number,
      default: 35,
    },
    variant: {
      type: String,
      default: 'light',
    },
    tooltipText: {
      type: String,
      default: '',
    },
  },
  components: {
    Icon,
  },
};
</script>

<style lang="scss" scoped>
button {
  background: transparent;
  z-index: 1;
  position: relative;
  display: flex;
  align-items: center;

  .material-icons {
    transition: 0.2s all;
  }

  &.light {
    &:hover,
    &:focus {
      .material-icons {
        filter: invert(1) opacity(0.5);
      }
    }

    &.active * {
      filter: invert(1) !important;

      &:focus {
        outline: 2px solid #333333 !important;
      }
    }
  }

  &.dark {
    &:hover,
    &:focus {
      .material-icons {
        filter: opacity(0.7);
      }
    }
  }
}
</style>
