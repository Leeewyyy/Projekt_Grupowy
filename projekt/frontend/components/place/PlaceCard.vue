<template>
  <div :class="['PlaceCard', { 'w-100': fullWidth }]" @click="$emit('click')">
    <div class="PlaceCard_wrapper">
      <div class="PlaceCard_photo">
        <PlacePhoto class="photo_image" :src="imageUrl" />
      </div>
      <div class="PlaceCard_details">
        <div class="details_top">
          <div class="details_name">
            <div class="name_text">
              {{ name }}
            </div>
            <div class="side_buttons">
              <NFZMark :value="isNFZ" />
              <IconToggleButton
                :icon-name="isFavoriteActive ? 'favorite' : 'favorite_border'"
                :size="25"
                variant="dark"
                class="favourite"
                @click="toggleFavoriteStatus"
              />
            </div>
          </div>
          <div class="details_address">
            {{ address }}
          </div>
        </div>
        <div class="details_wrapper">
          <Rating class="details_rating" v-model="rating" />
          <div class="details_building-type">
            {{ buildingType }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PlacePhoto from '@/components/place/PlacePhoto';
import Rating from '@/components/Rating';
import NFZMark from '@/components/NFZMark';
import IconToggleButton from '@/components/shared/IconToggleButton';

export default {
  components: {
    PlacePhoto,
    Rating,
    NFZMark,
    IconToggleButton,
  },

  inheritAttrs: false,

  props: {
    id: Number,
    name: String,
    address: String,
    imageUrl: String,
    isNFZ: Boolean,
    rating: Number,
    buildingType: String,
    fullWidth: Boolean,
    isFavorite: Boolean,
  },
  computed: {
    isFavoriteActive: {
      get() {
        return this.isFavorite ?? false;
      },
      set(isFavorite) {
        this.toggleFavourite(isFavorite);
      },
    },
  },
  methods: {
    toggleFavourite(isFavorite) {
      // here some ajax requests
      if (!isFavorite) {
        this.$notify({ text: 'Placówka została usunięta z ulubionych pomyślnie', type: 'success' });
      } else {
        this.$notify({ text: 'Placówka została dodana do ulubionych pomyślnie', type: 'success' });
      }

      this.$emit('favoriteChanged');
    },
    toggleFavoriteStatus(ev) {
      console.log(ev);
      this.isFavoriteActive = !this.isFavoriteActive;
    },
  },
};
</script>

<style lang="scss">
.PlaceCard {
  height: 105px;
  width: 450px;

  overflow: hidden;
  color: rgb(var(--color-black));
  background: rgb(var(--color-white));
  border-radius: 8px;
  box-shadow: 0px 2px 4px rgba(var(--color-black), 0.125);

  .PlaceCard_wrapper {
    height: 100%;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
  }

  .PlaceCard_photo {
    max-width: 150px;
    width: 100%;
    height: auto;
  }

  .PlaceCard_details {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 0.5rem 0.5rem 0.5rem 1rem;

    .details_name {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: flex-start;

      .side_buttons {
        display: flex;
        flex-direction: column;
        align-items: flex-end;

        .favourite {
          position: relative;
          top: 5px;
        }
      }

      .name_text {
        display: -webkit-box;
        overflow: hidden;
        text-overflow: ellipsis;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        line-clamp: 2;

        font-weight: 600;
      }
    }

    .details_address {
      margin-top: 0.25rem;
      font-size: 0.8rem;
    }

    .details_wrapper {
      display: flex;
      flex-direction: row;
      align-items: center;
    }

    .details_rating {
      margin-right: 0.5rem;
    }

    .details_building-type {
      font-size: 0.8rem;
    }
  }
}
</style>
