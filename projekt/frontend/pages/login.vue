<template>
  <AppPage name="login">
    <div class="centered-page">
      <LoginForm id="login-form" class="centered-form" />
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import LoginForm from '@/components/LoginForm';

export default {
  components: {
    AppPage,
    LoginForm,
  },
};
</script>

<style lang="scss" scoped>
.centered-page {
  width: 100vw;
  height: 100vh !important;
  background: rgb(162, 190, 223);
  background: radial-gradient(circle, rgba(162, 190, 223, 1) 44%, rgba(255, 255, 255, 1) 86%);

  .centered-form {
    margin: auto;
    position: relative;
    top: 20%;
  }
}
</style>
