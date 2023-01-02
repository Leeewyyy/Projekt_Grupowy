<template>
  <div class="NFZMark" :class="{ 'NFZMark--supported': value }" v-tooltip="tooltipText">
    <Icon class="NFZMark_icon" :name="iconName" />
    NFZ
  </div>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  components: {
    Icon,
  },

  props: {
    value: {
      type: Boolean,
      default: false,
    },
  },

  computed: {
    iconName() {
      return this.value ? 'done' : 'close';
    },

    tooltipText() {
      return this.value
        ? 'Ta placówka jest finansowana<br />przez Narodowy Fundusz Zdrowia.'
        : 'Ta placówka nie jest finansowana <br /> przez Narodowy Fundusz Zdrowia.';
    },
  },
};
</script>

<style lang="scss">
.NFZMark {
  width: max-content;

  display: flex;
  flex-direction: row;
  align-items: center;
  flex-wrap: nowrap;

  padding: 0.1rem 0.6rem 0.1rem 0.3rem;

  font-weight: 400;
  font-size: 0.8rem;

  background: #ed4b4b;
  border-radius: 8px;

  .NFZMark_icon {
    margin-right: 0.25rem;
  }

  &.NFZMark--supported {
    background: #6af989;
  }
}
</style>
