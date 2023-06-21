<template>
  <div class="PlaceReviewAdd">
    <div class="PlaceReviewAdd_title">
      Dodaj opinię
    </div>
    <form action="" class="PlaceReviewAdd_form" @submit.prevent="addReview">
      <div class="form_group">
        <div class="group_name">
          Wybierz ocenę:
        </div>
        <Rating
          id="selectRating"
          v-model="form.rating"
          :large="true"
          :preview="false"
        />
      </div>
      <InputText
        class="form_textarea"
        name="reviewText"
        :isTextarea="true"
        v-model="form.text"
        placeholder="Napisz jak Ci się podobało..."
      />
      <Button class="form_submit" type="submit" variant="dark">
        Umieść opinię
      </Button>
    </form>
  </div>
</template>

<script>
import Rating from '@/components/Rating';
import InputText from '@/components/shared/InputText';
import Button from '@/components/shared/Button';

export default {
  props: {
    placeId: {
      type: Number,
      required: true,
    },
  },

  components: {
    Rating,
    InputText,
    Button,
  },

  data() {
    return {
      form: {},

      defaultForm: {
        text: null,
        rating: 5,
      },
    };
  },

  methods: {
    async addReview() {
      const userId = this.$store.getters['user/getUserId'];

      if (!userId) {
        this.$notify({ text: 'Nie jesteś zalogowany!', type: 'error' });
        return;
      }

      // Form not filled
      if (!this.form?.text || !this.form?.rating) return;

      const payload = {
        facilityId: this.placeId,
        userId,
        ...this.form,
      };

      try {
        const review = await this.$store.dispatch('opinion/addOpinion', payload);
        this.resetForm();
        this.$emit('reviewAdded', review);
        this.$notify({ text: 'Dodano opinię!', type: 'success' });
      } catch (error) {
        this.$notify({ text: 'Wystąpił błąd przy dodawaniu opinii!', type: 'error' });
        console.error(error);
      }
    },

    resetForm() {
      this.form = { ...this.defaultForm };
    },
  },

  created() {
    this.resetForm();
  },
};
</script>

<style lang="scss">
.PlaceReviewAdd {
  .PlaceReviewAdd_title {
    font-size: 1.2rem;
  }

  .PlaceReviewAdd_form {
    margin-top: 0.25rem;

    .form_group {
      display: flex;
      flex-direction: row;
      align-items: center;

      .group_name {
        margin-right: 0.5rem;
        font-size: 1rem;
        color: #444;
      }
    }

    .form_textarea {
      margin: 0.5rem 0 1rem 0;
      box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.2);
      border-radius: 8px;
    }

    .form_submit {
      float: right;
      padding: 0.5rem 1rem;
      border-radius: 25px;
    }
  }
}
</style>
