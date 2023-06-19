<template>
  <div
    class="SwitchButton"
    :class="{
      [`SwitchButton--${dir}`]: dir,
      'SwitchButton--disabled': disabled,
    }"
  >
    <div class="SwitchButton_wrapper">
      <div class="SwitchButton_title" v-if="$slots.default">
        <slot></slot>
      </div>
      <input
        :id="id"
        class="SwitchButton_input"
        type="checkbox"
        @change="onInput"
        :checked="checked"
        :value="value"
        :disabled="disabled"
      />
      <label class="SwitchButton_label" :for="id"></label>
    </div>
    <div class="SwitchButton_description" v-if="description">
      {{ description }}
    </div>
  </div>
</template>
<script>
export default {
  model: {
    prop: 'modelValue',
    event: 'change',
  },

  props: {
    id: {
      type: String,
      default: '',
    },
    value: {
      type: Boolean,
      default: false,
    },
    modelValue: {
      type: Boolean,
      default: false,
    },
    description: {
      type: String,
      default: null,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    dir: {
      type: String,
      default: 'left',
    },
  },

  computed: {
    checked() {
      return this.modelValue;
    },
  },

  methods: {
    onInput(event) {
      const { checked } = event.target;
      this.$emit('change', checked);
    },
  },
};
</script>

<style lang="scss" scoped>
.SwitchButton {
  font-size: 0.8rem;

  &_wrapper {
    display: flex;
    flex-direction: row;
    align-items: center;
    width: max-content;
  }

  &_description {
    text-align: center;
  }

  &--right {
    .SwitchButton_wrapper {
      flex-direction: row-reverse;
    }
    
    .SwitchButton_title {
      margin: 0 0 0 0.5rem;
    }
  }

  &--disabled {
    cursor: not-allowed;
  }

  &_title {
    margin-right: 0.5rem;
    color: $dark-grey;
  }

  &_description {
    padding: 0.5rem 0;
    color: $dark-grey;
  }

  &_input {
    display: none;
    height: 0;
    width: 0;
  }

  &_label {
    display: block;
    position: relative;
    width: 50px;
    height: 25px;
    border-radius: 25px;
    background: $extra-light-grey;
    text-indent: -9999px;
    cursor: pointer;
  }

  &_label:after {
    content: '';
    position: absolute;
    top: 2.5px;
    left: 2px;
    width: 20px;
    height: 20px;
    border-radius: 20px;
    background: $dark-grey;
    transition: .3s;
  }

  &_input:disabled + &_label:after {
    cursor: not-allowed;
    opacity: 0.7;
  }

  &_input:checked + &_label {
    background: $light-blue;
  }

  &_input:checked + &_label:after {
    left: calc(100% - 2px);
    transform: translateX(-100%);
  }

  &:hover {
    .SwitchButton_label {
      background: $light-grey;
    }

    .SwitchButton_input:checked + .SwitchButton_label {
      background: $light-blue-darken;
    }
  }
}
</style>
