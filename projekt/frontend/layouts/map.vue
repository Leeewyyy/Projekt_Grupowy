<template>
  <div class="LayoutMap">
    <Header />

    <main class="LayoutMap_main">
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
            <img src="/images/my_position3.svg" class="my-position-icon">
            <div class="main_headline">
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
        
        <nuxt></nuxt>
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

export default {
  components: {
    Header,
    Authors,
    Map,
    PlaceCard,
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
      return [this.coords.latitude, this.coords.longitude - 0.03];
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
  },

  data() {
    return {
      mapZoom: 13,

      // Hovered card
      hoveredPlace: null,
      hoveredPlacePoint: null,
      isHoveredPlaceCardVisible: false,
    };
  },

  methods: {
    onPlaceSelected({ id }) {
      this.$router.push(`/place/${id}`);
    },

    showPlaceCard(place, point) {
      this.hoveredPlace = place;
      this.hoveredPlacePoint = point;
      this.isHoveredPlaceCardVisible = true;
    },

    hidePlaceCard() {
      this.isHoveredPlaceCardVisible = false;
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

    @media screen and (max-width: $desktop_breakpoint) {
      min-height: unset;
    }

    .main_map {
      width: 100vw;
      height: 100vh;
      display: none;
      z-index: 1;
    }

    .main_headline {
      text-align: center;
      font-size: 1em;
      font-weight: bold;
      width: 60px;
      position: relative;
      left: -10px;
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
  }
}

@media screen and (min-width: $desktop_breakpoint) {
  .LayoutMap {
    .LayoutMap_main {
      .main_map {
        display: block;
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
