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
      class="Header_logout-button d-flex-center less-padding cursor-pointer"
      @click="logout()"
    >
      Wyloguj się <Icon name="logout" class="ml-1" />
    </Button>
    <div v-else-if="$route.name !== 'login'" class="Header_account">
      <MenuLink
        v-if="!isLoggedIn"
        href="login"
        class="account_button"
        type="button"
      >
        Zaloguj się
      </MenuLink>
      <MenuLink href="/user-panel" class="account_link flex-panel-button" v-else> 
        <template #before>
          <img
            v-if="user && user.imageUrl"
            class="account_avatar"
            :src="user.imageUrl"
            alt=""
          />
          <Icon
            v-else
            name="account_circle"
            :size="40"
            color="#8AA9CE"
          />
        </template>
        Panel użytkownika 
      </MenuLink>
    </div>
  </nav>
</template>

<script>
// eslint-disable-next-line
import { mapGetters } from 'vuex';
import Icon from '@/components/shared/Icon';
import MenuLink from '@/components/MenuLink';

export default {
  components: {
    Icon,
    MenuLink,
  },

  computed: {
    ...mapGetters('user', {
      isLoggedIn: 'isLoggedIn',
      user: 'getUser',
    }),
  },

  data() {
    return {
      menuItems: [
        {
          name: 'O aplikacji',
          href: '/about',
        },
        {
          name: 'Kontakt',
          href: '/contact',
        },
      ],
    };
  },

  methods: {
    logout() {
      this.$store.dispatch('user/logout').then(() => {
        if (!this.isLoggedIn) {
          this.$notify({ text: 'Wylogowano pomyślnie', type: 'success' });
          this.$router.push({ name: 'index' });
        }
      });
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

  .Header_logout-button {
    @media screen and (min-width: $desktop_breakpoint) {
       margin-right: 15px;
    }

    @media screen and (max-width: $desktop_breakpoint) {
      background: rgb(var(--color-side));
      font-size: 1em;
    }
  }

  .flex-panel-button {
    display: flex;
    align-items: center;
    justify-content: space-around;
    padding: 0 10px;

    img, .material-icons {
      margin-right: 7px;
    }

    .account_avatar {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
  }
}
</style>
