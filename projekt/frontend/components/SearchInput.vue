<template>
  <div style="padding: 0 2rem; box-sizing: border-box">
    <InputText 
      v-model="value"
      name="search-address"
      :label="!loading ? 'Wpisz, aby zobaczyć proponowane adresy' : ''"
      :placeholder="placeholder || 'np. Gdańsk, Zwycięstwa'"
      :icon="{ show: true, tooltip: 'Ustaw moją lokalizację', name: 'my_location', size: 23 }"
      @input="getAddresses"
      @iconClicked="$emit('iconClicked')"
    >
      <Loader v-if="loading" style="margin: 1em 0;"/>
      <vue-scroll 
        v-else
        class="choose-result"
        :ops="scrollOptions"
      >
        <ul>
          <li
            v-for="(result, index) in possibleAddresses"
            :key="index"
            :class="[`single-search-result bg-extra-light-grey display-flex
            flex-column border-rounded cursor-pointer`, {
              'active': isActive(result),
            }]"
            @click="$emit('toggleAddress', result)"
          >
            {{ buildAddress(result) }}
          </li>
        </ul>
      </vue-scroll>
    </InputText>
  </div>
</template>

<script>
// eslint-disable-next-line
import { mapGetters } from 'vuex';
import InputText from '@/components/shared/InputText';
import Loader from '@/components/shared/Loader';

export default {
  components: {
    InputText,
    Loader,
  },
  props: {
    coords: {
      type: Object,
      default: null,
    },
    placeholder: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      value: '',
      delayTimer: null,
      loading: false,
      scrollOptions: {
        mode: 'native',
        sizeStrategy: 'percent',
        detectResize: true,
      },
    };
  },
  watch: {
    possibleAddresses(addresses) {
      this.$emit('resultsLength', !!addresses.length);
    },
  },
  computed: {
    ...mapGetters('facilitiesSearch', {
      possibleAddresses: 'getPossibleAddresses',
    }),
  },
  methods: {
    async getAddresses(value) {
      try {
        this.loading = true;
        this.$emit('loading', true);
        clearTimeout(this.delayTimer);
        this.delayTimer = setTimeout(() => {
          if (!value.length) {
            this.$store.commit('facilitiesSearch/setPossibleAddresses', []);
            this.$emit('toggleAddress', null);
          } else {
            this.$store.dispatch('facilitiesSearch/getAddresses', value);
          }
          
          this.loading = false;
          this.$emit('loading', false);
        }, 1000);
      } catch (error) {
        this.$notify({ text: 'Błąd pobierania możliwych adresów' });
      }
    },

    isActive(address) {
      return address?.location.longitude === this.coords?.longitude 
      && address?.location.latitude === this.coords?.latitude;
    },

    // eslint-disable-next-line
    buildAddress({ address: { city, road, neighbourhood, postcode }}) {
      return [city, road, neighbourhood, postcode]
        .filter((el) => !!el)
        .join(', ');
    },
  },
};
</script>

<style lang="scss" scoped>
.single-search-result {
  box-sizing: border-box;
  padding: .8rem .8rem;
  margin-top: .8em;
}

ul {
  max-height: 300px;
}

li.active {
  background-color: $light-blue;
}
</style>