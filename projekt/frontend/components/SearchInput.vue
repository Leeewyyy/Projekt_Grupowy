<template>
  <div class="SearchInput" style="box-sizing: border-box">
    <InputText
      v-model="value"
      name="search-address"
      :label="!loading && !hideLabel ? 'Wpisz, aby zobaczyć proponowane adresy' : ''"
      :placeholder="placeholder || 'np. Gdańsk, Zwycięstwa'"
      :icon="{ show: showIcon, tooltip: 'Ustaw moją lokalizację', name: 'my_location', size: 23 }"
      @input="getAddresses"
      @iconClicked="$emit('iconClicked')"
    >
      <Loader v-if="loading" style="margin: 1em 0;"/>
      <vue-scroll 
        v-else-if="!hideResults"
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
    hideResults: {
      type: Boolean,
      default: true,
    },
    hideLabel: {
      type: Boolean,
      default: false,
    },
    showIcon: {
      type: Boolean,
      default: true,
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
        this.delayTimer = setTimeout(async () => {
          if (!value.length) {
            await this.$store.commit('facilitiesSearch/setPossibleAddresses', []);
            this.$emit('toggleAddress', null);
          } else {
            await this.$store.dispatch('facilitiesSearch/getAddresses', value);
          }
          
          this.loading = false;
          this.$emit('loading', false);
        }, 500);
      } catch (error) {
        this.$notify({ text: 'Błąd pobierania możliwych adresów', type: 'error' });
      }
    },

    isActive(address) {
      return address?.location.longitude === this.coords?.longitude 
      && address?.location.latitude === this.coords?.latitude;
    },

    // eslint-disable-next-line
    buildAddress({ address: { city, road, postcode, house_number }}) {
      // eslint-disable-next-line
      return (house_number ? [city, `${road} ${house_number}`, postcode] : [city, road, postcode])
        .filter((el) => !!el)
        .join(', ');
    },

    async clearAddresses() {
      try {
        await this.$store.commit('facilitiesSearch/setPossibleAddresses', []);
      } catch (e) {
        this.$notify({ text: 'Błąd resetu adresów', type: 'error' });
      }

      this.value = '';
      this.$emit('toggleAddress', null);
    },
  },
};
</script>

<style lang="scss" scoped>
.SearchInput {
  min-width: 400px;
}

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
