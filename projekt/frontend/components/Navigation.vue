<template>
  <nav class="Navigation">
    <ul class="Navigation_list">
      <li class="list_item" v-for="(item, idx) in menuItems" :key="idx">
        <MenuLink
          class="item_link"
          :href="item.href"
        >
          {{ item.name }}
        </MenuLink>
      </li>
    </ul>
    <!-- User panel link -->
    <Button
      v-if="$route.name === 'user-panel'"
      variant="light"
      class="Header_logout-button d-flex-center less-padding"
      @click="logout()"
    >
      Wyloguj się <Icon name="logout" class="ml-1" />
    </Button>
    <div v-else-if="$route.name !== 'login'" class="Header_account">
      <MenuLink
        v-if="!isUserLoggedIn"
        href="login"
        class="account_button"
        type="button"
      >
        Zaloguj się
      </MenuLink>
      <MenuLink class="account_link" v-else> Tu będzie nazwa użytkownika </MenuLink>
    </div>
  </nav>
</template>

<script>
import Icon from '@/components/shared/Icon';
import MenuLink from '@/components/MenuLink';

export default {
  components: {
    Icon,
    MenuLink,
  },

  computed: {
    isUserLoggedIn() {
      return false;
    },
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

  methods: {
    logout() {
      // this.$store.dispatch('user/logout');
      this.$notify({ text: 'Wylogowano pomyślnie', type: 'success' });
      this.$router.push({ name: 'index' });
    },
  },
};
</script>

<style lang="scss">
.Navigation {
  display: flex;
  flex-direction: row;
  justify-content: space-between;

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
