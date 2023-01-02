<template>
  <div :class="['Authors', { 'more-right': $route.name === 'index' }]">
    <span class="Authors_label">
      O autorach
      <Icon
        v-tooltip="{
          classes: ['Tooltip', 'Tooltip--padding', 'Tooltip--large'],
          content: tooltipContent,
        }"
        class="label_icon cursor-pointer"
        name="info"
      />
    </span>
  </div>
</template>

<script>
import Icon from '@/components/shared/Icon';

export default {
  components: {
    Icon,
  },

  data() {
    const currentYear = new Date().getFullYear();

    return {
      tooltipContent: `
        © ${currentYear} lokalnylekarz.pl | Politechnika Gdańska<br /><br />
        Jakub Trzeciak, Adam Lewandowski,<br/>
        Alan Oleszek, Mateusz Hawryluk
      `,
    };
  },
};
</script>

<style scoped lang="scss">
.Authors {
  position: fixed;
  bottom: 30px;
  z-index: 2;
  right: 20px;

  &.more-right {
    right: 54px !important;
  }

  .Authors_label {
    display: flex;
    flex-direction: row;
    align-items: center;
    font-size: 1rem;
    color: rgb(var(--color-black));

    .label_icon {
      margin-left: 0.15rem;
    }
  }
}
</style>
