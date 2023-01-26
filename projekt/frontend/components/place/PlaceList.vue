<template>
  <BoxSection
    class="PlaceList main-container"
    :class="{
      'PlaceList--selectable': selectable,
    }"
  >
    <template #header>
      <div class="PlaceList_header">
        <div class="text-with-button d-flex-center">
          <button class="header_close-button" v-if="closable" @click.prevent="onClose">
            <Icon name="keyboard_backspace" />
          </button>
          <h2 class="header_title">
            <span v-if="customTitle">
              {{ customTitle }}
            </span>
            <span v-else>
              Znaleziono <strong class="title_count">{{ places.length }}</strong> wyników
            </span>
          </h2>
        </div>
        <IconToggleButton
          v-if="!customTitle && welcomeCookie"
          tooltip-text="Zwiń okno wyszukiwania"
          class="icon-hide"
          @click="$emit('hideBox')"
          icon-name="keyboard_double_arrow_left"
        />
      </div>
    </template>
    <template #body>
      <div class="PlaceList_container">
        <div v-if="!places.length" class="container_empty">
          Nie znaleziono żadnych miejsc spełniających kryteria.
        </div>
        <vue-scroll v-else :ops="scrollOptions">
          <ul class="container_list">
            <li class="list_item" v-for="place in places" :key="place.id">
              <PlaceCard
                v-bind="place"
                class="item_place-card"
                :full-width="true"
                @click="onPlaceSelected(place)"
                @favoriteChanged="updateList"
              />
            </li>
          </ul>
        </vue-scroll>
      </div>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import PlaceCard from '@/components/place/PlaceCard';
import Icon from '@/components/shared/Icon';
import IconToggleButton from '@/components/shared/IconToggleButton';

export default {
  components: {
    BoxSection,
    PlaceCard,
    Icon,
    IconToggleButton,
  },

  props: {
    customTitle: {
      type: String,
      default: null,
    },
    places: {
      type: Array,
      default: () => [],
    },
    selectable: {
      type: Boolean,
      default: true,
    },
    closable: {
      type: Boolean,
      default: true,
    },
  },

  data() {
    return {
      scrollOptions: {
        mode: 'native',
        sizeStrategy: 'percent',
        detectResize: true,
      },
    };
  },

  computed: {
    welcomeCookie() {
      return this.$store.getters['cookie/getShowWelcomeBoxCookie'];
    },
  },

  methods: {
    onPlaceSelected(place) {
      this.$emit('onPlaceSelected', place);
    },

    onClose() {
      this.$emit('onClose');
    },

    updateList() {
      this.$emit('updateList');
    },
  },
};
</script>

<style lang="scss">
.PlaceList {
  @media screen and (max-width: $desktop_breakpoint) {
    background: #FDFDFD;
    box-shadow: unset;
  }

  &.PlaceList--selectable {
    .item_place-card {
      cursor: pointer;
    }
  }

  .PlaceList_header {
    height: 55px;
    font-size: .8rem;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 0 1rem;

    .header_title {
      text-indent: 1rem;
      font-weight: 600;
    }

    .text-with-button {
      padding-left: 10px;
    }

    .icon-hide {
      padding-right: 15px;
    }

    @media screen and (max-width: $desktop_breakpoint) {
      justify-content: center;

      .icon-hide {
        display: none;
      }
    }

    .header_close-button {
      width: max-content;
      display: flex;
      align-items: center;
      background: none;
      cursor: pointer;

      .material-icons {
        font-size: 1.5rem !important;
      }
    }

    @media screen and (max-width: $desktop_breakpoint) {
      font-size: .9em;
      text-indent: unset;
      height: 140px;
      line-height: .9em;
      display: flex;
      align-items: center;
      justify-content: center;
      text-align: center;
      background: transparent;
      position: relative;

      .header_close-button {
        position: absolute;
        bottom: 10px;
        left: 17px;

        .material-icons {
          font-size: 2rem !important;
        }
      }

      .header_title {
        text-indent: unset;
      }
    }
  }

  .PlaceList_container {
    padding: 2rem;
    background: rgb(var(--color-main));

    .container_list {
      max-height: 60vh;
      list-style-type: none;
      padding-bottom: 2em;

      .list_item {
        margin-bottom: 1rem;

        &:last-child {
          margin-bottom: 0;
        }
      }

      @media screen and (max-width: $desktop_breakpoint) {
        max-height: unset;
      }
    }

    @media screen and (max-width: $desktop_breakpoint) {
      background: transparent;
      padding: 0 1em;
    }
  }
}

@media screen and (max-width: $tablet_breakpoint) {
  .PlaceList {
    .PlaceList_container {
      padding: 1rem;
    } 
  }
}
</style>
