<template>
  <div class="Branding">
    <div class="Branding_logo">
      <img
        class="logo_image"
        src="/images/logo.png"
        alt=""
        loading="lazy"
      />
      <h1 class="logo_text">
        LokalnyLekarz
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
    margin-top: 1rem;
    color: $black;
    font-size: 1.5rem;
    font-weight: 400;
    text-align: center;
  }
}
</style>
