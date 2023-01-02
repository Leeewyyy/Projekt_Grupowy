<template>
  <div class="w-100">
    <label for="input-file" :class="['d-flex-center', variant]"><slot /></label>
    <input
      id="input-file"
      name="photo"
      ref="file"
      type="file"
      accept=".png,.jpg,.jpeg"
      @input="$emit('fileUploaded', $event)"
    />
  </div>
</template>

<script>
export default {
  props: {
    variant: {
      type: String,
      default: 'dark',
    },
  },
};
</script>

<style lang="scss" scoped>
label {
  border-radius: 10px;
  padding: 7px 20px;
  font-size: 0.9em;
  cursor: pointer;
  transition: all 0.1s ease-in;
  display: block;

  &.dark {
    background-color: #333333;
    color: #ffffff;

    &:hover,
    &:focus {
      background-color: #262626;
    }
  }

  &.light {
    background-color: #dddd dd;
    color: #333333;

    &:hover,
    &:focus {
      background-color: #cdcdcd;
    }
  }
}

input {
  opacity: 0;
  position: absolute;
  z-index: -10;
}
</style>
