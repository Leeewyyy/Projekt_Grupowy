<template>
  <div class="width-100">
    <label for="input-file" :class="['display-flex justify-center', variant, { 'w-110px': showImages }]"><slot /></label>
    <input
      id="input-file"
      name="photo"
      ref="file"
      type="file"
      accept=".png,.jpg,.jpeg"
      :multiple="multiple"
      @input="$emit('fileUploaded', $event)"
    />

    <div v-if="showImages">
      <div
        v-for="image in images" 
        :key="image.lastModified"
        class="display-flex align-center picture-row"
      >
        <span class="material-icons image">image</span>
        <span class="text">{{ image.name }}</span>
        <div class="liner"></div>
        <button type="button" @click="$emit('delete', image)">
          <span class="material-icons">close</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    variant: {
      type: String,
      default: 'dark',
    },
    showImages: {
      type: Boolean,
      default: false,
    },
    multiple: {
      type: Boolean,
      default: false,
    },
    images: {
      type: Array,
      default: () => ([]),
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
    background-color: #dddddd;
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

.picture-row {
  gap: 10px;
  margin-top: 1em;

  .text {
    min-width: fit-content;
  }

  .liner {
    height: 1px;
    background-color: $grey;
    width: 100%;
  }

  button {
    background: none !important;
    cursor: pointer;
  }

  .material-icons.image {
    color: $grey;
  }
}

.w-110px {
  width: 110px !important;
}
</style>
