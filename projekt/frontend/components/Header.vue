<template>
  <header class="Header">
    <div class="Header_container">
      <div class="container_wrapper">
        <!-- Logo -->
        <div class="Header_logo">
          <nuxtLink to="/" class="logo_link">
            <img class="logo_image" src="/images/logo.png" alt="" loading="lazy" />
            <h1 class="logo_text">LokalnyLekarz</h1>
          </nuxtLink>
        </div>
        <!-- Main navigation -->
        <Navigation class="Header_navigation" />
      </div>
      <!-- User panel link -->
      <Button
        v-if="$route.name === 'user-panel'"
        variant="light"
        class="d-flex-center less-padding"
        @click="logout()"
      >
        Wyloguj się <Icon name="logout" class="ml-1" />
      </Button>
      <div v-else-if="$route.name !== 'login'" class="Header_account">
        <MenuLink v-if="!isUserLoggedIn" href="login" class="account_button" type="button">
          Zaloguj się
        </MenuLink>
        <MenuLink class="account_link" v-else> Tu będzie nazwa użytkownika </MenuLink>
      </div>
    </div>
  </header>
</template>

<script>
import Navigation from '@/components/Navigation';
import MenuLink from '@/components/MenuLink';
import Icon from '@/components/shared/Icon';
import Button from '@/components/shared/Button';

export default {
  components: {
    Navigation,
    MenuLink,
    Icon,
    Button,
  },

  computed: {
    isUserLoggedIn() {
      // return this.$store.getters['user/isLoggedIn'];
      return false;
    },
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
.Header {
  width: 100%;
  height: 50px;
  position: absolute;
  margin: 1rem 0;
  padding: 0 1rem;
  z-index: 10;

  .Header_container {
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    .container_wrapper {
      display: flex;
      flex-direction: row;
      align-items: center;
    }

    .Header_logo {
      width: max-content;
      height: 50px;
      padding: 0 0.75rem;
      // background: rgb(var(--color-white));
      // box-shadow: 0px 2px 6px rgba(var(--color-text), 0.25);
      // border-radius: 8px;

      .logo_link {
        height: 100%;
        display: flex;
        flex-direction: row;
        align-items: center;
      }

      .logo_image {
        height: 35px;
        width: auto;
      }

      .logo_text {
        color: rgb(var(--color-text));
        font-family: 'Poppins';
        font-weight: 500;
        font-size: 1.5rem;
        text-indent: 0.5rem;
      }
    }

    .Header_navigation {
      margin-left: 1.5rem;
    }
  }

  .less-padding {
    padding: 13px !important;
  }
}
</style>
