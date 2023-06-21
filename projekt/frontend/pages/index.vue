<template>
  <PlaceSearch
    id="placeSearch"
    class="main-box"
    @getCoords="updateMapCoords"
    @onSearch="searchPlaces"
    @hideBox="hideBox"
  />
</template>

<script>
import PlaceSearch from '@/components/place/PlaceSearch';
import IconToggleButton from '@/components/shared/IconToggleButton';

export default {
  components: {
    PlaceSearch,
    IconToggleButton,
  },

  layout: 'map',

  head() {
    return {
      title: 'Wyszukaj placówki',
    };
  },

  methods: {
    updateMapCoords(coords) {
      if (!coords) return;
      this.$store.dispatch('map/setCoords', coords);
      this.$store.dispatch('map/setCustomCoords', true);
      this.$store.dispatch('map/incrementKey');
    },

    async searchPlaces(form) {
      console.log('Searching places!');
      // Skip empty form parameters
      const query = Object.keys(form)
        .reduce((obj, key) => {
          const value = form[key];
          if (value !== null && value !== undefined) obj[key] = value;
          return obj;
        }, {});
        
      this.$router.push({ path: '/places', query });
    },

    hideBox() {
      this.$nuxt.$emit('map:toggleBoxExpand');
    },
  },
};
</script>
