<template>
  <BoxSection
    class="PlaceDetails main-container"
    :showBottomButtons="true"
    @onBack="onBack"
  >
    <template #header>
      <div class="PlaceDetails_header mobile-hidden">
        <Branding id="placeDetailsBranding" />
        <CollapseButton @collapse="$emit('hideBox')" />
      </div>
    </template>
    <template #body>
      <vue-scroll :ops="scrollOptions">
        <div class="PlaceDetails_container" v-if="place">
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
          <div class="container_details">
            <div class="details_wrapper">
              <div class="details_name">
                {{ place.name }}
              </div>
              <div class="details_address">
                {{ place.address }}
              </div>
              <div class="details_group">
                <div class="details_rating">
                  <Rating
                    class="details_rating"
                    v-model="place.rating"
                    :ratingCount="place.ratingCount || 0"
                  />
                </div>
                <div class="details_building-type">
                  <Icon
                    class="building-type_icon"
                    name="home"
                    :size="18"
                  /> {{ place.type }}
                </div>
              </div>
            </div>
            <div class="details_sidebar">
              <FavouriteButton
                v-if="isLogged"
                class="sidebar_favourite-button"
                :placeId="placeId"
              />
              <NFZStatus
                class="sidebar_nfz-status"
                :status="place.nfzStatus || 'none'"
              />
            </div>
          </div>
          <div class="container_links" v-if="links.length">
            <ul class="links_list">
              <li class="list_item" v-for="(link, idx) in links" :key="idx">
                <div class="item_link">
                  <a
                    :href="link.href"
                    :target="link.icon === 'phone' ? '_self' : '_blank'"
                    rel="noopener noreferer"
                  >
                    <Icon
                      class="link_icon"
                      :name="link.icon"
                      :size="36" color="#333"
                    />
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
            :reviews="place.opinions"
          />
        </div>
        <div v-else-if="isLoading" class="PlaceDetails_message">
          Trwa wczytywanie szczegółów placówki...
        </div>
        <div v-else class="PlaceDetails_message">
          Nie znaleziono podanej placówki.
        </div>
      </vue-scroll>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import FavouriteButton from '@/components/place/FavouriteButton';
import PlaceDescription from '@/components/place/PlaceDescription';
import PlaceReviews from '@/components/place/PlaceReviews';
import ImageSlider from '@/components/ImageSlider';
import Rating from '@/components/Rating';
import NFZStatus from '@/components/NFZStatus';
import Icon from '@/components/shared/Icon';
import Branding from '@/components/Branding';
import CollapseButton from '@/components/CollapseButton';

export default {
  components: {
    BoxSection,
    FavouriteButton,
    PlaceDescription,
    PlaceReviews,
    ImageSlider,
    Rating,
    NFZStatus,
    Icon,
    Branding,
    CollapseButton,
  },

  props: {
    placeId: {
      type: Number,
      required: true,
    },
  },

  computed: {
    links() {
      const { websiteUrl, phone } = this.place || {};
      const list = [];
      
      if (websiteUrl) {
        list.push({
          name: 'Odwiedź witrynę',
          icon: 'public',
          href: websiteUrl,
        });
      }

      if (phone) {
        list.push({
          name: `Zadzwoń ${phone}`,
          icon: 'phone',
          href: `tel:${phone}`,
        });
      }

      return list;
    },
    
    isLogged() {
      return this.$store.getters['user/isLoggedIn'];
    },
  },

  data() {
    return {
      scrollOptions: {
        mode: 'native',
        sizeStrategy: 'percent',
        detectResize: true,
      },
      
      place: null,
      isLoading: true,
    };
  },

  methods: {
    async loadPlaceDetails() {
      this.isLoading = true;

      try {
        this.place = await this.$store.dispatch('facility/fetchDetails', this.placeId);
      } catch (error) {
        if (process.client) this.$notify({ text: 'Wystąpił problem przy pobieraniu szczegółów placówki.', type: 'error' });
        console.error(error);
      }

      this.isLoading = false;
    },

    onBack() {
      this.$emit('onClose');
    },
  },

  async mounted() {
    await this.loadPlaceDetails();
  },
};
</script>

<style lang="scss">
.PlaceDetails {
  .PlaceDetails_message {
    padding: 2rem 0;
    text-align: center;
  }

  .PlaceDetails_header {
    display: flex;
    flex-direction: row;
    justify-content: center;
    padding: 1rem 0;
  }

  .PlaceDetails_container {
    max-height: 50vh;
    color: #333;

    .container_image {
      .image_empty-list {
        margin: 0.5rem 0 1rem 0;
        color: #888;
        text-align: center;
      }
    }
    
    .container_details {
      display: flex;
      flex-direction: row;
      flex-wrap: nowrap;
      justify-content: space-between;
      align-items: flex-start;
      padding: 1rem;

      .details_name {
        display: -webkit-box;
        overflow: hidden;
        text-overflow: ellipsis;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 4;
        line-clamp: 4;

        font-size: 1.4rem;
        font-weight: 600;
      }

      .details_address {
        margin-top: 0.25rem;
      }

      .details_group {
        display: flex;
        flex-direction: row;
        align-items: flex-end;
        margin-top: 0.5rem;
      }

      .details_building-type {
        display: flex;
        flex-direction: row;
        align-items: center;
        margin: 0.5rem 0 0 0.5rem;
        text-transform: lowercase;

        .building-type_icon {
          margin-right: 0.25rem;
        }
      }

      .details_sidebar {
        display: flex;
        flex-direction: column;
        align-items: center;

        .sidebar_favourite-button {
          margin-bottom: 5px;
        }
      }
    }

    .container_links {
      width: 100%;
      padding: 1rem;

      .links_list {
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        list-style-type: none;

        .list_item {
          margin-right: 2rem;

          &:last-child {
            margin-right: 0;
          }

          .item_link {
            width: 100px;
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;

            .link_icon {
              margin-bottom: 0.5rem;
            }
          }
        }
      }
    }

    .container_reviews {
      padding-bottom: 1rem;
    }

    @media screen and (max-width: $desktop_breakpoint) {
      max-height: unset;
      padding-bottom: 0;
    }
  }
}
</style>
