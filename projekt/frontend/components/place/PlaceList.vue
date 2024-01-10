<template>
  <BoxSection
    class="PlaceList main-container"
    :class="{
      'PlaceList--selectable': selectable,
      'account-container': $route.name === 'user-panel',
    }"
    :show-bottom-buttons="closable"
    @onBack="onBack"
  >
    <template #header>
      <div v-if="!customTitle" class="PlaceList_header">
        <div class="header_logo mobile-hidden">
          <Branding
            id="placeListBranding"
            description="Placówki medyczne w Twojej okolicy"
          />
        </div>
        <div class="header_title">
          <span v-if="isLoading">Trwa ładowanie placówek...</span>
          <span v-else data-tid="place-list-count">Znaleziono <b class="title_count">{{ places.length }}</b> wyników</span>
        </div>
        <CollapseButton class="mobile-hidden" @collapse="$emit('hideBox')" />
      </div>
      <div v-else class="PlaceList_header">
        <h2 class="header_title">
          {{ customTitle }}
        </h2>
        <slot name="header-side" />
      </div>
    </template>
    <template #body>
      <div class="PlaceList_container" data-tid="place-list-container">
        <Loader v-if="isLoading" style="padding: 1rem;" />
        <div v-else-if="!places.length" class="container_empty">
          Nie znaleziono żadnych miejsc spełniających kryteria.
        </div>
        <vue-scroll v-else :ops="scrollOptions">
          <ul class="container_list" data-tid="place-list">
            <li class="list_item" v-for="place in places" :key="place.id" data-tid="place-list-item">
              <PlaceCard
                v-bind="place"
                class="item_place-card"
                :full-width="true"
                :is-own="areOwnPlaces"
                @click="onPlaceSelected(place)"
                @favoriteChanged="updateList"
                @placeDeleted="updateList"
                data-tid="place-list-card"
              />
            </li>
          </ul>
        </vue-scroll>
      </div>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import Branding from '@/components/Branding';
import PlaceCard from '@/components/place/PlaceCard';
import Icon from '@/components/shared/Icon';
import CollapseButton from '@/components/CollapseButton';
import Loader from '@/components/shared/Loader';

export default {
  components: {
    BoxSection,
    Branding,
    PlaceCard,
    Icon,
    CollapseButton,
    Loader,
  },

  props: {
    customTitle: {
      type: String,
      default: null,
    },
    places: {
      type: Array,
      default: () => [],
    },
    selectable: {
      type: Boolean,
      default: true,
    },
    closable: {
      type: Boolean,
      default: true,
    },
    isLoading: {
      type: Boolean,
      default: false,
    },
    areOwnPlaces: {
      type: Boolean,
      default: false,
    },
  },

  data() {
    return {
      scrollOptions: {
        mode: 'native',
        sizeStrategy: 'percent',
        detectResize: true,
      },
    };
  },

  computed: {
    welcomeCookie() {
      return this.$store.getters['cookie/getShowWelcomeBoxCookie'];
    },
  },

  methods: {
    onPlaceSelected(place) {
      this.$emit('onPlaceSelected', place);
    },

    onBack() {
      this.$emit('onClose');
    },

    updateList() {
      this.$emit('updateList');
    },
  },
};
</script>

<style lang="scss">
.account-container {
  min-height: auto !important;
  padding-bottom: 20px;
}

.PlaceList {
  @media screen and (max-width: $desktop_breakpoint) {
    background: #FDFDFD;
    box-shadow: unset;
  }

  &.PlaceList--selectable {
    .item_place-card {
      cursor: pointer;
    }
  }

  .PlaceList_header {
    padding: 1rem;
    display: flex;
    gap: 10px;

    .header_title {
      text-indent: 1rem;
      font-size: 1.2rem;
      font-weight: 600;
      display: flex;
      align-items: center;
    }
  }

  .PlaceList_container {
    padding: 0 2rem;
    background: $white;

    .container_list {
      max-height: 50vh;
      list-style-type: none;

      .list_item {
        margin-bottom: 1rem;

        .item_place-card {
          background: $extra-light-grey;
        }

        &:last-child {
          margin-bottom: 0;
        }
      }

      @media screen and (max-width: $desktop_breakpoint) {
        max-height: unset;
        padding-bottom: 2rem;
      }
    }

    @media screen and (max-width: $desktop_breakpoint) {
      padding: 0 1rem !important;
    }
  }
}

@media screen and (max-width: $tablet_breakpoint) {
  .PlaceList {
    .PlaceList_container {
      padding: 1rem;
    } 
  }
}
</style>
