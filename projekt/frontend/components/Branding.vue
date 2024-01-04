<template>
  <div :class="['Branding', { 'left': alignLeft }]">
    <div class="Branding_logo">
      <img
        v-if="withImage" 
        class="logo_image"
        src="/images/logo.png"
        alt=""
        loading="lazy"
      />
      <h1 class="logo_text">
        <span v-html="customText || 'LokalnyLekarz'"></span>
      </h1>
    </div>
    <div v-if="description" class="Branding_description">
      {{ description }}
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
    description: {
      type: String,
      default: null,
    },
    withImage: {
      type: Boolean,
      default: true,
    },
    alignLeft: {
      type: Boolean,
      default: false,
    },
    customText: {
      type: String,
      default: '',
    },
  },
};
</script>

<style lang="scss">
.Branding {
  width: 100%;
  max-width: 260px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  &.left {
    align-items: flex-start !important;
  }

  .Branding_logo {
    width: max-content;
    height: 50px;
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 0 0.75rem;

    .logo_image {
      height: 35px;
      width: auto;
    }

    .logo_text {
      color: $black;
      font-family: 'Poppins';
      font-weight: 500;
      font-size: 1.5rem;
      text-indent: 0.5rem;
    }
  }

  .Branding_description {
    margin-top: .7rem;
    color: $black;
    font-size: 1.4rem;
    font-weight: 400;
    text-align: center;
    padding: 0 10px;
  }
}
</style>
