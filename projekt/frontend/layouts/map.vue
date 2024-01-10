<template>
  <div class="LayoutMap">
    <Header />

    <main :class="['LayoutMap_main', {'map-open': isMapOpened}]">
      <Map
        :key="mapKey"
        ref="map"
        class="main_map"
        :center="mapPosition"
        :zoom="mapZoom"
      >
        <l-marker
          v-for="place in displayedPlaces"
          :key="place.id"
          :lat-lng="[place.location.latitude, place.location.longitude]"
          @click="onPlaceSelected(place)"
          @mouseenter="showPlaceCard(place, $event.containerPoint)"
          @mouseleave="hidePlaceCard"
        >
        </l-marker>

        <l-marker 
          v-if="isCustomCoords"
          :lat-lng="[coords.latitude, coords.longitude]"
        >
          <l-icon 
            :icon-size="[44, 57]"
            :icon-anchor="[30, 94]"
          >
            <img
              src="/images/my_position3.svg"
              class="my-position-icon"
            >
            <div
              class="main_headline"
              data-tid="user-location"
            >
              Twoja lokalizacja
            </div>
          </l-icon>
        </l-marker>
      </Map>

      <div class="main_container">
        <!-- Hovered card -->
        <PlaceCard
          class="main_hovered-place"
          ref="hoveredPlaceCard"
          v-if="isHoveredPlaceCardVisible"
          :style="hoveredPlaceCardStyle"
          v-bind="hoveredPlace"
        />

        <div v-if="!isBoxExpanded" class="main_expand-box">
          <IconToggleButton
            class="icon-hide"
            icon-name="keyboard_arrow_right"
            variant="dark"
            :size="32"
            @click="toggleBoxExpand"
            tooltip-text="Pokaż okno wyszukiwania"
          />
        </div>

        <div v-if="isBoxExpanded">
          <nuxt></nuxt>
        </div>
      </div>
    </main>

    <Authors class="LayoutMap_authors"/>

    <client-only>
      <notifications classes="main-notification" position="bottom left" width="370px" />
    </client-only>
  </div>
</template>

<script>
import Header from '@/components/Header';
import Authors from '@/components/Authors';
import Map from '@/components/Map';
import PlaceCard from '@/components/place/PlaceCard';
import IconToggleButton from '@/components/shared/IconToggleButton';

export default {
  components: {
    Header,
    Authors,
    Map,
    PlaceCard,
    IconToggleButton,
  },

  computed: {
    allPlaces() {
      return this.$store.getters['facility/getFacilities'];
    },

    filteredPlaces() {
      return this.$store.getters['facilitiesSearch/getFacilities'];
    },

    displayedPlaces() {
      return this.filteredPlaces || this.allPlaces;
    },

    coords() {
      return this.$store.getters['map/getCoords'];
    },

    isCustomCoords() {
      return this.$store.getters['map/getCustomCoords'];
    },

    mapKey() {
      return this.$store.getters['map/getKey'];
    },

    mapPosition() {
      const lonDiff = this.isMapMobile ? 0 : 0.03;
      const latDiff = this.isMapMobile ? 0 : 0.005;

      return this.zoomCords
        ? [this.zoomCords.latitude, this.zoomCords.longitude - lonDiff]
        : [this.coords.latitude - latDiff, this.coords.longitude - lonDiff];
    },

    isMapMobile() {
      return this.$store.getters['map/isMapMobile'];
    },

    hoveredPlaceCardStyle() {
      const { x, y } = this.hoveredPlacePoint;
      const { innerWidth, innerHeight } = window;
      
      const cardWidth = 460;
      const cardHeight = 105;
      const offset = 15;

      // Clamp X/Y values within screen size
      const cardX = x + cardWidth + offset > innerWidth ? x - cardWidth - offset : x + offset;
      const cardY = y + cardHeight + offset > innerHeight ? y - cardHeight - offset : y + offset;

      return {
        top: `${cardY}px`,
        left: `${cardX}px`,
      };
    },

    isBoxExpanded() {
      return this.$store.getters['boxExpand/getExpanded'];
    },

    isMapOpened() {
      return this.$store.getters['map/isMapOpened'];
    },
  },

  data() {
    return {
      zoomCords: null,
      mapZoom: 13,

      // Hovered card
      hoveredPlace: null,
      hoveredPlacePoint: null,
      isHoveredPlaceCardVisible: false,
    };
  },

  methods: {
    onPlaceSelected(place) {
      this.$router.push(`/place/${place.id}`);
      this.onMapToggled(false, true);
    },

    showPlaceCard(place, point) {
      if (window.innerWidth > 1280) {
        this.hoveredPlace = place;
        this.hoveredPlacePoint = point;
        this.isHoveredPlaceCardVisible = true;
      }
    },

    hidePlaceCard() {
      this.isHoveredPlaceCardVisible = false;
    },

    onPlaceOpened(place) {
      if (!place?.location) return;
      this.zoomCords = place.location;
    },

    onPlaceClosed() {
      this.zoomCords = null;
    },
    
    toggleBoxExpand(val = null) {
      this.$store.dispatch('boxExpand/setExpanded', val !== null ? val : !this.isBoxExpanded);
    },

    resetBoxExpand() {
      this.$store.dispatch('boxExpand/setExpanded', true);
    },

    onMapToggled(val = null, notSetMobile = false) {
      this.$store.dispatch('map/setMapState', val === null ? !this.isMapOpened : val);
      if (window.innerWidth >= 1280) {
        this.toggleBoxExpand(true);
      } else if (!this.isMapOpened) {
        this.toggleBoxExpand(true);
      } else {
        this.toggleBoxExpand(false);
      }
      
      if (!notSetMobile && val !== null) this.$store.dispatch('map/setisMapMobile', !val);
      this.$store.dispatch('map/incrementKey');
    },
  },
  mounted() {
    if (window.innerWidth >= 1280) {
      this.onMapToggled(true);
    } else {
      this.$store.dispatch('map/setisMapMobile', true);
    }

    window.addEventListener('resize', () => {
      if (window.innerWidth >= 1280) {
        this.onMapToggled(true);
      } else if (this.isMapOpened) {
        this.onMapToggled(false);
      } else {
        this.$store.dispatch('map/setisMapMobile', true);
      }
    });
  },
  created() {
    this.$nuxt.$on('map:toggleBoxExpand', this.toggleBoxExpand);
    this.$nuxt.$on('map:placeOpened', this.onPlaceOpened);
    this.$nuxt.$on('map:placeClosed', this.onPlaceClosed);
    this.$nuxt.$on('map:toggleMap', this.onMapToggled);
  },

  destroyed() {
    this.$nuxt.$off('map:toggleBoxExpand', this.toggleBoxExpand);
    this.$nuxt.$off('map:placeOpened', this.onPlaceOpened);
    this.$nuxt.$off('map:placeClosed', this.onPlaceClosed);
    this.$nuxt.$off('map:toggleMap', this.onMapToggled);
  },
  
  watch: {
    $route: {
      // eslint-disable-next-line
      handler: function (to, from) {
        this.resetBoxExpand();
      },
    },
  },
};
</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  border: 0;
  outline: 0;
  box-sizing: border-box;
  font-family: inherit;
}

