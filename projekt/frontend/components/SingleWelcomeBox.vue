<template>
  <div class="single-welcome-box cursor-pointer bg-extra-light-grey
    display-flex flex-column border-rounded"
    :class="{ 'open': isOpen }"
    @click="isOpen = !isOpen"
  >
    <h2>{{ header }}</h2>
    <p v-if="description && !isOpen" >{{ description }}</p>
    <slot />
    <div v-if="isOpen" class="label-span">Naciśnij ponownie, aby zamknąć</div>
  </div>
</template>

<script>
export default {
  props: {
    header: {
      type: String,
      required: true,
    },
    description: {
      type: String,
      default: '',
    },
    value: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    isOpen: {
      get() { return this.value; },
      set(isOpen) { this.$emit('input', { header: this.header, isOpen }); },
    },
  },
};
</script>

<style lang="scss" scoped>
.single-welcome-box {
  box-sizing: border-box;
  padding: 1rem 1.5rem;
  position: relative;

  &.open {
    min-height: 300px;
    padding-bottom: 40px;
  }
}

h2 {
  font-size: 1.2rem;
}

p {
  margin-top: .5rem;
  font-size: 1rem;
  padding-right: 2rem;
}

.label-span {
  position: absolute;
  bottom: 10px;
  left: 50%;
  transform: translateX(-50%);
  width: 100%;
  text-align: center;
  font-size: 14px;
}
</style>
