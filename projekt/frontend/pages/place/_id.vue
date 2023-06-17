<template>
  <PlaceDetails
    v-if="placeId"
    class="main-box"
    id="placeDetails"
    :placeId="placeId"
    @onClose="onPlaceDetailsClose"
  />
</template>

<script>
import PlaceDetails from '@/components/place/PlaceDetails';
import IconToggleButton from '@/components/shared/IconToggleButton';

export default {
  components: {
    PlaceDetails,
    IconToggleButton,
  },

  layout: 'map',

  head() {
    return {
      title: 'Szczegóły placówki',
    };
  },

  async fetch() {
    this.placeId = +this.$route.params.id;
    if (!this.placeId) this.$nuxt.error({ statusCode: 404 });
  },

  data() {
    return {
      placeId: null,
    };
  },

  methods: {
    async onPlaceDetailsClose() {
      const query = this.$store.getters['facilitiesQuery/getQuery'];

      // Go to first page or search results
      if (query) {
        this.$router.push({ path: '/places', query });
      } else {
        this.$store.dispatch('facilitiesSearch/reset');
        this.$router.push('/');
      }
    },
  },
};
</script>
