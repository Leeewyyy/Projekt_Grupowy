<template>
  <BoxSection
    class="PlaceSearch main-container"
    :showBottomButtons="step !== SEARCH_STEPS.WELCOME"
    :showSubmit="step === SEARCH_STEPS.MORE_FILTERS"
    @onBack="handlePreviousStep"
    @onSubmit="submitSearch"
  >
    <template #header>
      <div class="header_logo">
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
          :placeholder="placeholder"
          :coords="coords"
          :hide-results="step === SEARCH_STEPS.MORE_FILTERS"
          :hide-label="step === SEARCH_STEPS.MORE_FILTERS"
          @toggleAddress="toggleAddress"
          @resultsLength="handleAddresses"
          @loading="setStep(SEARCH_STEPS.RESULTS_VISIBLE)"
          @iconClicked="getCurrentPosition()"
          style="margin-bottom: 0.5rem; padding: 0 2rem;"
          data-tid="address-search-input"
        />
        <WelcomeBox v-if="step === SEARCH_STEPS.WELCOME" />
        <div v-if="step === SEARCH_STEPS.MORE_FILTERS" class="more-filters" data-tid="place-filters">
          <div class="display-flex align-center justify-between first-row">
            <Select
              name="placeType"
              :value="formState.type"
              :options="facilitiesTypes"
              label="name"
              select-label="Wybierz typ placówki"
              @input="(value) => setFormState({ key: 'type', value })"
              class="select"
              data-tid="place-type-select"
            />
            <SwitchButton id="switch-nfz" :model-value="formState.isNFZ" @change="(value) => setFormState({ key: 'isNFZ', value })" description="NFZ" class="switch" data-tid="nfz-switch" />
          </div>

          <Select
            name="specialization"
            :value="formState.specialization"
            :options="specialistsTypes"
            select-label="Wybierz specjalizację lekarza"
            @input="(value) => setFormState({ key: 'specialization', value })"
            class="second-row"
            data-tid="specialization-select"
          />
          
          <div class="buttons display-flex align-center justify-center flex-wrap">
            <Button 
              v-for="{ value, isWiderButton, name } in distanceOptions"
              :key="value"
              type="button"
              :class="['button', { 'wider': isWiderButton }]"
              :active="formState.distance === value"
              @click="setFormState({ key: 'distance', value })"
              data-tid="distance-button"
            >
              {{ name }}
            </Button>
          </div>
          <label class="buttons-label">Wybierz max. odległość od wskazanego adresu</label>
        </div>
      </form>
    </template>
  </BoxSection>
</template>

<script>
// eslint-disable-next-line
import { mapGetters, mapActions } from 'vuex';
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
      addressesVisible: false,
    };
  },
  
  mounted() {
    this.resetForm();
    try {
      this.$store.dispatch('facilitiesSearch/getFacilitiesTypes');
      this.$store.dispatch('facilitiesSearch/getSpecialistsTypes');
      this.$store.dispatch('facilitiesQuery/setQuery', null);
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
    
    ...mapGetters('placeSearch', {
      formState: 'getFormState',
      step: 'getStep',
      placeholder: 'getPlaceholder',
    }),

    form() {
      const {
        type,
        specialization,
        distance,
        isNFZ,
        latitude,
        longitude,
      } = this.formState;

      const form = {
        specialization: specialization?.name,
        type: type?.name,
        distance,
        isNFZ,
      };

      if (latitude && longitude) {
        Object.assign(form, {
          longitude,
          latitude,
        });
      }

      return form;
    },
  },
  watch: {
    // eslint-disable-next-line
    step(step) {
      if (step === SEARCH_STEPS.WELCOME) this.resetForm();
    },
  },
  methods: {
    ...mapActions('placeSearch', ['setStep', 'setFormState', 'resetFormState', 'setPlaceholder']),
    
    resetForm() {
      this.resetFormState();
      this.activeAddress = null;
      this.setPlaceholder('');
      this.$store.commit('facilitiesSearch/setPossibleAddresses', []);
      this.searchKey++;
      this.coords = null;
      this.$emit('getCoords', null);
      this.setStep(SEARCH_STEPS.WELCOME);
    },
    
    submitSearch() {
      this.$emit('onSearch', this.form);
    },

    getCurrentPosition() {
      const onSuccess = ({ coords }) => {
        const { latitude, longitude } = coords;
        this.searchKey++;
        this.setPlaceholder('Twoja lokalizacja');
        this.coords = { 
          latitude,
          longitude,
        };
        this.activeAddress = null;
        this.$store.commit('facilitiesSearch/setPossibleAddresses', []);
        this.$nextTick(() => {
          this.$emit('getCoords', this.coords);
          this.updateCoordsInFormState();
          this.setStep(SEARCH_STEPS.MORE_FILTERS);
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
        this.setPlaceholder('');
        this.setStep(SEARCH_STEPS.RESULTS_VISIBLE);
      } else {
        this.activeAddress = address;
        this.coords = address?.location;
        this.setPlaceholder(this.buildAddress(address));
        this.searchKey++;
        this.setStep(SEARCH_STEPS.MORE_FILTERS);
      }

      this.$emit('getCoords', this.coords);
      this.updateCoordsInFormState();
    },

    isActive(address) {
      return address?.location?.longitude === this.coords?.longitude 
      && address?.location?.latitude === this.coords?.latitude;
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
        this.setStep(SEARCH_STEPS.RESULTS_VISIBLE);
      } else {
        this.setStep(SEARCH_STEPS.WELCOME);
      }
    },

    handlePreviousStep() {
      if (!this.addressesVisible) {
        this.setStep(SEARCH_STEPS.WELCOME);
        return;
      }

      this.setStep(--this.step);
    },

    updateCoordsInFormState() {
      if (this.coords) {
        this.setFormState({ key: 'latitude', value: this.coords.latitude });
        this.setFormState({ key: 'longitude', value: this.coords.longitude });
      }
    },
  },
};
</script>

<style lang="scss">
.PlaceSearch {
  width: 100%;
  
  @media screen and (max-width: $desktop_breakpoint) {
    display: flex;
    align-items: center;  
    justify-content: center;
    flex-direction: column;

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
