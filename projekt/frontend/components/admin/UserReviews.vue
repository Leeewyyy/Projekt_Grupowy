<template>
  <BoxSection class="UserReviews">
    <template #header>
      Opinie użytkownika ({{ reviews.length }})
    </template>
    <template #body>
      <div class="User_box-content">
        <div v-if="!reviews.length">
          Ten użytkownik nie wystawił jeszcze żadnej opinii.
        </div>
        <vue-scroll :ops="scrollOptions">
          <ul class="UserReviews_reviews-list">
            <li
              class="UserReviews_review"
              v-for="review in reviews"
              :key="review.id"
            >
              <Avatar
                class="UserReviews_review-avatar"
                placeholderIcon="place"
                :imageUrl="review.medicalFacility.imageUrl"
              />
              <div class="UserReviews_review-details">
                <div class="UserReviews_review-name">
                  {{ review.medicalFacility.name }}
                  <Icon
                    class="UserReviews_delete"
                    v-tooltip="'Usuń opinię'"
                    name="delete"
                    color="#f00"
                    @click.native="deleteReview(review.id)"
                  />
                </div>
                <div class="UserReviews_review-rating">
                  <Rating v-model="review.rating" :showTooltip="false" />
                </div>
                <div class="UserReviews_review-description">
                  {{ review.description }}
                </div>
              </div>
            </li>
          </ul>
        </vue-scroll>
      </div>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import Avatar from '@/components/shared/Avatar';
import Rating from '@/components/Rating';
import Icon from '@/components/shared/Icon';

export default {
  props: {
    userId: {
      type: Number,
      required: true,
    },
  },

  components: {
    BoxSection,
    Avatar,
    Rating,
    Icon,
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
      this.reviews = await this.$store.dispatch('user/fetchOpinions', this.userId);
    } catch (error) {
      this.$notify({ text: 'Wystąpił błąd pobierania danych. Spróbuj odświeżyć stronę.', type: 'error' });
      console.error(error);
    }
  },

  methods: {
    async deleteReview(id) {
      const confirm = window.confirm(`Potwierdź usunięcie opinii...`); // eslint-disable-line
      if (!confirm) return;

      try {
        const reviewIdx = this.reviews.findIndex((review) => review.id === id);
        if (reviewIdx > -1) this.reviews.splice(reviewIdx, 1);
        this.$notify({ text: 'Opinia została usunięta pomyślnie.', type: 'success' });
      } catch (error) {
        this.$notify({ text: 'Wystąpił błąd podczas usuwania opinii.', type: 'error' });
        console.error(error);
      }
    },
  },
};
</script>

<style lang="scss">
.UserReviews {
  .UserReviews_list {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    list-style-type: none;
  }

  .UserReviews_review {
    display: flex;
    flex-direction: row;
    align-items: flex-start;

    .UserReviews_review-avatar {
      margin-right: 1rem;
      flex-shrink: 0;
    }
    
    .UserReviews_review-details {
      color: #000;
    }

    .UserReviews_review-name {
      display: flex;
      flex-direction: row;
      align-items: center;
      gap: 0.25rem;
      font-weight: 600;
    }
  }

  .UserReviews_delete {
    cursor: pointer;
  }
}
</style>
