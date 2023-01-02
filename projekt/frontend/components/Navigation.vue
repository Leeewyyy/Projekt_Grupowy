<template>
  <nav class="Navigation">
    <ul class="Navigation_list">
      <li class="list_item" v-for="(item, idx) in menuItems" :key="idx">
        <MenuLink class="item_link" :href="item.href">
          {{ item.name }}
        </MenuLink>
      </li>
    </ul>
  </nav>
</template>

<script>
import MenuLink from '@/components/MenuLink';

export default {
  components: {
    MenuLink,
  },

  data() {
    return {
      menuItems: [
        {
          name: 'O aplikacji',
          href: 'about',
        },
        {
          name: 'Kontakt',
          href: 'contact',
        },
      ],
    };
  },
};
</script>

<style lang="scss">
.Navigation {
  .Navigation_list {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    list-style-type: none;

    .list_item {
      min-width: 140px;
      margin-right: 1rem;

      &:last-child {
        margin-right: 0;
      }
    }
  }
}
</style>
