<template>
  <AppPage name="page">
    <Map ref="map" class="page_map" :center="mapPosition" :zoom="mapZoom">
      <!-- Test marker - Gdańsk Politechnika -->
      <l-marker
        v-for="place in places"
        :key="place.id"
        :lat-lng="[place.lat, place.lng]"
        @click="onPlaceSelected(place)"
        @mouseenter="showPlaceCard(place, $event.containerPoint)"
        @mouseleave="hidePlaceCard"
      ></l-marker>
    </Map>

    <!-- Put all elements to draw on top of map here -->
    <div class="page_container">
      <PlaceSearch
        v-show="currentView === 'placeSearch'"
        id="placeSearch"
        class="page_place-search"
        @getCoords="coords = $event"
        @onSearch="onPlaceSearch"
      />
      <PlaceList
        v-show="currentView === 'placeList'"
        class="page_place-list"
        id="placeList"
        :places="places"
        @onPlaceSelected="onPlaceSelected"
        @onClose="onPlaceListClose"
        @updateList="getPlaces()"
      />
      <PlaceDetails
        v-if="currentView === 'placeDetails' && selectedPlace"
        class="page_place-details"
        id="placeDetails"
        :place="selectedPlace"
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
import TestPlaces from '~/assets/test-places.js';

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
      selectedPlace: null,
    };
  },

  computed: {
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
        : [54.3739, 18.6214];
    },
  },

  async asyncData() {
    return {
      currentView: 'placeSearch',
      mapZoom: 13,

      // Test places from assets file
      places: TestPlaces,

      // Hovered card
      hoveredPlace: null,
      hoveredPlacePoint: null,
      isHoveredPlaceCardVisible: false,
    };
  },

  // PRZYKŁAD POBIERANIA PLACÓWEK
  // TO TRZEBA PRZENIEŚĆ DO ASYNCDATA
  async mounted() {
    try {
      const medicalFacilities = await this.$store.dispatch('medicalFacilities/fetchAll');
      console.info(`Pobrano listę ${medicalFacilities?.length} placówek z backendu. Pierwsza placówka:`);
      console.dir(medicalFacilities?.[0]);
    } catch (error) {
      console.error(`Error przy pobieraniu placówek: ${error}!`);
    }
  },

  methods: {
    onPlaceSelected(place) {
      this.selectedPlace = place;
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
