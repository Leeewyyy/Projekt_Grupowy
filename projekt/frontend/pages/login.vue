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
