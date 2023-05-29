<template>
  <div class="single-welcome-box cursor-pointer bg-extra-light-grey
    display-flex flex-column border-rounded"
    :style="isOpen ? 'min-height: 300px' : null"
    @click="isOpen = !isOpen"
  >
    <h2>{{ header }}</h2>
    <p v-if="description && !isOpen" >{{ description }}</p>
    <slot />
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

<style scoped>
.single-welcome-box {
  box-sizing: border-box;
  padding: 1rem 1.5rem;
}

h2 {
  font-size: 1.2rem;
}

p {
  margin-top: .5rem;
  font-size: 1rem;
  padding-right: 4rem;
}
</style>
