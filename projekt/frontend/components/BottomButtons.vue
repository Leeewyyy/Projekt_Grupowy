<template>
  <!-- Button sam sie dostosowywuje do kontenera i umieszcza go na samym dole -->
  <div
    class="BottomButtons display-flex justify-center align-center"
    :class="{
      'BottomButtons--with-submit': showSubmit
    }"
  >
    <button
      class="BottomButtons_button"
      type="button"
      @click="onClick"
      data-tid="main-back"
    >
      <Icon
        class="BottomButtons_icon"
        name="arrow_back"
        :size="18"
      />
      Powrót
    </button>
    <Button
      @click="onSubmit"
      v-if="showSubmit"
      type="submit"
      class="BottomButtons_submit"
      data-tid="main-submit"
    >
      Szukaj
    </Button>
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
  background: $white;
  z-index: 10;

  @media screen and (min-width: $desktop_breakpoint) {
    position: static;
    padding-top: 10px;
  }

  &.BottomButtons--with-submit {
    margin-top: 20px;
    padding-bottom: 0.5rem;
  }

  .BottomButtons_button {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    padding: 1rem;
    font-size: 1rem;
    cursor: pointer;
    background: $white;
  }

  .BottomButtons_icon {
    margin-right: 0.5rem;
  }
  
  .BottomButtons_submit {
    padding: 0.8rem 3.5rem !important;
    font-size: 1em;
  }
}
</style>
