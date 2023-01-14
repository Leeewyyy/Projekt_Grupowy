<template>
  <BoxSection class="PlaceDetails main-container">
    <template #header>
      <div class="PlaceDetails_header">
        <div class="header_actions">
          <ul class="actions_list">
            <li class="list_item" v-for="(action, idx) in actions" :key="idx">
              <IconToggleButton
                v-if="action.type === 'favorite'"
                :icon-name="isFavorite ? 'favorite' : 'favorite_border'"
                :size="24"
                variant="dark"
                class="favourite"
                @click="addToFavorites"
                style="margin-top: 5px;"
              />
              <button v-else class="item_button" @click.prevent="action.method">
                <Icon class="button_icon" :name="action.icon" :size="24" />
              </button>
            </li>
          </ul>
        </div>
        <div class="header_info">
          <div class="info_title">
            <h2 class="title_text">
              {{ place.name }}
            </h2>
          </div>
          <div class="info_address">
            {{ place.address }}
          </div>
          <div class="info_rating">
            <Rating class="details_rating" v-model="place.rating" />
          </div>
        </div>
        <button class="header_close-button" @click.prevent="onClose">
          <Icon name="keyboard_backspace" :size="24" />
        </button>
        <NFZMark class="header_nfz-mark" :value="place.isNFZ" />
      </div>
    </template>
    <template #body>
      <vue-scroll :ops="scrollOptions">
        <div class="PlaceDetails_container">
          <div class="container_image">
            <ImageSlider
              v-if="place.images && place.images.length"
              :images="place.images"
              class="image_slider"
            />
            <div class="image_empty-list" v-else>
              Dla tej placówki nie zamieszczono żadnych zdjęć.
            </div>
          </div>
          <div class="container_links" v-if="links.length">
            <ul class="links_list">
              <li class="list_item" v-for="(link, idx) in links" :key="idx">
                <div class="item_link">
                  <Icon class="link_icon" :name="link.icon" :size="24" color="#333" />
                  <a
                    :href="link.href"
                    :target="link.icon === 'phone' ? '_self' : '_blank'"
                    rel="noopener noreferer"
                  >
                    <div class="link_text">
                      {{ link.name }}
                    </div>
                  </a>
                </div>
              </li>
            </ul>
          </div>
          <!-- Place description from Markdown -->
          <PlaceDescription id="placeDescription" :text="place.description" />

          <!-- Reviews -->
          <PlaceReviews
            id="placeReviews"
            class="container_reviews"
            title="Opinie użytkowników"
            :placeId="place.id"
            :reviews="place.reviews"
          />
        </div>
      </vue-scroll>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import PlaceDescription from '@/components/place/PlaceDescription';
import PlaceReviews from '@/components/place/PlaceReviews';
import ImageSlider from '@/components/ImageSlider';
import Rating from '@/components/Rating';
import NFZMark from '@/components/NFZMark';
import Icon from '@/components/shared/Icon';
import IconToggleButton from '@/components/shared/IconToggleButton';

export default {
  components: {
    BoxSection,
    PlaceDescription,
    PlaceReviews,
    ImageSlider,
    Rating,
    NFZMark,
    Icon,
    IconToggleButton,
  },

  props: {
    place: {
      type: Object,
      required: true,
    },
  },

  computed: {
    links() {
      const { websiteUrl, phone } = this.place;
      const list = [];

      if (websiteUrl) {
        list.push({
          name: 'Strona',
          icon: 'public',
          href: websiteUrl,
        });
      }

      if (phone) {
        list.push({
          name: phone,
          icon: 'phone',
          href: `tel:${phone}`,
        });
      }

      return list;
    },
  },

  data() {
    return {
      scrollOptions: {
        mode: 'native',
        sizeStrategy: 'percent',
        detectResize: true,
      },

      actions: [
        {
          type: 'favorite',
          name: 'Dodaj do ulubionych',
          icon: 'favorite_border',
          method: this.addToFavorites,
        },
      ],
      isFavorite: false,
    };
  },

  methods: {
    addToFavorites(isFavorite) {
      // some ajax here and getData after
      console.log(isFavorite);
      if (!isFavorite) {
        this.$notify({ text: 'Placówka została usunięta z ulubionych pomyślnie', type: 'success' });
      } else {
        this.$notify({ text: 'Placówka została dodana do ulubionych pomyślnie', type: 'success' });
      }
    },

    onClose() {
      this.$emit('onClose');
    },
  },
};
</script>

<style lang="scss">
.PlaceDetails {
  .PlaceDetails_header {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: flex-start;
    align-items: flex-start;
    position: relative;
    padding: 0.5rem;

    .header_actions {
      .actions_list {
        list-style-type: none;

        .list_item {
          margin-bottom: 0.5rem;

          .item_button {
            background: none;
            padding: 0.5rem;
          }
        }
      }
    }

    .header_info {
      margin-right: 4rem;
      padding: 0.25rem;
      color: #000;

      .info_title {
        .title_text {
          font-size: 1.5rem;
          font-weight: 600;
        }
      }

      .info_address {
        margin-top: 0.25rem;
        font-size: 1rem;
      }

      .info_rating {
        margin-top: 0.5rem;
      }
    }

    .header_nfz-mark {
      position: absolute;
      top: 1.2rem;
      right: 2.85rem;
    }

    .header_close-button {
      width: max-content;
      display: flex;
      align-items: center;
      margin-left: auto;
      padding: 0.5rem;
      background: none;
      cursor: pointer;
    }
  }

  .PlaceDetails_container {
    max-height: 60vh;
    color: #333;

    .container_image {
      .image_empty-list {
        margin: 0.5rem 0 1rem 0;
        color: #888;
        text-align: center;
      }
    }

    .container_links {
      width: 100%;
      padding: 1rem;

      .links_list {
        display: flex;
        flex-direction: row;
        align-items: center;
        list-style-type: none;

        .list_item {
          .item_link {
            display: flex;
            flex-direction: row;
            align-items: center;
            margin-right: 1rem;
            padding: 0.5rem 1rem;
            border-radius: 25px;
            background: #ddd;

            .link_icon {
              margin-right: 0.25rem;
            }
          }
        }
      }
    }

    @media screen and (max-width: $desktop_breakpoint) {
      max-height: unset;
    }
  }
}
</style>
