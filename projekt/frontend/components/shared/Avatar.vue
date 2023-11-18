<template>
  <div
    class="Avatar"
    :class="`Avatar--${size}`"
  >
    <img
      v-if="!isPlaceholder"
      class="Avatar_image"
      loading="lazy"
      :src="imageUrl"
      @error="onLoadError"
    />
    <div class="Avatar_icon-container" v-else>
      <Icon class="icon-container_icon" :name="placeholderIcon" />
    </div>
  </div>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  props: {
    imageUrl: {
      type: String,
      default: null,
    },
    placeholderIcon: {
      type: String,
      default: 'person',
    },
    size: {
      type: String,
      default: 'medium',
    },
  },

  components: {
    Icon,
  },

  computed: {
    isPlaceholder() {
      return !this.imageUrl || this.isLoadError;
    },
  },

  data() {
    return {
      isLoadError: false,
    };
  },

  methods: {
    onLoadError() {
      this.isLoadError = true;
    },
  },
};
</script>

<style lang="scss">
.Avatar {
  border-radius: 50%;
  overflow: hidden;

  &.Avatar--small {
    width: 32px;
    height: 32px;
  }

  &.Avatar--medium {
    width: 48px;
    height: 48px;
  }

  &.Avatar--large {
    width: 64px;
    height: 64px;
  }

  &.Avatar--huge {
    width: 128px;
    height: 128px;
  }

  .Avatar_image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .Avatar_icon-container {
    width: 100%;
    height: 100%;

    display: flex;
    justify-content: center;
    align-items: center;
    background: #ddd;

    .icon-container_icon {
      font-size: 2rem !important;
      color: #595959 !important;
    }
  }
}
</style>
