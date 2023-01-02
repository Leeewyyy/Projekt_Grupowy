<template>
  <label class="container">
    <slot />
    <input v-model="val" :tabindex="tabIndex" type="checkbox" />
    <span class="checkmark"></span>
  </label>
</template>

<script>
export default {
  props: {
    value: {
      type: Boolean,
      default: false,
    },
    tabIndex: {
      type: Number,
      default: 0,
    },
  },
  computed: {
    val: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit('input', val);
      },
    },
  },
};
</script>

<style scoped>
.container {
  display: block;
  position: relative;
  padding-left: 30px;
  padding-top: 2px;
  margin-bottom: 12px;
  cursor: pointer;
  font-size: 0.9em;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

/* Hide the browser's default checkbox */
.container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

/* Create a custom checkbox */
.checkmark {
  position: absolute;
  top: 0;
  left: 0;
  height: 20px;
  width: 20px;
  background-color: rgba(221, 221, 221, 0.5);
}

/* On mouse-over, add a grey background color */
.container:hover input ~ .checkmark,
input:focus ~ .checkmark {
  background-color: rgba(204, 204, 204, 0.5);
  box-shadow: 1px 1px 3px rgb(183, 182, 182);
}

/* When the checkbox is checked, add a blue background */
.container input:checked ~ .checkmark {
  background-color: #333333;
}

.container input:checked:focus ~ .checkmark {
  background-color: #262626;
  box-shadow: 1px 1px 3px #333333;
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
  content: '';
  position: absolute;
  display: none;
}

/* Show the checkmark when checked */
.container input:checked ~ .checkmark:after {
  display: block;
}

/* Style the checkmark/indicator */
.container .checkmark:after {
  left: 7px;
  top: 3px;
  width: 3px;
  height: 7px;
  border: solid white;
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}
</style>
