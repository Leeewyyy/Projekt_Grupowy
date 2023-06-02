<template>
  <!-- Button sam sie dostosowywuje do kontenera i umieszcza go na samym dole -->
  <div class="BottomButtons display-flex justify-center align-center">
    <button
      class="BackButton"
      type="button"
      @click="onClick"
    >
      <Icon
        class="BackButton_icon"
        name="arrow_back"
        :size="18"
      />
      Powrót
    </button>
    <Button v-if="showSubmit" @click="onSubmit" type="submit" class="submit-button">Szukaj</Button>
  </div>
</template>

<script>
import Icon from '@/components/shared/Icon';
import Button from '@/components/shared/Button'; 

export default {
  components: {
    Icon,
    Button,
  },
  props: {
    showSubmit: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    onClick() {
      this.$emit('back');
    },
    onSubmit() {
      this.$emit('submit');
    },
  },
};
</script>

<style lang="scss">
.BottomButtons {
  width: 100%;
  position: fixed;
  bottom: 0;
  left: 0;

  @media screen and (min-width: $desktop_breakpoint) {
    position: static;
    padding-top: 10px;
  }
}

.BackButton {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 0 1rem 0 0;
  font-size: 1rem;
  cursor: pointer;
  background: $white;
  z-index: 10;

  .BackButton_icon {
    margin-right: 0.5rem;
  }
}

.submit-button {
  padding: .8rem 3.5rem !important;
  font-size: 1em; 
}
</style>
