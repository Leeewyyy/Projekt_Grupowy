<template>
  <div class="select-outer">
    <div class="container">
      <label>{{ description }}</label>
      <multiselect
        :id="name"
        v-model="val"
        :options="options"
        :placeholder="placeholder"
        :track-by="trackBy"
        :label="label"
        :show-labels="false"
        :tabindex="tabIndex"
        :max-height="140"
      />
    </div>
  </div>
</template>

<script>
import Multiselect from 'vue-multiselect';

export default {
  props: {
    name: {
      type: String,
      required: true,
    },
    value: {
      type: [String, Number, Object],
      default: '',
    },
    options: {
      type: Array,
      default: () => [],
    },
    description: {
      type: String,
      default: '',
    },
    placeholder: {
      type: String,
      default: 'Wybierz',
    },
    trackBy: {
      type: String,
      default: 'id',
    },
    label: {
      type: String,
      default: 'name',
    },
    tabIndex: {
      type: Number,
      default: 0,
    },
  },
  components: { Multiselect },
  computed: {
    val: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit('input', value);
      },
    },
  },
};
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>

<style lang="scss">
.multiselect__tags {
  font-size: 1em;
  border-radius: 10px;
  padding: 8px 40px 0 20px;

  @media screen and (max-width: $desktop_breakpoint) {
    font-size: 15px;
  }
}

.multiselect__content-wrapper {
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.multiselect__select:before {
  border-color: #000 transparent transparent;
}

.multiselect__single,
.multiselect__input {
  padding-left: 0;
}

.multiselect__option--highlight {
  background: #dddddd;
  color: rgb(var(--color-text));

  &.multiselect__option--selected {
    background: rgb(var(--color-detail-2));
  }
}
</style>

<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;

  label {
    font-size: 0.9em;
    color: #000;
    margin-bottom: 3px;
  }
}
</style>
