<template>
  <BoxSection
    class="PlaceList main-container"
    :class="{
      'PlaceList--selectable': selectable,
    }"
  >
    <template #header>
      <div class="PlaceList_header">
        <h2 class="header_title">
          <span v-if="customTitle">
            {{ customTitle }}
          </span>
          <span v-else>
            Znaleziono <strong class="title_count">{{ places.length }}</strong> wyników
          </span>
        </h2>
        <button class="header_close-button" v-if="closable" @click.prevent="onClose">
          <Icon name="close" />
        </button>
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

export default {
  components: {
    BoxSection,
    PlaceCard,
    Icon,
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
  &.PlaceList--selectable {
    .item_place-card {
      cursor: pointer;
    }
  }

  .PlaceList_header {
    height: 55px;

    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    padding: 0 1rem;

    .header_title {
      height: 55px;
      line-height: 55px;
      text-indent: 1rem;
      font-size: 1.2rem;
      font-weight: 600;
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
  }

  .PlaceList_container {
    padding: 2rem;
    background: rgb(var(--color-main));

    .container_list {
      max-height: 60vh;
      list-style-type: none;

      .list_item {
        padding-right: 1rem; // scroll space
        margin-bottom: 1rem;

        &:last-child {
          margin-bottom: 0;
        }
      }
    }
  }
}
</style>
