<template>
  <BoxSection class="login-form-outer main-container">
    <template #header>
      <h2 class="login-form-title">Wpisz dane logowania</h2>
    </template>
    <template #body>
      <form class="login-form" @submit.prevent="submitSearch()">
        <div class="login-form-container-inner">
          <TextField
            id="login"
            name="login-input"
            v-model="inputLogin"
            label="Adres e-mail"
            :is-flat="true"
          />

          <TextField
            id="password"
            name="password-input"
            v-model="inputPassword"
            label="Hasło"
            :is-flat="true"
            type="password"
          />

          <div class="buttons">
            <Button name="submit-button" type="submit" variant="dark"> Zaloguj </Button>
          </div>
        </div>
        <NuxtLink to="register" class="register-link"> Nie masz konta? Zarejestruj się! </NuxtLink>
      </form>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import TextField from '@/components/shared/TextField';
import Select from '@/components/shared/Select';
import Button from '@/components/shared/Button';
import IconToggleButton from '@/components/shared/IconToggleButton';
import Icon from '@/components/shared/Icon';

export default {
  components: {
    BoxSection,
    TextField,
    Select,
    Button,
    IconToggleButton,
    Icon,
  },

  data() {
    return {
      inputLogin: '',
      inputPassword: '',
    };
  },
  methods: {
    submitSearch() {
      if (this.validate()) {
        this.$notify({ text: 'Zalogowano pomyślnie', type: 'success' });
        this.$router.push({ name: 'user-panel' });
      } else {
        this.$notify({ text: 'Błędne dane logowania. Spóbuj ponownie.', type: 'error' });
      }
    },
    validate() {
      if (this.inputLogin.length && this.inputPassword.length) {
        return true;
      }

      return false;
    },
  },
};
</script>

<style lang="scss" scoped>
.login-form-outer {
  .login-form-title {
    height: 55px;
    line-height: 55px;
    text-indent: 2rem;
    font-size: 1.2rem;
  }

  .login-form {
    // Temporary only for placeholder

    padding: 2rem;
    padding-bottom: 1rem;
    background: #fff;

    .login-form-container-inner {
      box-sizing: border-box;
      padding: 20px 20px 15px 20px;
      padding-top: 0;
      border-radius: 10px;
    }

    .text-field-outer#password {
      margin-top: 20px;
    }
  }

  .register-link {
    margin-left: 20px;
  }

  .buttons {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 30px;

    button[name='submit-button'] {
      width: 100%;
    }
  }
}
</style>
