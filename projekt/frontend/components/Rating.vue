<template>
  <div
    class="Rating"
    :class="{
      'Rating--large': large,
      'Rating--preview': preview,
    }"
  >
    <div class="Rating_container">
      <div
        v-if="preview && modelValue !== undefined"
        class="Rating_value"
        v-tooltip="showTooltip && ratingTooltip"
      >
        {{ ratingValue }}
      </div>
      <div class="Rating_stars">
        <Icon
          v-for="n in max"
          :key="`starInput_${n}`"
          class="stars_item"
          :class="{
            'item--clickable': !preview,
          }"
          :name="getIconName(n)"
          @click.native="onInput(n)"
        />
      </div>
      <div v-if="isCountVisible" class="Rating_count">
        {{ ratingCount }} opinii
      </div>
    </div>
  </div>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  components: {
    Icon,
  },

  model: {
    prop: 'modelValue',
    event: 'change',
  },

  props: {
    modelValue: {
      type: Number,
      default: null,
    },
    max: {
      type: Number,
      default: 5,
    },
    preview: {
      type: Boolean,
      default: true,
    },
    large: {
      type: Boolean,
      default: false,
    },
    showTooltip: {
      type: Boolean,
      default: true,
    },
    ratingCount: {
      type: Number,
      default: undefined,
    },
  },

  computed: {
    ratingValue() {
      return this.modelValue?.toFixed(1) || 'N/A';
    },

    ratingTooltip() {
      return this.modelValue
        ? `Ta placówka uzyskała średnią ocenę ${this.modelValue} gwiazdki.`
        : 'Ta placówka nie otrzymała jeszcze żadnej oceny.';
    },

    checkedStars() {
      return Math.max(this.modelValue, 0);
    },

    isCountVisible() {
      return this.preview && typeof this.ratingCount !== 'undefined';
    },
  },

  methods: {
    onInput(starNumber) {
      // Don't allow input if preview only
      if (this.preview) return;
      this.$emit('change', starNumber);
    },

    getIconName(starNumber) {
      return starNumber <= this.checkedStars ? 'star' : 'star_border';
    },
  },
};
</script>

<style lang="scss">
.Rating {
  user-select: none;
  font-size: 0.9rem;
  font-weight: 400;
  
  &.Rating--large {
    .Rating_stars {
      .stars_item {
        font-size: 1.3rem !important;
      }
    }
  }

  .Rating_container {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: flex-start;
  }

  .Rating_value {
    min-width: 24px;
    margin-right: 0.25rem;
    text-align: center;
    color: rgb(var(--color-text));
  }

  .Rating_stars {
    height: 16px;
    line-height: 16px;

    .stars_item {
      color: #f9c33a !important;

      &.item--clickable:hover {
        cursor: pointer;
      }
    }
  }

  .Rating_count {
    margin-left: 0.35rem;
    font-size: 0.9rem;
    color: $grey;
    white-space: nowrap;
  }
}

@media screen and (max-width: $tablet_breakpoint) {
  .Rating {
    &.Rating--preview {
      .Rating_stars {
        .stars_item {
          display: none;

          &:first-child {
            display: block;
          }
        }
      }
    }
  }
}
</style>
