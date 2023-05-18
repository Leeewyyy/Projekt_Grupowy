<template>
  <div :class="['width-100', ]">
    <div :class="[`input-outer bg-white
      border border-rounded 
      display-flex align-center justify-center`, { 'focused': isFocused }]"
    >
      <input 
        v-model="val"
        :type="type"
        :name="name"
        :placeholder="placeholder"
        :required="required"
        class="width-100"
        @focus="isFocused = true"
        @blur="isFocused = false"
      >
      <IconToggleButton 
        v-if="icon.show"
        :icon-name="icon.name"
        :tooltip-text="icon.tooltip"
        variant="dark"
        :size="icon.size"
        @click="$emit('iconClicked')"
      />
    </div>
    <label v-if="label.length">{{ label }}</label>
  </div>
</template>

<script>
import IconToggleButton from './IconToggleButton.vue';
import Icon from '@/components/shared/Icon';

export default {
  props: {
    value: {
      type: [String, Number],
      default: '',
    },
    name: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      default: 'Wpisz...',
    },
    label: {
      type: String,
      default: '',
    },
    required: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: 'text',
      validator: (value) => ['text', 'password', 'number', 'email'].includes(value),
    },
    icon: {
      type: Object,
      default: () => ({
        show: false,
        name: '',
        tooltip: '',
        size: 27,
      }),
    },
  },
  components: { 
    IconToggleButton,
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

<style lang="scss" scoped>
@import "~/assets/scss/colors.scss";

.input-outer {
  height: 45px;
  box-sizing: border-box;
  padding-right: 10px;

  input {
    box-sizing: border-box;
    padding: 10px;
    border-radius: 10px 0 0 10px;
    height: 100%;
    font-size: 15px;
  }
}

label {
  font-size: 13px;
  margin-top: 6px;
  padding-left: 10px;
}

.focused {
  -webkit-box-shadow: 0px 0px 7px 3px $light-blue;
  -moz-box-shadow: 0px 0px 7px 3px $light-blue;
  box-shadow: 0px 0px 7px 3px $light-blue;
}
</style>
