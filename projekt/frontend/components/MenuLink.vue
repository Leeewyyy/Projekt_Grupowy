<template>
  <div class="MenuLink">
    <component
      class="MenuLink_link"
      :is="element"
      :to="href"
    >
      <slot></slot>
    </component>
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
    width: 100%;
    height: 45px;
    display: block;
    padding: 0 1rem;

    font-family: 'Montserrat', sans-serif;
    font-weight: 400;
    font-size: 1rem;

    line-height: 45px;
    text-align: center;
    color: rgb(var(--color-text));
    background: rgb(var(--color-white));
    
    border-radius: 8px;
    box-shadow: 0px 2px 4px rgba(var(--color-black), 0.125);
  }
}
</style>
