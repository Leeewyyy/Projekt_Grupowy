<template>
  <!-- Button sam sie dostosowywuje do kontenera i umieszcza w górnym prawym rogu -->
  <button
    class="CollapseButton"
    v-tooltip="'Zwiń okno wyszukiwania'"
    @click="onClick"
  >
    <Icon
      class="CollapseButton_icon"
      name="expand_less"
      :size="24"
    />
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

  .CollapseButton_icon {
    
  }
}
</style>
