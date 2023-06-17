<template>
  <div
    class="NFZStatus"
    :class="`NFZStatus--${statusValue}`"
    v-tooltip="tooltipText"
  >
    <Icon
      class="NFZStatus_icon"
      :name="iconName"
      :size="20"
    />
    <div class="NFZStatus_label">
      NFZ
    </div>
  </div>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  components: {
    Icon,
  },

  props: {
    status: {
      type: String,
      default: '',
      validator: (value) => ['full', 'partial', 'none'].includes(value.toLowerCase()),
    },
  },

  computed: {
    statusValue() {
      return this.status?.toLowerCase();
    },

    iconName() {
      return this.icons[this.statusValue];
    },

    tooltipText() {
      return this.tooltips[this.statusValue];
    },
  },

  data() {
    return {
      tooltips: {
        full: 'Ta placówka posiada pełną obsługę finansowaną<br />przez Narodowy Fundusz Zdrowia.',
        partial: 'Ta placówka posiada częściową obsługę finansowaną<br />przez Narodowy Fundusz Zdrowia.',
        none: 'Ta placówka nie jest finansowana <br /> przez Narodowy Fundusz Zdrowia.',
      },

      icons: {
        full: 'done',
        partial: 'question_mark',
        none: 'close',
      },
    };
  },
};
</script>

<style lang="scss">
.NFZStatus {
  width: max-content;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  font-weight: 400;
  font-size: 0.75rem;

  .NFZStatus_icon {
    padding: 0.25rem;
    border-radius: 50%;
  }

  .NFZStatus_label {
    margin-top: 0.15rem;
    white-space: nowrap;
  }

  &.NFZStatus--full, &.NFZStatus--partial {
    .NFZStatus_icon {
      background: $light-blue;
    }
  }

  &.NFZStatus--none {
    .NFZStatus_icon {
      background: $red;
    }
  }
}
</style>
