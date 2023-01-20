<template>
  <AppPage name="page">
    <Map :key="mapKey" ref="map" class="page_map" :center="mapPosition" :zoom="mapZoom">
      <!-- Test marker - Gdańsk Politechnika -->
      <l-marker
        v-for="place in allPlaces"
        :key="place.id"
        :lat-lng="[place.location.latitude, place.location.longitude]"
        @click="onPlaceSelected(place)"
        @mouseenter="showPlaceCard(place, $event.containerPoint)"
        @mouseleave="hidePlaceCard"
      >
      </l-marker>

      <l-marker 
        v-if="coords"
        :lat-lng="[coords.latitude, coords.longitude]"
        class="my-position-marker"
      >
        <l-icon 
          :icon-size="[44, 57]"
          :icon-anchor="[30, 94]"
         >
          <img src="/images/my_position3.svg" class="my-position-icon">
          <div class="headline">Tu jestem</div>
         </l-icon>
      </l-marker>
    </Map>

    <!-- Put all elements to draw on top of map here -->
    <div class="page_container">
      <PlaceSearch
        v-show="currentView === 'placeSearch'"
        id="placeSearch"
        class="page_place-search"
        @getCoords="setCoords"
        @onSearch="onPlaceSearch"
      />
      <PlaceList
        v-show="currentView === 'placeList'"
        class="page_place-list"
        id="placeList"
        :places="allPlaces"
        @onPlaceSelected="onPlaceSelected"
        @onClose="onPlaceListClose"
        @updateList="getPlaces()"
      />
      <PlaceDetails
        v-if="currentView === 'placeDetails' && selectedPlaceId"
        class="page_place-details"
        id="placeDetails"
        :placeId="selectedPlaceId"
        @onClose="onPlaceDetailsClose"
      />

      <!-- Hovered card -->
      <PlaceCard
        class="page_hovered-place"
        ref="hoveredPlaceCard"
        v-if="isHoveredPlaceCardVisible"
        :style="hoveredPlaceCardStyle"
        v-bind="hoveredPlace"
      />
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import Map from '@/components/Map';
import PlaceSearch from '@/components/place/PlaceSearch';
import PlaceList from '@/components/place/PlaceList';
import PlaceDetails from '@/components/place/PlaceDetails';
import PlaceCard from '@/components/place/PlaceCard';

export default {
  components: {
    AppPage,
    Map,
    PlaceSearch,
    PlaceList,
    PlaceDetails,
    PlaceCard,
  },

  layout: 'page',

  head() {
    return {
      title: 'Mapa',
    };
  },

  data() {
    return {
      coords: null,

      lastCoords: {
        latitude: 54.3739,
        longitude: 18.6214,
      },

      mapKey: 1,
      selectedPlaceId: null,
    };
  },

  watch: {
    coords(coords) {
      if (coords) this.lastCoords = { ...coords };
    },
  },

  computed: {
    allPlaces() {
      return this.$store.getters['facility/getFacilities'];
    },

    hoveredPlaceCardStyle() {
      const { x, y } = this.hoveredPlacePoint;
      const { innerWidth, innerHeight } = window;

      // TODO: Maybe get those values from ref value if dynamic size
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

    mapPosition() {
      return this.coords
        ? [this.coords.latitude, this.coords.longitude - 0.03]
        : [this.lastCoords?.latitude, this.lastCoords?.longitude - 0.03];
    },
  },

  async asyncData() {
    return {
      currentView: 'placeSearch',
      mapZoom: 13,

      // Hovered card
      hoveredPlace: null,
      hoveredPlacePoint: null,
      isHoveredPlaceCardVisible: false,
    };
  },

  methods: {
    onPlaceSelected({ id }) {
      this.selectedPlaceId = id;
      this.currentView = 'placeDetails';
    },

    onPlaceListClose() {
      this.currentView = 'placeSearch';
    },

    onPlaceDetailsClose() {
      this.currentView = 'placeList';
    },

    showPlaceCard(place, point) {
      this.hoveredPlace = place;
      this.hoveredPlacePoint = point;
      this.isHoveredPlaceCardVisible = true;
    },

    hidePlaceCard() {
      this.isHoveredPlaceCardVisible = false;
    },

    async onPlaceSearch(query) {
      console.log('Searching for places:');
      console.dir(query);
      this.currentView = 'placeList';
    },

    getPlaces() {
      // some ajax get
      // this.places = new Array()
    },

    setCoords(coords) {
      this.coords = coords;
      if (coords) this.mapKey++;
    },
  },
};
</script>

<style lang="scss">
.AppPage--page {
  position: relative;
  
  .page_map {
    width: 100vw;
    height: 100vh;
    display: none;
    z-index: 1;
  }

  .page_container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: row;
    justify-content: center;
    z-index: 2; // draw on map
  }

  .page_hovered-place {
    position: absolute;
  }
}

.headline {
  text-align: center;
  font-size: 1em;
  font-weight: bold;
  width: 60px;
  position: relative;
  left: -10px;
}

@media screen and (min-width: $desktop_breakpoint) {
  .AppPage--page {
    .page_map {
      display: block;
    }

    .page_place-search,
    .page_place-list,
    .page_place-details {
      position: absolute;
      top: 15%;
      left: 100px;
    }
  }
}
</style>
