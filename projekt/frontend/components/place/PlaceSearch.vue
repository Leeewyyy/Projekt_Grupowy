<template>
  <BoxSection class="PlaceSearch main-container">
    <template #header>
      <div class="place-search-header">
        <h2 class="PlaceSearch_title">
          <span class="title-desktop">Wyszukaj placówki w okolicy</span>
          <span class="title-mobile">Znajdź placówki <br />medyczne w oklicy</span>
        </h2>
        <IconToggleButton
          v-if="welcomeCookie"
          tooltip-text="Zwiń okno wyszukiwania"
          class="icon-hide"
          @click="$emit('hideBox')"
          icon-name="keyboard_double_arrow_left"
        />
      </div>
    </template>
    <template #body>
      <WelcomeBox v-if="!welcomeCookie"/>
      <form v-else class="PlaceSearch_container main-container" @submit.prevent="submitSearch">
        <div class="PlaceSearch_container_inner">
          <div class="outer-input">
            <TextField
              name="address-input"
              v-model="form.search"
              :placeholder="tmpPlaceholder.length ? tmpPlaceholder : 'np. Gdańsk, al. Zwycięstwa'"
              label="Adres [ wymagane ]"
              :tab-index="1"
              @input="getPossibleResults"
            />
            <IconToggleButton
              @click="iconLocationON = $event"
              :is-active="iconLocationON"
              :with-shadow="true"
              icon-name="location_on"
              tabindex="2"
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
            <IconToggleButton
              :is-active="form.isNFZ"
              name="nfz-button"
              :is-custom="true"
              :with-shadow="true"
              icon-name="nfz-icon"
              @click="form.isNFZ = $event"
              tabindex="4"
            />
          </div>

          <div class="buttons">
            <Button
              tabindex="5"
              name="clear-button"
              type="button"
              variant="light"
              :disabled="!coords"
              @click="actionOnButton()"
            >
              Wyczyść
            </Button>

            <Button 
              tabindex="6"
              name="submit-button"
              type="submit"
              variant="dark"
              :disabled="!coords"
            >
              Szukaj 
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
              :options="distanceOptions"
              label="name"
              description="Odległość"
              :tab-index="9"
            />
          </div>
        </div>
      </form>
    </template>
  </BoxSection>
</template>

<script>
// eslint-disable-next-line
import { mapGetters } from 'vuex';
import BoxSection from '@/components/BoxSection';
import TextField from '@/components/shared/TextField';
import Select from '@/components/shared/Select';
import Button from '@/components/shared/Button';
import IconToggleButton from '@/components/shared/IconToggleButton';
import Icon from '@/components/shared/Icon';
import WelcomeBox from '@/components/WelcomeBox';

export default {
  components: {
    BoxSection,
    TextField,
    Select,
    Button,
    IconToggleButton,
    Icon,
    WelcomeBox,
  },

  data() {
    return {
      form: {
        search: null,
        doctor: null,
        placeType: null,
        maxDistance: null,
        isNFZ: null,
      },

      moreFiltersOn: false,
      iconLocationON: false,
      tmpPlaceholder: '',
      scrollOptions: {
        mode: 'native',
        sizeStrategy: 'percent',
        detectResize: true,
      },
      coords: null,
      activeAddress: null,
      distanceOptions: [
        {
          id: 1,
          name: '5 km',
          val: 5,
        },
        {
          id: 2,
          name: '10 km',
          val: 10,
        },
        {
          id: 3,
          name: '50 km',
          val: 50,
        },
      ],
      delayTimer: null,
    };
  },
  mounted() {
    try {
      this.$store.dispatch('facilitiesSearch/getFacilitiesTypes');
      this.$store.dispatch('facilitiesSearch/getSpecialistsTypes');
      this.$store.dispatch('cookie/getCookie', 'showWelcomeBox');
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
    welcomeCookie() {
      return this.$store.getters['cookie/getShowWelcomeBoxCookie'];
    },
  },
  watch: {
    iconLocationON(val) {
      if (val) {
        this.getCurrentPosition();
      } else if (!this.activeAddress) this.$emit('getCoords', null);
      else this.tmpPlaceholder = '';
    },
    activeAddress: {
      deep: true,
      handler() {
        this.iconLocationON = false;
      },
    },
    // eslint-disable-next-line
    'form.search'() {
      this.moreFiltersOn = false;
    },
  },
  methods: {
    actionOnButton() {
      this.form = {};
    },

    submitSearch() {
      this.$emit('onSearch', this.form);
    },

    getCurrentPosition() {
      const onSuccess = ({ coords }) => {
        const { latitude, longitude } = coords;
        this.coords = { 
          latitude,
          longitude,
        };
        this.form.search = '';
        this.tmpPlaceholder = 'Twoja lokalizacja';
        this.iconLocationON = true;
        this.activeAddress = null;
        this.$emit('getCoords', null);
        this.$nextTick(() => {
          this.$emit('getCoords', this.coords);
        });
      };

      const onError = (error) => {
        console.error(error.message);
      };

      navigator.geolocation.getCurrentPosition(onSuccess, onError);
    },

    toggleAddress(address) {
      if (this.isActive(address)) {
        this.coords = null;
        this.activeAddress = null;
        this.form.search = '';
      } else {
        this.activeAddress = address;
        this.coords = address.location;
        this.form.search = this.buildAddress(address);
      }

      this.$emit('getCoords', this.coords);
    },

    async getPossibleResults(inputVal) {
      try {
        clearTimeout(this.delayTimer);
        this.delayTimer = setTimeout(() => {
          this.$store.dispatch('facilitiesSearch/getAddresses', inputVal);
        }, 700);
      } catch (error) {
        this.$notify({ text: 'Błąd pobierania możliwych adresów' });
      }
    },

    isActive(address) {
      return address.location.longitude === this.coords?.longitude 
      && address.location.latitude === this.coords?.latitude;
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

  .buttons {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 30px;

    button[name='submit-button'] {
      width: 60%;
    }

    button[name='clear-button'] {
      width: 35%;
      padding-left: 9px;
      padding-right: 9px;
    }

     @media screen and (max-width: $desktop_breakpoint) {
      button[name='clear-button'] {
        display: none;
      }
      
      button[name='submit-button'] {
        width: 100%;
      }
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

    .icon-button {
      width: 35px;
      height: 35px;
      position: relative;
      top: -2px;
    }

    .icon-button[name='nfz-button'] {
      top: -5px;
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
</style>
