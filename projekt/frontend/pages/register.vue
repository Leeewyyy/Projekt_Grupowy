<template>
  <AppPage name="login">
    <div class="centered-page">
      <RegisterForm id="register-form" class="centered-form" />
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import RegisterForm from '@/components/RegisterForm';

export default {
  components: {
    AppPage,
    RegisterForm,
  },

  head() {
    return {
      title: 'Rejestracja',
    };
  },
};
</script>

<style lang="scss" scoped>
  .centered-form {
    margin: auto;
    position: relative;
    top: 17%;
  }
</style>
