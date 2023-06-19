<template>
  <div class="width-100">
    <div class="input-outer display-flex align-start justify-center flex-column">
      <multiselect
          :id="name"
          v-model="val"
          :options="options"
          :placeholder="placeholder"
          :track-by="trackBy"
          :label="label"
          :show-labels="false"
          :allow-empty="allowEmpty"
          :max-height="140"
          @focus="isFocused = true"
          @blur="isFocused = false"
          class="width-100"
      >
        <template #noResult>Brak wyników o podanej frazie.</template>
        <template #noOptions>Lista jest pusta.</template>
      </multiselect>
      <label v-if="selectLabel.length">{{ selectLabel }}</label>
    </div>
  </div>
</template>

<script>
import Multiselect from 'vue-multiselect';
import Icon from '@/components/shared/Icon';

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
    allowEmpty: {
      type: Boolean,
      default: true,
    },
    selectLabel: { 
      type: String,
      default: '',
    },
  },
  components: { 
    Multiselect,
    Icon,
  },
  data() {
    return { isFocused: false };
  },
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
  border: 1px solid $black;
  padding: 8px 40px 0 20px;
  height: 45px;

  @media screen and (max-width: $desktop_breakpoint) {
    font-size: 15px;
  }
}

.multiselect--active {
  .multiselect__tags {
    border-bottom: 0px;
  }
}

.multiselect__content-wrapper {
  border-left: 1px solid $black;
  border-right: 1px solid $black;
  border-bottom: 1px solid $black;
  border-top: 0px;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.multiselect--above {
  .multiselect__content-wrapper {
    border-left: 1px solid $black;
    border-right: 1px solid $black;
    border-top: 1px solid $black;
    border-bottom: 0px;
  }

  &.multiselect--active .multiselect__tags {
    border-top: 0px;
  }

  &.multiselect--active {
    border-bottom: 1px solid $black;
    border-radius: 10px;
  }
}

.multiselect__select:before {
  border-color: $black transparent transparent;
}

.multiselect__single,
.multiselect__input {
  padding-left: 0;
  margin-bottom: 0;
  margin-top: 4px;
}

.multiselect__option--highlight {
  background: $light-grey;
  color: $dark-grey;

  &.multiselect__option--selected {
    background: $light-blue-darken;
  }
}

.multiselect__option--selected {
  background: $light-blue;
}
</style>

<style lang="scss" scoped>
label {
  font-size: 13px;
  margin-top: 6px;
  padding-left: 10px;
}
</style>

</style>
