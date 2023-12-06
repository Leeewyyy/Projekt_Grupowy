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
