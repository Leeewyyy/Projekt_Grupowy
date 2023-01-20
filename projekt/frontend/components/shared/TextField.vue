<template>
  <div class="text-field-outer">
    <div class="container">
      <label>{{ label }}</label>
      <textarea
        v-if="isTextarea"
        v-model="val"
        :id="name"
        :placeholder="placeholder"
        :tabindex="tabIndex"
        :required="required"
        :class="['text-field', { 'box-shadow': withShadow, flat: isFlat }]"
      />
      <div v-else>
        <input
          v-model="val"
          :type="isPasswordActive ? 'text' : type"
          :id="name"
          :placeholder="placeholder"
          :tabindex="tabIndex"
          :required="required"
          :class="['text-field', { 'box-shadow': withShadow, flat: isFlat }]"
        />
        <IconToggleButton
          v-if="type === 'password' && showPasswordToggleIcon"
          :is-active="isPasswordActive"
          class="toggle-password-icon"
          icon-name="visibility"
          :size="25"
          :tabindex="tabIndex + 0.5"
          @click="isPasswordActive = $event"
        />
      </div>
    </div>
  </div>
</template>

<script>
import IconToggleButton from './IconToggleButton.vue';

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
    isTextarea: {
      type: Boolean,
      default: false,
    },
    withShadow: {
      type: Boolean,
      default: false,
    },
    isFlat: {
      type: Boolean,
      default: false,
    },
    required: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: 'text',
    },
    tabIndex: {
      type: Number,
      default: 0,
    },
    showPasswordToggleIcon: {
      type: Boolean,
      default: true,
    },
  },
  components: { IconToggleButton },
  data() {
    return {
      isPasswordActive: false,
      delayedValue: '',
    };
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
.container {
  display: flex;
  flex-direction: column;
  position: relative;
  width: 100%;

  label {
    font-size: 0.9em;
    color: #000;
    margin-bottom: 3px;
  }
}

.text-field {
  box-sizing: border-box;
  padding: 10px 20px;
  background-color: #fff;
  border-radius: 10px;
  width: 100%;

  &::placeholder {
    color: #a6a6a6;
  }

  &.box-shadow {
    box-shadow: 0 1px 0 1px #00000025;
  }

  &.flat {
    border-radius: unset;
    border-bottom: 1px solid #dddddd;
  }

  @media screen and (max-width: $desktop_breakpoint) {
    padding: 10px 25px;

    &.flat {
      background: transparent;
    }
  }
}

textarea {
  min-height: 122px;
  width: 100%;
  resize: none;

  &.flat {
    border-radius: unset;
    border-bottom: 1px solid #dddddd;
  }
}

input,
textarea {
  font-size: 1em;

  @media screen and (max-width: $desktop_breakpoint) {
    font-size: 15px;
  }
}

.toggle-password-icon {
  position: absolute;
  top: 29px;
  right: 10px;
}
</style>
