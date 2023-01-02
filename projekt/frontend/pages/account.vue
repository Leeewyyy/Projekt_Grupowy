<template>
  <AppPage name="account">
    Tu będą ustawienia konta
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';

export default {
  components: {
    AppPage,
  },
  
  layout: 'page',

  head() {
    return {
      title: 'Moje konto',
    };
  },
};
</script>

<style lang="scss">
.AppPage--account {

}
</style>
