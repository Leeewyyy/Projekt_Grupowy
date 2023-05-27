<template>
  <!-- Button sam sie dostosowywuje do kontenera i umieszcza go na samym dole -->
  <button
    class="BackButton"
    @click="onClick"
  >
    <Icon
      class="BackButton_icon"
      name="arrow_back"
      :size="18"
    />
    Powrót
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
      this.$emit('back');
    },
  },
};
</script>

<style lang="scss">
.BackButton {
  width: 100%;
  position: fixed;
  bottom: 0;
  left: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 1rem;
  font-size: 1.1rem;
  cursor: pointer;
  background: $white;
  z-index: 10;

  .BackButton_icon {
    margin-right: 0.5rem;
  }

  @media screen and (min-width: $desktop_breakpoint) {
    position: static;
  }
}
</style>
