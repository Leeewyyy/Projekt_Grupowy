<template>
  <nav
    class="Navigation"
    :class="{
      'Navigation--compact': menuItems.length < 2,
    }"
  >
    <ul class="Navigation_list">
      <li class="list_item" v-for="(item, idx) in menuItems" :key="idx">
        <MenuLink
          class="item_link"
          :href="item.href"
          @closeNavigation="handleCloseNavigation(item)"
        >
          {{ item.name }}
        </MenuLink>
      </li>
    </ul>
    <!-- User panel link -->
    <div class="display-flex align-center justify-center end-column">
    <Branding id="menu-branding" />
      <form @submit.prevent="handleSearchPlaces" class="search display-flex align-center justify-center">
          <span>Wyszukaj placówkę</span>
          <InputText
            name="search-input"
            v-model="searchValue"
            placeholder="Wpisz..."
            class="searchInput"
          />
          <Button type="submit" class="submit-search">Szukaj</Button>
      </form>
      <div
        v-if="$route.name === 'user-panel'"
        class="Header_account display-flex align-center less-padding cursor-pointer"
        @click="logout()"
      >
        <div class="account_button">
          <div>
            Wyloguj się <Icon name="logout" style="margin-left: 10px;" class="mobile-hidden" />
          </div>
        </div>
      </div>
      <div v-else-if="$route.name !== 'login'" class="Header_account">
        <MenuLink
          v-if="!isLoggedIn"
          href="/login"
          class="account_button"
          type="button"
          data-tid="header-login-button"
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
import Branding from './Branding.vue';

export default {
  components: {
    Icon,
    MenuLink,
    InputText,
    Button,
    Branding,
  },
  computed: {
    ...mapGetters('user', {
      isLoggedIn: 'isLoggedIn',
      user: 'getUser',
    }),

    isMapOpened() {
      return this.$store.getters['map/isMapOpened'];
    },

    isMapMobile() {
      return this.$store.getters['map/isMapMobile'];
    },

    menuItems() {
      const { name } = this.$route;
      const items = [];

      if (name !== 'index') {
        items.push({
          name: 'Strona główna',
          href: '/',
        });
      }
      
      if (name !== 'contact') {
        items.push({
          name: 'Napisz do nas',
          href: '/contact',
        });
      }
      
      if (['index', 'places', 'place-id'].includes(name) && this.isMapMobile) {
        items.push({
          name: this.isMapOpened ? 'Zamknij mapę' : 'Otwórz mapę',
          href: this.$route.fullPath,
          key: 'map',
        });
      }
      
      return items;
    },
  },
  
  data() {
    return {
      searchValue: '',
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
      // Skip if empty search
      if (!this.searchValue) return;
      
      this.$router.push({
        path: '/places',
        query: {
          search: this.searchValue,
        },
      });

      // Reset input text
      this.searchValue = '';
      this.$emit('toggleNavigation');
    },

    handleCloseNavigation({ key }) {
      if (key === 'map') {
        this.$nuxt.$emit('map:toggleMap');
      }

      this.$emit('toggleNavigation');
    },
  },
};
</script>

<style lang="scss">
.Navigation {
  display: flex;
  flex-direction: row;
  justify-content: space-between;

  #menu-branding {
    display: none;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    top: 27%;
    width: auto;
    margin-left: 0 !important;
  }

  @media screen and (max-width: $desktop_breakpoint) {
    .Navigation_list {
      padding-bottom: 0 !important;

      .list_item {
        min-width: unset !important;
      }
    }

    #menu-branding {
      display: block;
    }

    &.Navigation--compact {
      .end-column .search {
        margin-top: 10rem;
      }
    }

    .end-column {
      margin-top: .5rem;
      flex-direction: column;
      align-items: flex-end;
      padding: 0 2rem 2rem 2rem;

      .search {
        order: 2;
        margin-top: 3rem;
        margin-right: 0;
        align-self: center;
        flex-direction: column;
        align-items: center;
        gap: 20px;
        width: 100%;
        margin-top: 8rem;
        padding: 0;

        .searchInput {
          margin-left: 0 !important;
          width: 80%;

          input {
            font-size: 1em;
          }
        }

        .Button--submit {
          width: 50%;
          margin-left: 0 !important;
          font-size: 1em;
          padding: 10px 0;
        }
      }

      .Header_account {
        order: 1;
        padding: 0 !important;
      }
    }
  }
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
      margin-left: 20px;

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

  .Navigation_content {
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  @media screen and (max-width: $desktop_breakpoint) {
    .Navigation_list {
      margin-bottom: 0 !important;
      .list_item {
        .item_link {
          width: 100%;
          border-radius: 4px;

          a {
            display: block;
            width: 100%;
            padding: 0;
          }
        }
      }
    }

    .Navigation_content {
      flex-direction: column;
    }

    .search {
      width: 100%;
      margin: 0;
      padding: 0 2rem;

      .searchInput {
        width: 100%;
        margin-left: 0;
      }
    }

    .Header_account {
      .account_avatar {
        width: 50px !important;
      }

      .account_link  {
        width: 100%;
        background: $white;
        border-radius: 4px;
        box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
        padding: 5px 10px;

        a {
          padding: 0;
          width: 100%;
        }
      }
    }

    .Header_logout-button {
      margin-top: 1rem !important;
      background: $white;
      border-radius: 4px;
      box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
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
