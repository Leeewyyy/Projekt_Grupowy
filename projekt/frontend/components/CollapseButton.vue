<template>
  <!-- Button sam sie dostosowywuje do kontenera i umieszcza w górnym prawym rogu -->
  <button
    class="CollapseButton"
    v-tooltip="'Zwiń okno wyszukiwania'"
    @click="onClick"
  >
    <Icon
      class="CollapseButton_icon"
      name="keyboard_arrow_left"
      :size="24"
    />
    <span class="collapse-text">Zwiń</span>
  </button>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  components: {
    Icon,
  },

  methods: {
    onClick() {
      this.$emit('collapse');
    },
  },
};
</script>

<style lang="scss">
.CollapseButton {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  cursor: pointer;
  background: none;
  display: flex;
  align-items: center;
  font-weight: bold;

  .collapse-text {
    width: 0;
    opacity: 0;
    transition: 0.2s all;
  }

  &:hover {
    .collapse-text {
      width: auto;
      opacity: 1;
    }
  }
}
</style>
