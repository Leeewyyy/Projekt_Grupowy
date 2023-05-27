<template>
  <div
    class="PlaceCard"
    @click="$emit('click')"
  >
    <div class="PlaceCard_photo">
      <PlacePhoto class="photo_image" :src="imageUrl" />
    </div>
    <div class="PlaceCard_wrapper">
      <div class="PlaceCard_details">
        <div class="details_name">
          {{ name }}
        </div>
        <div class="details_address">
          {{ address }}
        </div>
        <div class="details_rating">
          <Rating
            class="details_rating"
            v-model="rating"
            :ratingCount="ratingCount || 0"
          />
        </div>
        <div class="details_building-type">
          <Icon
            class="building-type_icon"
            name="home"
            :size="18"
          /> {{ type }}
        </div>
        <div
          class="details_specialists"
          v-if="specialists.length"
        >
          <ul class="specialists_list">
            <li
              class="list_item"
              v-for="specialist in specialists"
              :key="specialist"
            >
              {{ specialist }}
            </li>
          </ul>
        </div>
      </div>
      <div class="PlaceCard_sidebar">
        <FavouriteButton
          v-if="isLogged"
          class="sidebar_favourite-button"
          :placeId="id"
        />
        <NFZStatus
          class="sidebar_nfz-status"
          :status="nfzStatus"
        />
      </div>
    </div>
  </div>
</template>

<script>
import PlacePhoto from '@/components/place/PlacePhoto';
import Rating from '@/components/Rating';
import Icon from '@/components/shared/Icon';
import NFZStatus from '@/components/NFZStatus';
import FavouriteButton from '@/components/place/FavouriteButton';

export default {
  components: {
    PlacePhoto,
    Rating,
    Icon,
    NFZStatus,
    FavouriteButton,
  },

  inheritAttrs: false,

  props: {
    id: {
      type: Number,
    },
    name: {
      type: String,
    },
    address: {
      type: String,
    },
    type: {
      type: String,
    },
    imageUrl: {
      type: String,
    },
    nfzStatus: {
      type: String,
      default: 'none',
      validator: (value) => ['full', 'partial', 'none'].includes(value),
    },
    rating: {
      type: Number,
      default: 0,
    },
    ratingCount: {
      type: Number,
      default: 0,
    },
    specialists: {
      type: Array,
      // TODO: Remove once backend is updated
      default: () => ['Kardiolog', 'Stomatolog', 'Endokrynolog'],
    },
    isFavorite: {
      type: Boolean,
      default: false,
    },
  },

  computed: {
    isLogged() {
      return this.$store.getters['user/isLoggedIn'];
    },
  },
};
</script>

<style lang="scss">
.PlaceCard {
  width: 100%;
  max-width: 450px;
  padding: 1rem;

  display: flex;
  flex-direction: row;
  align-items: flex-start;
  flex-wrap: nowrap;

  overflow: hidden;
  color: $black;
  background: $white;
  border-radius: 8px;
  box-shadow: 0px 2px 4px rgba($black, 0.125);

  .PlaceCard_wrapper {
    width: 100%;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
  }

  .PlaceCard_photo {
    width: 120px;
    height: 120px;
    overflow: hidden;
    border-radius: 0.5rem;
    flex-shrink: 0;
  }

  .PlaceCard_details {
    width: 100%;
    padding: 0 1rem;

    .details_name {
      display: -webkit-box;
      overflow: hidden;
      text-overflow: ellipsis;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      line-clamp: 2;

      font-size: 1rem;
      font-weight: 600;
    }

    .details_address {
      margin-top: 0.25rem;
      font-size: 0.9rem;
    }

    .details_rating {
      margin-top: 0.25rem;
    }

    .details_building-type {
      display: flex;
      flex-direction: row;
      align-items: center;
      margin-top: 0.25rem;
      font-size: 0.9rem;
      text-transform: lowercase;

      .building-type_icon {
        margin-right: 0.25rem;
      }
    }

    .details_specialists {
      margin-top: 0.25rem;

      .specialists_list {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        list-style-type: none;

        .list_item {
          position: relative;
          margin: 0;
          padding: 0 0.5rem;
          font-size: 0.75rem;
          color: $dark-grey;

          &::before {
            content: '';
            position: absolute;
            display: block;
            top: calc(50% - 2px);
            left: 0;
            width: 4px;
            height: 4px;
            background: $black;
            border-radius: 50%;
          }
        }
      }
    }
  }

  .PlaceCard_sidebar {
    display: flex;
    flex-direction: column;
    align-items: center;

    .sidebar_favourite-button {
      margin-bottom: 5px;
    }
  }
}

@media screen and (max-width: $tablet_breakpoint) {
  .PlaceCard {
    .PlaceCard_photo {
      width: 60px;
      height: 60px;
    }
  }
}
</style>