.custom-zoom-marker {
  filter: saturate(4) invert(1) !important;
  width: 50px !important;
  height: 30px !important;
}

a {
  color: inherit;
  text-decoration: none;
}

body {
  max-width: 100%;

  font-size: 16px;
  font-family: 'Montserrat', sans-serif;

  --color-white: 255, 255, 255;
  --color-black: 0, 0, 0;

  // Layout colors:
  --color-text: 51, 51, 51;
  --color-main: 162, 190, 223;
  --color-side: 138, 169, 206;
  --color-detail-1: 106, 249, 137;
  --color-detail-2: 237, 75, 75;
}

html {
  scroll-behavior: smooth;
}

@media screen and (max-width: $desktop_breakpoint) {
  .LayoutMap_authors {
    display: none;
  }
}

.LayoutMap {
  .LayoutMap_main {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
    position: relative;

    .main_map {
      width: 100vw;
      height: 100vh;
      display: none;
      z-index: 1;
    }

     @media screen and (max-width: $desktop_breakpoint) {
      &:not(.map-open) {
        min-height: unset;
      }

      &.map-open {
        .main_map {
          display: block !important;
        }
      }
    }

    .main_headline {
      text-align: center;
      font-size: 1em;
      font-weight: bold;
      width: 100px;
      position: relative;
      left: -30px;
      background: white;
      border-radius: 10px;
      padding: 5px 10px;
      -webkit-box-shadow: 0px 1px 2px rgba(0,0,0,.25);
      -moz-box-shadow: 0px 1px 2px rgba(0,0,0,.25);
      -o-box-shadow: 0px 1px 2px rgba(0,0,0,.25);
      box-shadow: 0px 1px 2px rgba(0,0,0,.25);
    }

    .main_container {
      width: 100%;
      height: 100%;
      display: flex;
      flex-direction: row;
      justify-content: center;
      z-index: 2; // Above the map
    }

    .main_hovered-place {
      position: absolute;
    }

    .main_expand-box {
      display: none;
    }
  }
}

@media screen and (min-width: $desktop_breakpoint) {
  .LayoutMap {
    .LayoutMap_main {
      .main_map {
        display: block;
      }

      .main_expand-box {
        display: block;
        position: absolute;
        top: 16vh;
        left: 0;
        background: #fff;
        border-radius: 0 10px 10px 0;
        padding: 7px 3px;
        box-shadow: 0px 2px 4px rgba(var(--color-black), 0.125);
      }
    }

    .main-box {
      position: absolute;
      top: 10%;
      left: 50px;
    }
  }
}
</style>
