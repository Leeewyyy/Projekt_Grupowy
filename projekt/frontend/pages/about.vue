<template>
  <AppPage name="about">
    <div class="centered-page">
      <InfoBoxAbout id="info-box-about" class="centered-form" />
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import InfoBoxAbout from '@/components/InfoBoxAbout';

export default {
  components: {
    AppPage,
    InfoBoxAbout,
  },
};
</script>
