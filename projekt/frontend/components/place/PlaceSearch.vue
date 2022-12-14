<template>
  <BoxSection class="PlaceSearch main-container">
    <template #header>
      <h2 class="PlaceSearch_title">Wyszukaj placówki w okolicy</h2>
    </template>
    <template #body>
      <form class="PlaceSearch_container main-container" @submit.prevent="submitSearch">
        <div class="PlaceSearch_container_inner">
          <div class="outer-input">
            <TextField
              name="address-input"
              v-model="form.search"
              placeholder="np. Gdańsk, al. Zwycięstwa"
              label="Adres"
              :tab-index="1"
            />
            <IconToggleButton
              @click="iconLocationON = $event"
              :is-active="iconLocationON"
              :with-shadow="true"
              icon-name="location_on"
              tabindex="2"
            />
          </div>

          <div class="outer-input">
            <Select
              name="doctors-select"
              v-model="form.doctor"
              :options="doctorsList"
              label="name"
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
              @click="actionOnButton()"
            >
              Wyczyść
            </Button>

            <Button tabindex="6" name="submit-button" type="submit" variant="dark"> Szukaj </Button>
          </div>
        </div>

        <button
          tabindex="7"
          type="button"
          class="filters-label"
          @click="moreFiltersOn = !moreFiltersOn"
        >
          <span class="label">Pokaż więcej opcji filtrowania</span>
          <Icon :name="`${moreFiltersOn ? 'keyboard_arrow_up' : 'keyboard_arrow_down'}`" />
        </button>

        <div v-if="moreFiltersOn" id="bottomFilters" class="PlaceSearch_container_inner">
          <div class="bottom-filters">
            <Select
              name="places-select"
              v-model="form.placeType"
              :options="placesTypes"
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
import BoxSection from '@/components/BoxSection';
import TextField from '@/components/shared/TextField';
import Select from '@/components/shared/Select';
import Button from '@/components/shared/Button';
import IconToggleButton from '@/components/shared/IconToggleButton';
import Icon from '@/components/shared/Icon';

export default {
  components: {
    BoxSection,
    TextField,
    Select,
    Button,
    IconToggleButton,
    Icon,
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

      coords: {
        longitude: null,
        latitude: null,
      },

      doctorsList: [
        {
          id: 1,
          name: 'Dentysta',
        },
        {
          id: 2,
          name: 'Kardiolog',
        },
        {
          id: 3,
          name: 'Urolog',
        },
      ],

      placesTypes: [
        {
          id: 1,
          name: 'Przychodnia',
        },
        {
          id: 2,
          name: 'Szpital',
        },
        {
          id: 3,
          name: 'Prywatny lokal',
        },
      ],

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
    };
  },

  watch: {
    iconNfzOn(val) {
      alert(`NFZ filtering ${val ? 'on' : 'off'}`);
    },
    iconLocationON(val) {
      if (val) {
        this.getCurrentPosition();
      }
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
        this.coords.latitude = latitude;
        this.coords.longitude = longitude;
        this.iconLocationON = true;
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
  },
};
</script>

<style lang="scss">
.PlaceSearch {
  .PlaceSearch_title {
    height: 55px;
    line-height: 55px;
    text-indent: 2rem;
    font-size: 1.2rem;
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

        .select-outer:first-of-type {
          width: 55%;
        }

        .select-outer:last-of-type {
          width: 40%;
        }
      }
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
  }

  .outer-input {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;

    .text-field-outer,
    .select-outer {
      width: 87%;
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
  }
}
</style>
