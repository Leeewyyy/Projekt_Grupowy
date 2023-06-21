<template>
  <AppPage name="contact">
    <div class="centered-page">
      <InfoBoxContact id="info-box-contact" class="centered-form" />
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import InfoBoxContact from '@/components/InfoBoxContact';

export default {
  components: {
    AppPage,
    InfoBoxContact,
  },

  head() {
    return {
      title: 'Kontakt',
    };
  },
};
</script>

<style lang="scss" scoped>
.mobile {
  display: none;
}

@media screen and (max-width: $desktop_breakpoint) {
  .centered-page {
    display: none;
  }

  .mobile {
    display: block;
  }
}
</style>
