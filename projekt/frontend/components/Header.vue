<template>
  <header
    class="Header"
    :class="{
      'Header--dark': isDark,
      'Header--menu-visible': isNavigationVisible,
    }">
    <div class="Header_container">
      <div class="container_wrapper">
        <!-- Logo -->
        <div class="Header_logo">
          <nuxtLink to="/" class="logo_link" @click.native="closeNavigation">
            <img
              class="logo_image"
              src="/images/logo.png"
              alt=""
              loading="lazy"
            />
            <h1 class="logo_text">LokalnyLekarz</h1>
          </nuxtLink>
        </div>
        <!-- Main navigation -->
        <Navigation
          class="Header_navigation"
          @onItemClick="closeNavigation"
        />
        <button
          id="navigationToggle"
          class="Header_navigation-toggle"
          @click="toggleNavigation"
        >
          <Icon
            :name="menuIcon"
            :size="36"
            :color="isDark ? '#fff' : '#000'"
          />
        </button>
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
    menuIcon() {
      return this.isNavigationVisible
        ? 'close'
        : 'menu';
    },

    isDark() {
      return true;
    },
  },

  data() {
    return {
      isNavigationVisible: false,
    };
  },

  methods: {
    toggleNavigation() {
      this.isNavigationVisible = !this.isNavigationVisible;
    },

    closeNavigation() {
      this.isNavigationVisible = false;
    },
  },

  watch: {
    '$route.path': {
      // eslint-disable-next-line
      handler: function () {
        this.closeNavigation();
      },
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

  .Header_navigation-toggle {
    display: none;
  }

  .Header_container {
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    .container_wrapper {
      width: 100%;
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
      width: 100%;
      margin-left: 1.5rem;
    }
  }

  .less-padding {
    padding: 13px !important;
  }
}

@media screen and (max-width: $desktop_breakpoint) {
  .Header {
    margin: 0;
    height: 60px;
    background: #fff;
    position: relative;

    &.Header--dark {
      background: rgb(var(--color-main));

      .Header_navigation-toggle {
        color: #fff;
      }

      .Header_container {
        .Header_logo {
          .logo_image {
            filter: invert(100%);
          }

          .logo_text {
            color: #fff;
          }
        }
      }
    }

    .Header_navigation-toggle {
      display: block;
      background: none;
    }

    .Header_navigation {
      display: none;
      width: 100%;
      height: 100vh;
      background: rgb(var(--color-main));
      overflow: hidden;
      z-index: 10;
    }

    .Header_logout-button {
      margin: 0 2rem;
    }

    &.Header--menu-visible {
      .Header_navigation {
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        position: absolute;
        top: 60px;
        left: 0;
        margin-left: 0;

        .Navigation_list {
          width: 100%;
          display: flex;
          flex-direction: column;
          align-items: center;
          padding: 2rem 2rem 1rem 2rem;
          
          .list_item {
            width: 100%;
            margin: 0 0 1rem 0;
          }
        }
      }

      .Header_account {
        padding: 0 2rem;

        .account_button {
          .MenuLink_link {
            color: #fff;
            background: rgb(var(--color-side));
          }
        }
      }
    }

    .Header_container {
      .Header_logo {
        padding: 0;
      }

      .container_wrapper {
        justify-content: space-between;
      }
    }
  }
}
</style>
