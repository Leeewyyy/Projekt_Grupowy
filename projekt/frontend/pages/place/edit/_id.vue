<template>
  <AppPage name="place-add">
    <div id="add-place" class="centered-page">
      <AddPlaceForm id="add-place-form" class="centered-form" />
    </div>
  </AppPage>
</template> 

<script>
import AppPage from '@/components/AppPage';
import AddPlaceForm from '@/components/AddPlaceForm';

export default {
  components: { AppPage, AddPlaceForm },
  computed: {
    userId() {
      return this.$store.getters['user/getUserId'];
    },
  },
  async mounted() {
    if (!this.userId) {
      //eslint-disable-next-line
      confirm('Brak dostępu. Zaloguj się.');
      this.$router.push('/');
    }
  },
};
</script>

<style>
.main-box {
  margin-top: 30px;
}

.centered-page#add-place {
  width: auto !important;
  height: auto !important;
}
</style>
