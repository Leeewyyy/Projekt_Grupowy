<template>
  <div class="PlaceReviews">
    <div class="PlaceReviews_title" v-if="title">
      <h2 class="title_text">{{ title }}</h2>
    </div>

    <PlaceReviewAdd
      v-if="isCanAddReview"
      :placeId="placeId"
      class="PlaceReviews_add-review"
    />
    
    <ul class="PlaceReviews_list">
      <li
        class="list_item"
        v-for="review in reviews"
        :key="review.id"
      >
        <Avatar
          class="item_avatar"
          placeholderIcon="person"
          :imageUrl="review.addedBy.avatarUrl"
        />
        <div class="item_details">
          <div class="details_name">
            {{ review.addedBy.firstname }} {{ review.addedBy.lastname }}
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
    <div class="PlaceReviews_load-more" v-if="isShowMore">
      <button class="load-more_button">
        Załaduj więcej opinii...
      </button>
    </div>
  </div>
</template>

<script>
import Rating from '@/components/Rating';
import Avatar from '@/components/shared/Avatar';
import PlaceReviewAdd from '@/components/place/PlaceReviewAdd';

export default {
  props: {
    title: {
      type: String,
      default: null,
    },
    placeId: {
      type: Number,
      required: true,
    },
    reviews: {
      type: Array,
      default: () => [],
    },
  },

  components: {
    Rating,
    Avatar,
    PlaceReviewAdd,
  },

  computed: {
    isShowMore() {
      return true;
    },
  
    isCanAddReview() {
      // return is user logged in
      return true;
    },
  },
};
</script>

<style lang="scss">
.PlaceReviews {
  margin: 1rem;

  .PlaceReviews_add-review {
    padding: 0 1rem 1rem 1rem;
  }

  .PlaceReviews_title {
    margin-bottom: 1rem;

    .title_text {
      font-size: 1.3rem;
      color: #000;
      line-height: 1.7rem;
    }
  }

  .PlaceReviews_list {
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

  .PlaceReviews_load-more {
    margin: 0.5rem 0;
    text-align: center;

    .load-more_button {
      background: none;
      font-size: 1rem;
      font-weight: 500;
      color: #666;
      cursor: pointer;
    }
  }
}
</style>
