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
    <div class="display-flex align-center justify-center">
      <form @submit.prevent="handleSearchPlaces" class="search display-flex align-center justify-center">
          <span>Wyszukaj placówkę</span>
          <InputText
            name="search-input"
            v-model="searchValue"
            placeholder="Wpisz..."
            style="margin-left: 20px;"
            class="searchInput"
          />
          <Button type="submit" class="submit-search">Szukaj</Button>
      </form>
      <div
        v-if="$route.name === 'user-panel'"
        class="Header_logout-button display-flex align-center less-padding cursor-pointer"
        @click="logout()"
      >
        Wyloguj się <Icon name="logout" style="margin-left: 10px;" />
      </div>
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
              :size="35"
              color="#8AA9CE"
            />
          </template>
          <span style="margin-left: 2px;">{{ user.fullName }}</span>
        </MenuLink>
      </div>
      
    </div>
  </nav>
</template>

<script>
// eslint-disable-next-line
import { mapGetters } from 'vuex';
import Icon from '@/components/shared/Icon';
import MenuLink from '@/components/MenuLink';
import InputText from '@/components/shared/InputText';
import Button from '@/components/shared/Button';

export default {
  components: {
    Icon,
    MenuLink,
    InputText,
    Button,
  },

  computed: {
    ...mapGetters('user', {
      isLoggedIn: 'isLoggedIn',
      user: 'getUser',
    }),
  },

  data() {
    return {
      searchValue: '',
      menuItems: [
        ...(this.$route.name !== 'contact' ? [{
          name: 'Napisz do nas',
          href: '/contact',
        }] : []),
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
    handleSearchPlaces() {
      const query = { name: this.searchValue };
      this.$router.push({ path: '/places', query });
    },
  },
};
</script>

<style lang="scss">
.Navigation {
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  .search {
    margin-right: 2rem;

    .submit-search {
      padding: 8px 12px !important;
      margin-left: 1rem !important;
    }

    span {
      font-size: .8rem;
    }

    .searchInput {
      width: 200px;

      .input-outer {
        height: 30px;
        border: none;
        border-radius: 0;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0, .08);
        -moz-box-shadow: 0 1px 2px rgba(0,0,0, .08);
        -o--shadow: 0 1px 2px rgba(0,0,0, .08);
        box-shadow: 0 1px 2px rgba(0,0,0, .08);

        input {
          font-size: 13px !important;
        }

        &.focused {
          -webkit-box-shadow: 0 1px 2px rgba(0,0,0, .2);
          -moz-box-shadow: 0 1px 2px rgba(0,0,0, .2);
          -o--shadow: 0 1px 2px rgba(0,0,0, .2);
          box-shadow: 0 1px 2px rgba(0,0,0, .2);
        }
      }

      input {
        padding-top: 0;
        padding-bottom: 0; 
      }
    }
  }

  .Navigation_list {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    list-style-type: none;

    .list_item {
      min-width: 140px;
      display: flex;
      align-items: center;

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
      font-size: 1em;
    }
  }

  .flex-panel-button {

    .MenuLink_link {
      display: flex;
      align-items: center;
    }
  
    img, .material-icons {
      margin-right: 7px;
    }

    .account_avatar {
      width: 35px;
      height: 35px;
      border-radius: 50%;
    }
  }
}
</style>
