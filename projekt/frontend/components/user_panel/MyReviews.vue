<template>
  <BoxSection class="MyReviews inner-padding">
    <template #header>
      Twoje opinie <span class="reviews-count">({{ reviews.length }})</span>
    </template>
    <template #body>
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
              :imageUrl="review.facility.imageUrl"
            />
            <div class="item_details">
              <div class="details_name">
                {{ review.facility.name }}
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
import MyReviewsTestList from '~/assets/my-reviews.js';

export default {
  components: {
    BoxSection,
    Avatar,
    Rating,
  },

  data() {
    return {
      reviews: MyReviewsTestList,

      scrollOptions: {
        mode: 'native',
        scrollPanel: {
          maxHeight: 300,
        },
      },
    };
  },

  methods: {
    uploadImage({ target: { files } }) {
      const [file] = files;

      if (!['image/png', 'image/png', 'image/jpeg'].includes(file.type)) {
        this.$notify({ text: 'Dodaj plik o poprawnym typie: png, jpg lub jpeg', type: 'error' });
      } else {
        this.file = file;
        this.$notify({ text: `Plik o nazwie: ${files[0].name} został dodany`, type: 'success' });
      }
    },
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
</style>
