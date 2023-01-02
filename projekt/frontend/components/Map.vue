<template>  
  <div class="Map">
    <client-only>
      <l-map :center="center" :zoom="zoom" :options="mapOptions">
        <l-tile-layer
          :url="tileServerUrl"
          attribution="&copy; <a href='https://stadiamaps.com/'>Stadia Maps</a>, &copy; <a href='https://openmaptiles.org/'>OpenMapTiles</a> &copy; <a href='http://openstreetmap.org'>OpenStreetMap</a> contributors"
        ></l-tile-layer>

        <!-- Add zoom controls to bottom right -->
        <l-control-zoom position="bottomright"></l-control-zoom>
        
        <!-- Custom data managed by parent component -->
        <slot></slot>
      </l-map>
    </client-only>
  </div>
</template>

<script>
export default {
  props: {
    zoom: {
      type: Number,
      default: 10,
    },
    center: {
      type: Array,
      default: null,
    },
  },

  data() {
    return {
      // Różni providerzy mają różne style:
      // https://leaflet-extras.github.io/leaflet-providers/preview/
      tileServerUrl: 'https://tiles.stadiamaps.com/tiles/osm_bright/{z}/{x}/{y}{r}.png',

      mapOptions: {
        zoomControl: false,
      },
    };
  },

  methods: {
    getMapInstance() {
      return this.$refs.map;
    },
  },
};
</script>

<style lang=>
.Map {
  width: 100%;
  height: 100vh;
}
</style>
