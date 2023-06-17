<template>
  <PlaceList
    class="main-box"
    id="placeList"
    :places="places"
    :isLoading="isLoading"
    @onPlaceSelected="onPlaceSelected"
    @onClose="onPlaceListClose"
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
  },

  async fetch() {
    const { query } = this.$route;
    this.query = { ...query };

    await this.loadPlaces();
    await this.$store.dispatch('facilitiesQuery/setQuery', this.query);
  },

  data() {
    return {
      query: {},
      isLoading: false,
    };
  },

  methods: {
    async loadPlaces() {
      // Facilities already loaded
      if (this.$store.getters['facilitiesSearch/getFacilities'] !== null) return;
      
      this.isLoading = true;

      try {
        await this.$store.dispatch('facilitiesSearch/searchFacilities', this.query);
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
  },
};
</script>
