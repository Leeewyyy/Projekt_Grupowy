<template>
  <AppPage name="about">
    <div class="centered-page">
      <InfoBoxAbout id="info-box-about" class="centered-form" />
    </div>
    
    <InfoBoxAbout id="info-box-about" class="centered-form mobile" />
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
