<template>
  <BoxSection
    class="PlaceSearch main-container"
    :showBottomButtons="step !== SEARCH_STEPS.WELCOME"
    :showSubmit="step === SEARCH_STEPS.MORE_FILTERS"
    @onBack="handlePreviousStep"
    @onSubmit="submitSearch"
  >
    <template #header>
      <div class="header_logo mobile-hidden">
        <Branding
          id="placeSearchBranding"
          description="Placówki medyczne w Twojej okolicy"
        />
      </div>
      <CollapseButton class="mobile-hidden" @collapse="$emit('hideBox')" />
    </template>
    <template #body>
      <form>
        <SearchInput
          :key="searchKey"
          :placeholder="tmpPlaceholder"
          :coords="coords"
          :hide-results="step === SEARCH_STEPS.MORE_FILTERS"
          :hide-label="step === SEARCH_STEPS.MORE_FILTERS"
          @toggleAddress="toggleAddress"
          @resultsLength="handleAddresses"
          @loading="step = SEARCH_STEPS.RESULTS_VISIBLE"
          @iconClicked="getCurrentPosition()"
          style="margin-bottom: .5rem;"
        />
        <WelcomeBox v-if="step === SEARCH_STEPS.WELCOME" />
        <div v-if="step === SEARCH_STEPS.MORE_FILTERS" class="more-filters">
          <div class="display-flex align-center justify-between first-row">
            <Select
              name="place-type"
              v-model="form.placeType"
              :options="facilitiesTypes"
              label="name"
              select-label="Wybierz typ placówki"
              class="select"
            />
            <SwitchButton id="switch-nfz" v-model="form.nfzStatus" description="NFZ" class="switch" />
          </div>

          <Select
            name="doctors-select"
            v-model="form.doctor"
            :options="specialistsTypes"
            select-label="Wybierz specjalizację lekarza"
            class="second-row"
          />
          
          <div class="buttons display-flex align-center justify-center flex-wrap">
            <Button 
              v-for="distance in distanceOptions"
              :key="distance.value"
              type="button"
              :class="['button', { 'wider': distance.isWiderButton }]"
              :active="form.maxDistance === distance.value"
              @click="form.maxDistance = distance.value"
            >
              {{ distance.name }}
            </Button>
          </div>
          <label class="buttons-label">Wybierz max. odległość od wskazanego adresu</label>
        </div>
        <!-- <BackButton v-if="!showWelcomeBox" /> -->
        <!-- <div class="buttons">
          <Button v-if="coords" class="submit-button" type="submit">Szukaj</Button>
        </div> -->
      </form>
      <!-- <form v-else class="PlaceSearch_container main-container" @submit.prevent="submitSearch">
        <div class="PlaceSearch_container_inner">
          <div class="outer-input">
            <InputText
              name="address-input"
              v-model="form.search"
              :placeholder="tmpPlaceholder.length ? tmpPlaceholder : 'np. Gdańsk, al. Zwycięstwa'"
              :icon="{ 
                show: true,
                name: 'my_location',
                tooltip: 'Wyszukaj lokalizację',
                size: 23,
              }"
              label="Wpisz, aby zobaczyć proponowane adresy"
              @input="/*getPossibleResults*/"
            />
              <vue-scroll 
                v-if="!moreFiltersOn && possibleAddresses && possibleAddresses.length"
                class="choose-result"
                :ops="scrollOptions"
              >
                <ul>
                  <li
                    v-for="(result, index) in possibleAddresses"
                    :key="index"
                    :class="{ active: isActive(result) }"
                    @click="toggleAddress(result)"
                  >
                    {{ buildAddress(result) }}
                  </li>
                </ul>
              </vue-scroll>
              <div 
                v-else-if="!moreFiltersOn && form.search && form.search.length"
                class="write-more"
              ><span>Pisz dalej...</span></div>
          </div>

          <div class="outer-input">
            <Select
              name="doctors-select"
              v-model="form.doctor"
              :options="specialistsTypes"
              description="Lekarz"
              style="margin-top: 10px"
              :tab-index="3"
            />
          </div>

          <div class="buttons">
            <Button
              tabindex="5"
              name="clear-button"
              type="button"
              variant="light"
              @click="clearForm()"
            >
              Wyczyść
            </Button>

            <Button 
              tabindex="6"
              name="submit-button"
              type="submit"
              :disabled="loading"
              variant="dark"
            >
              {{ loading ? 'Ładowanie...' : 'Szukaj' }}
            </Button>
          </div>
        </div>

        <button
          tabindex="7"
          type="button"
          class="filters-label"
          @click="moreFiltersOn = !moreFiltersOn"
        >
          <span class="label">
            {{ `Pokaż ${ moreFiltersOn ? 'mniej' : 'więcej' } opcji filtrowania` }}
          </span>
          <Icon :name="`${moreFiltersOn ? 'keyboard_arrow_up' : 'keyboard_arrow_down'}`" />
        </button>

        <div v-if="moreFiltersOn" id="bottomFilters" class="PlaceSearch_container_inner">
          <div class="bottom-filters">
            <Select
              name="places-select"
              v-model="form.placeType"
              :options="facilitiesTypes"
              label="name"
              description="Typ placówki"
              :tab-index="8"
            />

            <Select
              name="distance-select"
              v-model="form.maxDistance"
              :options="distanceOptionsForSelect"
              description="Odległość"
              label="name"
              :icon-text="`
                Odległość brana jest pod uwagę dopiero wtedy,<br />
                gdy wybierzesz adres. Jeśli zostawisz to pole puste <br />
                system szuka placówek w całej Polsce.
              `"
              :tab-index="9"
            />
          </div>
        </div>
      </form> -->
    </template>
  </BoxSection>
</template>

<script>
// eslint-disable-next-line
import { mapGetters } from 'vuex';
import BoxSection from '@/components/BoxSection';
import SearchInput from '@/components/SearchInput';
import Select from '@/components/shared/Select';
import Button from '@/components/shared/Button';
import IconToggleButton from '@/components/shared/IconToggleButton';
import Icon from '@/components/shared/Icon';
import WelcomeBox from '@/components/WelcomeBox';
import Branding from '@/components/Branding';
import CollapseButton from '@/components/CollapseButton';
import SingleWelcomeBox from '@/components/SingleWelcomeBox';
import BottomButtons from '@/components/BottomButtons';
import SwitchButton from '@/components/shared/SwitchButton';
import { SEARCH_STEPS } from '../../consts';

export default {
  props: {
    loading: {
      type: Boolean,
      default: false,
    },
  },
  components: {
    BoxSection,
    SearchInput,
    Select,
    Button,
    IconToggleButton,
    Icon,
    WelcomeBox,
    Branding,
    CollapseButton,
    SingleWelcomeBox,
    BottomButtons,
    SwitchButton,
  },

  data() {
    return {
      SEARCH_STEPS,
      form: {
        search: null,
        doctor: null,
        placeType: null,
        maxDistance: 50,
        nfzStatus: null,
      },

      moreFiltersOn: false,
      iconLocationON: false,
      tmpPlaceholder: '',

      coords: null,
      activeAddress: null,
      distanceOptions: [{
        value: 10,
        name: '10 km',
      }, {
        value: 25,
        name: '25 km',
      }, {
        value: 50,
        name: '50 km',
      }, {
        value: 100,
        name: '100 km',
      }, {
        value: 200,
        name: '200 km',
      }, {
        value: 500,
        name: '500 km',
      }, {
        value: 1000,
        name: 'Cała Polska',
        isWiderButton: true,
      }],
      delayTimer: null,
      searchKey: 0,
      addressSelected: false,
      showSubmit: false,
      step: SEARCH_STEPS.WELCOME,
      addressesVisible: false,
    };
  },
  
  mounted() {
    try {
      this.$store.dispatch('facilitiesSearch/getFacilitiesTypes');
      this.$store.dispatch('facilitiesSearch/getSpecialistsTypes');
    } catch (e) {
      this.$notify({ text: 'Wystąpił błąd pobierania danych. Spróbuj odświeżyć stronę.', type: 'error' });
    }
  },
  computed: {
    ...mapGetters('facilitiesSearch', {
      possibleAddresses: 'getPossibleAddresses',
      facilitiesTypes: 'getFacilitiesTypes',
      specialistsTypes: 'getSpecialistsTypes',
    }),
  },
  watch: {
    // eslint-disable-next-line
    'form.search'() {
      this.moreFiltersOn = false;
    },
    step(step) {
      if (step === SEARCH_STEPS.WELCOME) this.resetForm();
    },
  },
  methods: {
    resetForm() {
      this.form = {
        search: null,
        doctor: null,
        placeType: null,
        maxDistance: 50,
        nfzStatus: null,
      };

      this.activeAddress = null;
      this.tmpPlaceholder = '';
      this.$store.commit('facilitiesSearch/setPossibleAddresses', []);
      this.searchKey++;
      this.coords = null;
      this.$emit('getCoords', null);
      this.showSubmit = false;
      this.step = SEARCH_STEPS.WELCOME;
    },

    submitSearch() {
      console.log('Search submitted!');

      this.$emit('onSearch', {
        ...this.form,
        ...this.coords,
      });

      this.resetForm();
    },

    getCurrentPosition() {
      const onSuccess = ({ coords }) => {
        const { latitude, longitude } = coords;
        this.searchKey++;
        this.tmpPlaceholder = 'Twoja lokalizacja';
        this.coords = { 
          latitude,
          longitude,
        };
        this.activeAddress = null;
        this.$store.commit('facilitiesSearch/setPossibleAddresses', []);
        this.$nextTick(() => {
          this.$emit('getCoords', this.coords);
          this.showSubmit = true;
          this.step = SEARCH_STEPS.MORE_FILTERS;
          this.addressesVisible = false;
          this.$notify({ text: 'Ustawiono Twoją lokalizację, jako domyślny adres wyszukiwania', type: 'success' });
        });
      };

      const onError = (error) => {
        console.error(error.message);
      };

      navigator.geolocation.getCurrentPosition(onSuccess, onError);
    },

    toggleAddress(address) {
      if (this.isActive(address) || !address) {
        this.coords = null;
        this.activeAddress = null;
        this.tmpPlaceholder = '';
        this.showSubmit = false;
        this.step = SEARCH_STEPS.RESULTS_VISIBLE;
      } else {
        this.activeAddress = address;
        this.coords = address?.location;
        this.tmpPlaceholder = this.buildAddress(address);
        this.showSubmit = true;
        this.searchKey++;
        this.step = SEARCH_STEPS.MORE_FILTERS;
      }

      this.$emit('getCoords', this.coords);
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

    handleAddresses(length) {
      this.addressesVisible = length;
      if (length) {
        this.step = SEARCH_STEPS.RESULTS_VISIBLE;
      } else {
        this.step = SEARCH_STEPS.WELCOME;
      }
    },

    handlePreviousStep() {
      if (!this.addressesVisible) {
        this.step = SEARCH_STEPS.WELCOME;
        return;
      }

      this.step--;
    },
  },
};
</script>

<style lang="scss">
.PlaceSearch {
  
  @media screen and (max-width: $desktop_breakpoint) {
    display: flex;
    align-items: center;  
    justify-content: center;
    flex-direction: column;
    background: rgb(var(--color-main));
    min-height: 80vh;

    .BoxSection_body, .BoxSection_header {
      width: 100%;
    }
  }

  .place-search-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .icon-hide {
      padding-right: 31px;
    }

    @media screen and (max-width: $desktop_breakpoint) {
      justify-content: center;

      .icon-hide {
        display: none;
      }
    }
  }

  .PlaceSearch_title {
    height: 55px;
    line-height: 55px;
    text-indent: 2rem;
    font-size: 1.2rem;

    .title {
      &-desktop {
        display: block;
      }
      
      &-mobile {
        display: none;
      }
    }

    @media screen and (max-width: $desktop_breakpoint) {
      font-size: 1.2em;
      text-indent: unset;
      height: 140px;
      line-height: 1.2em;
      display: flex;
      align-items: center;
      justify-content: center;
      text-align: center;
      background: transparent;

      .title {
        &-desktop {
          display: none;
        }
        
        &-mobile {
          display: block;
        }
      }
    }
  }

  .PlaceSearch_container {
    // Temporary only for placeholder

    padding: 2rem;
    padding-bottom: 1rem;
    background: rgb(var(--color-main));

    .PlaceSearch_container_inner {
      box-sizing: border-box;
      background: rgb(var(--color-side));
      padding: 20px;
      border-radius: 10px;

      &#bottomFilters {
        margin: 15px 0;
      }

      .bottom-filters {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: wrap;

        .select-outer:first-of-type {
          width: 55%;
        }

        .select-outer:last-of-type {
          width: 40%;
        }

        @media screen and (max-width: $desktop_breakpoint) {
          .select-outer:first-of-type, .select-outer:last-of-type {
            width: 100%;
          }

          .select-outer:last-of-type {
            margin-top: 1em;
          }
        }
      }

      .choose-result {
        background: #fff;
        padding: 7px 0 !important;
        width: 87% !important;
        margin-top: 10px;
        margin-bottom: 10px;
        border-radius: 10px;

        ul {
          max-height: 110px;
          list-style-type: none;
          padding: 0 7px;
        }

        li {
          color: rgb(var(--color-text));
          padding: 8px 10px;
          border-radius: 10px;
          box-sizing: border-box;
          transition: all .1s;
          font-size: 0.9em;

          &.active {
            background: rgba(51, 51, 51, .3);
            font-weight: bold;
            font-size: 1em;
            margin-top: 5px;
            margin-bottom: 5px;
          }

          &:hover, &:focus {
            background: rgba(51, 51, 51, .1);
            cursor: pointer;
          }
        }

        @media screen and (max-width: $desktop_breakpoint) {
          width: 95% !important;
        }

        @media screen and (max-width: $tablet_breakpoint) {
          width: 85% !important;
        }
      }

      .write-more {
        font-size: .9em;
        margin-top: 5px;
      }
    }

    @media screen and (max-width: $desktop_breakpoint) {
      padding: 0 1em 3em 1em;
    }
  }

  .outer-input {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    flex-wrap: wrap;

    .text-field-outer,
    .select-outer {
      width: 87%;

      @media screen and (max-width: $desktop_breakpoint) {
        width: 95%;
      }

      @media screen and (max-width: $tablet_breakpoint) {
        width: 85%;

        .text-field, .multiselect__tags {
          padding-left: 20px !important;
        }
      }
    }
  }

  .filters-label {
    background: transparent;
    display: flex;
    align-items: center;
    margin-top: 15px;
    margin-left: 22px;
    font-size: 0.9em;
    cursor: pointer;

    &:focus {
      span.label {
        text-decoration: underline;
      }
    }

    .material-icons {
      margin-left: 5px;
    }

    @media screen and (max-width: $desktop_breakpoint) {
      margin: 15px auto 0 auto;
      font-size: 1em;
    }
  }
}

.more-filters {
  padding: 0 2rem;

  .first-row {
    margin-top: 1rem;

    .select {
      width: 82%;
    }

    .switch {
      position: relative;
      top: 10px;

      .SwitchButton_label {
        bottom: 5px;
      }
    }
  }

  .second-row {
    margin-top: 1rem;
    width: 100%;
  }

  .buttons {
    margin-top: 1rem;
    gap: 10px;

    .button {
      width: 23%;
      padding-left: 10px;

      &.wider {
        width: 48%;
      }
    }
  }

  .buttons-label {
    font-size: 13px;
    padding-left: 10px;
    position: relative;
    top: 5px;
  }
}
</style>
