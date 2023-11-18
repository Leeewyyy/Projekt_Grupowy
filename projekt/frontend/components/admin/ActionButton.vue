<template>
  <component
    class="ActionButton"
    :style="{
      '--background-color': backgroundColor,
      '--text-color': textColor, 
    }"
    :is="element"
    :to="to"
  >
    <slot />
    <Icon
      v-if="icon"
      class="ActionButton_icon"
      :name="icon"
      :color="textColor"
    />
  </component>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  components: {
    Icon,
  },

  props: {
    element: {
      type: String,
      default: 'button',
    },
    to: {
      type: String,
      default: null,
    },
    icon: {
      type: String,
      default: null,
    },
    backgroundColor: {
      type: String,
      default: '#c5f6f6',
    },
    textColor: {
      type: String,
      default: '#000',
    },
  },
};
</script>

<style lang="scss">
.ActionButton {
  width: max-content;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem 1rem;

  color: var(--text-color) !important;
  background: var(--background-color) !important;
  border-radius: 0.5rem;
  cursor: pointer;

  font-size: 1rem !important;
  font-weight: 400 !important;
  text-decoration: none !important;
}
</style>
