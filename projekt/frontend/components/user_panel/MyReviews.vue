<template>
  <BoxSection class="MyReviews inner-padding">
    <template #header>
      Twoje opinie <span class="reviews-count">({{ reviews.length }})</span>
    </template>
    <template #body>
      <div v-if="!reviews.length" class="no-opinions">Nie wystawiłeś jeszcze żadnej opinii.</div>
      <vue-scroll :ops="scrollOptions">
        <ul class="MyReviews_list">
          <li
            class="list_item"
            v-for="review in reviews"
            :key="review.id"
          >
            <Avatar
              class="item_avatar"
              placeholderIcon="place"
              :imageUrl="review.medicalFacility.imageUrl"
            />
            <div class="item_details">
              <div class="details_name">
                {{ review.medicalFacility.name }}
              </div>
              <div class="details_rating">
                <Rating v-model="review.rating" :showTooltip="false" />
              </div>
              <div class="details_description">
                {{ review.description }}
              </div>
            </div>
          </li>
        </ul>
      </vue-scroll>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import Avatar from '@/components/shared/Avatar';
import Rating from '@/components/Rating';

export default {
  components: {
    BoxSection,
    Avatar,
    Rating,
  },

  props: {
    user: {
      type: Object,
      required: true,
    },
  },

  data() {
    return {
      reviews: [],

      scrollOptions: {
        mode: 'native',
        scrollPanel: {
          maxHeight: 300,
        },
      },
    };
  },

  async fetch() {
    try {
      this.reviews = await this.$store.dispatch('user/fetchOpinions', this.user.id);
    } catch (error) {
      this.$notify({ text: 'Nie udało się pobrać listy Twoich opinii.', type: 'error' });
      console.error(error);
    }
  },
};
</script>

<style lang="scss" scoped>
.reviews-count {
  font-size: 0.8em;
  font-weight: 300;
}

.inner-padding {
  box-sizing: border-box;
  padding: 20px 30px;
}

.section-element {
  width: 50%;

  .file-place {
    flex-direction: column;
    width: 65%;

    span {
      font-weight: 600;
    }
  }
}

.margin-top {
  margin-top: 10px;
}

.MyReviews {
  .MyReviews_list {
    margin-top: 1rem;
    list-style-type: none;

    .list_item {
      display: flex;
      flex-direction: row;
      align-items: flex-start;
      margin-bottom: 1rem;

      .item_avatar {
        margin-right: 1rem;
        flex-shrink: 0;
      }

      .item_details {
        color: #000;

        .details_name {
          font-weight: 600;
        }
      }
    }
  }
}

.no-opinions {
  margin-top: 10px;
}
</style>
