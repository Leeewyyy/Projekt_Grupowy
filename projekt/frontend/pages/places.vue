<template>
  <PlaceList
    class="main-box"
    id="placeList"
    :places="places"
    :isLoading="isLoading"
    @onPlaceSelected="onPlaceSelected"
    @onClose="onPlaceListClose"
    @hideBox="hideBox"
  />
</template>

<script>
import PlaceList from '@/components/place/PlaceList';
import IconToggleButton from '@/components/shared/IconToggleButton';

export default {
  components: {
    PlaceList,
    IconToggleButton,
  },

  layout: 'map',

  head() {
    return {
      title: 'Znalezione placówki',
    };
  },
  
  computed: {
    places() {
      return this.$store.getters['facilitiesSearch/getFacilities'] || [];
    },

    placesQuery() {
      return this.$route.query;
    },
  },

  async fetch() {
    const showPreviouslyLoaded = this.placesQuery ? !Object.keys(this.placesQuery).includes('search') : true;
    await this.loadPlaces(showPreviouslyLoaded);
  },

  data() {
    return {
      query: {},
      isLoading: false,
    };
  },

  methods: {
    async loadPlaces(showPreviouslyLoaded = false) {
      // Show previously loaded facilities
      if (showPreviouslyLoaded && this.$store.getters['facilitiesSearch/getFacilities'] !== null) return;
      console.log(showPreviouslyLoaded);
      this.isLoading = true;

      try {
        await this.$store.dispatch('facilitiesSearch/searchFacilities', this.placesQuery);
        await this.$store.dispatch('facilitiesQuery/setQuery', this.placesQuery);
      } catch (error) {
        if (process.client) this.$notify({ text: 'Nie udało się pobrać placówek.', type: 'error' });
        console.error(error);
      }

      this.isLoading = false;
    },

    async onPlaceSelected(place) {
      this.$router.push(`/place/${place.id}`);
    },

    async onPlaceListClose() {
      this.$store.dispatch('facilitiesSearch/reset');
      this.$router.push('/');
    },

    hideBox() {
      this.$nuxt.$emit('map:toggleBoxExpand');
    },
  },

  watch: {
    '$route.query': {
      deep: true,
      // eslint-disable-next-line
      handler: async function(oldValue, newValue) {
        if (oldValue === newValue) return;
        await this.loadPlaces(false);
      },
    },
  },
};
</script>
