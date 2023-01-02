<template>
  <div class="ImageSlider">
    <Icon
      class="ImageSlider_navigation navigation--prev"
      :class="{ 'navigation--disabled': !isPrev }"
      name="chevron_left"
      color="#fff" :size="64"
      @click.native="goPrev"
    />
    <img
      class="ImageSlider_image"
      :id="`sliderImage_${currentImage.id}`"
      :src="currentImage.url"
      alt=""
    />
    <Icon
      class="ImageSlider_navigation navigation--next"
      :class="{ 'navigation--disabled': !isNext }"
      name="chevron_right"
      color="#fff" :size="64"
      @click.native="goNext"
    />
  </div>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  components: {
    Icon,
  },

  props: {
    images: {
      type: Array,
      required: true,
    },
  },

  computed: {
    currentImage() {
      return this.images[this.currentImageIdx];
    },

    isPrev() {
      return this.currentImageIdx > 0;
    },

    isNext() {
      return this.currentImageIdx < (this.images.length - 1);
    },
  },

  data() {
    return {
      currentImageIdx: 0,
    };
  },

  methods: {
    goPrev() {
      this.currentImageIdx--;
    },

    goNext() {
      this.currentImageIdx++;
    },
  },
};
</script>

<style lang="scss">
.ImageSlider {
  width: 100%;
  position: relative;

  .ImageSlider_image {
    width: 100%;
    height: auto;
    min-height: 314px;
    object-fit: cover;
  }

  .ImageSlider_navigation {
    position: absolute;
    top: calc(50% - 32px);
    margin: 0 1rem;
    text-shadow: 1px 1px 10px #000;
    background: rgba(#000, 0.35);
    border-radius: 50%;
    cursor: pointer;

    &.navigation--prev {
      left: 0;
    }

    &.navigation--next {
      right: 0;
    }

    &.navigation--disabled {
      display: none;
    }
  }
}
</style>
