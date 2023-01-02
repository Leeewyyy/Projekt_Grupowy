<template>
  <div class="PlacePhoto">
    <img
      v-if="!isPlaceholder"
      class="PlacePhoto_image"
      loading="lazy"
      :src="src"
      @error="onLoadError"
    />
    <div title="Brak obrazka" class="PlacePhoto_placeholder" v-else>
      <Icon class="placeholder_icon" name="image_not_supported" />
    </div>
  </div>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  components: {
    Icon,
  },

  props: {
    src: String,
  },

  computed: {
    isPlaceholder() {
      // No source set or loading failed
      return !this.src || this.isLoadError;
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
.PlacePhoto {
  width: 100%;
  height: 100%;

  .PlacePhoto_image {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .PlacePhoto_placeholder {
    width: 100%;
    height: 100%;

    display: flex;
    justify-content: center;
    align-items: center;
    background: #ddd;

    .placeholder_icon {
      font-size: 3rem !important;
      color: #595959 !important;
    }
  }
}
</style>
