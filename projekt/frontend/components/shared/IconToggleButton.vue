<template>
  <button
    :class="[
      'icon-button cursor-pointer',
      variant,
      { active: isActive, 'with-shadow': withShadow },
    ]"
    type="button"
    @click.stop="$emit('click', !isActive)"
  >
    <img
      v-if="isCustom"
      :src="require(`@/assets/images/${iconName}.svg`)"
      :alt="customIconDescription"
    />
    <Icon v-else :name="iconName" :size="size" :color="variant === 'light' ? '#DDD' : '#000'" />
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
    isCustom: {
      type: Boolean,
      default: false,
    },
    customIconDescription: {
      type: String,
      default: 'Ikona kustomowa',
    },
    size: {
      type: Number,
      default: 35,
    },
    withShadow: {
      type: Boolean,
      default: false,
    },
    variant: {
      type: String,
      default: 'light',
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

  &.with-shadow:after {
    content: '';
    width: 70px;
    height: 40px;
    position: absolute;
    top: 12px;
    left: -15px;
    background: rgb(0, 0, 0);
    background: radial-gradient(circle, rgba(0, 0, 0, 0.1) 0%, rgba(138, 169, 206, 0) 38%);
    z-index: -1;
    transform: skew(-20deg);
  }

  .material-icons {
    transition: 0.2s all;
  }

  &.light {
    &:hover,
    &:focus {
      .material-icons,
      img {
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
      .material-icons,
      img {
        filter: opacity(0.7);
      }
    }
  }
}
</style>
