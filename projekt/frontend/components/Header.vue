<template>
  <header
    class="Header"
    :class="{
      'Header--dark': isDark,
      'Header--menu-visible': isNavigationVisible,
    }">
    <div class="Header_container">
      <div class="container_wrapper">
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
import { SEARCH_STEPS } from '../consts';

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
      SEARCH_STEPS,
      isNavigationVisible: false,
    };
  },

  methods: {
    toggleNavigation() {
      this.isNavigationVisible = !this.isNavigationVisible;
    },

    closeNavigation() {
      this.$nuxt.$emit('map:placeClosed');
      this.isNavigationVisible = false;
    },
  },

  watch: {
    '$route.path': {
      // eslint-disable-next-line
      handler: function (val) {
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
  padding: 0 1.5rem 0 3rem;
  z-index: 10;
  font-weight: 20px;
  background: white;
  -webkit-box-shadow: 0 2px 2px rgba(0,0,0, .125); 
  -moz-box-shadow: 0 2px 2px rgba(0,0,0, .125); 
  -o-box-shadow: 0 2px 2px rgba(0,0,0, .125); 
  box-shadow: 0 2px 2px rgba(0,0,0, .125); 

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
      min-width: 120px;
      margin-right: 0.5rem;

      .logo_link {
        font-weight: bold;
      }
    }

    .Header_navigation {
      width: 100%;
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
    box-sizing: border-box;
    background: #fff;
    position: relative;

    &.Header--dark {
      background: rgb(var(--color-main));

      .Header_navigation-toggle {
        margin-left: auto;
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
      height: calc(100vh - 60px);
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
