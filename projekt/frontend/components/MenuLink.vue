<template>
  <div class="MenuLink">
    <div class="MenuLink_link">
      <slot name="before" />
      <component
        :is="element"
        :to="href"
      >
        <slot></slot>
      </component>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    element: {
      type: String,
      default: 'nuxt-link',
    },
    href: {
      type: String,
      default: '#',
    },
  },
};
</script>

<style lang="scss">
.MenuLink {
  .MenuLink_link {
    font-family: 'Montserrat', sans-serif;
    font-weight: bold;
    text-align: center;
    color: $dark-grey;
  }
}
</style>
