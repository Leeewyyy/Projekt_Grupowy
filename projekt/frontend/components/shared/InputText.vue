<template>
  <div class="width-100">
    <textarea 
      v-if="isTextArea"
      v-model="val"
      :name="name"
      :placeholder="placeholder"
      :required="required"
      class="width-100"
      @focus="isFocused = true"
      @blur="isFocused = false"
      :class="[`input-outer textarea bg-white width-100
        border border-rounded 
        display-flex align-center justify-center`, { 'focused': isFocused }]"
      />
    <div v-else :class="[`input-outer bg-white
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
    <slot />
    <label v-if="label.length" :class="{ 'text-red': isLabelError }">{{ label }}</label>
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
        size: 23,
      }),
    },
    isTextArea: {
      type: Boolean,
      default: false,
    },
    isLabelError: {
      type: Boolean,
      default: false,
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
.text-red {
  color: $red;
}

.input-outer {
  min-height: 45px;
  box-sizing: border-box;
  padding-right: 10px;

  &.textarea {
    padding-top: 10px;
    padding-left: 10px;
    min-height: 200px !important;
    font-size: 15px;
  }

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
