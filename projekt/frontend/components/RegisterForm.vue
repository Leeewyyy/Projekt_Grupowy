<template>
  <BoxSection class="register-form-outer main-container">
    <template #header>
      <h2 class="register-form-title">Wpisz swoje dane</h2>
    </template>
    <template #body>
      <form class="register-form" @submit.prevent="submitSearch()">
        <div class="register-form-container-inner">
          <TextField
            id="full-name"
            name="fullname-input"
            v-model="inputFullName"
            label="Imię i nazwisko"
            :is-flat="true"
          />

          <TextField
            id="email"
            name="email-input"
            v-model="inputEmail"
            label="Adres e-mail"
            :is-flat="true"
            placeholder="np. jan@kowalski.pl"
          />

          <TextField
            id="password"
            name="password-input"
            v-model="inputPassword"
            label="Hasło"
            :is-flat="true"
            type="password"
          />

          <TextField
            id="password-repeat"
            name="password-input-repeat"
            v-model="inputPasswordRepeated"
            label="Powtórz hasło"
            :is-flat="true"
            type="password"
          />

          <div class="buttons">
            <Button name="submit-button" type="submit" variant="dark"> Zarejestruj </Button>
          </div>
        </div>
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
      inputEmail: '',
      inputPassword: '',
      inputPasswordRepeated: '',
      inputFullName: '',
    };
  },
  methods: {
    submitSearch() {
      if (this.validate()) {
        this.$notify({
          text: 'Zostałeś zarejestrowany pomyślnie! Teraz możesz się zalogować.',
          type: 'success',
        });
        this.$router.push({ name: 'login' });
      }
    },
    validate() {
      if (!/[A-Za-z0-9]*@[A-Za-z0-9]*\.[A-Za-z]*/.test(this.inputEmail)) {
        this.$notify({ text: 'Podaj prawidłowy adres e-mail', type: 'error' });
        return false;
      }

      if (!this.inputPassword.length) {
        this.$notify({ text: 'Hasło nie może być puste', type: 'error' });
        return false;
      }

      if (this.inputPassword.length < 8) {
        this.$notify({ text: 'Hasło powinno zawierać minimum 8 znaków', type: 'error' });
        return false;
      }

      if (this.inputPassword !== this.inputPasswordRepeated) {
        this.$notify({ text: 'Hasła muszą być takie same', type: 'error' });
        return false;
      }

      if (!this.inputFullName.length) {
        this.$notify({ text: 'Podaj imię i nazwisko', type: 'error' });
        return false;
      }

      return true;
    },
  },
};
</script>

<style lang="scss" scoped>
.register-form-outer {
  &.main-container {
    max-height: unset !important;
  }
  .register-form-title {
    height: 55px;
    line-height: 55px;
    text-indent: 2rem;
    font-size: 1.2rem;
  }

  .register-form {
    // Temporary only for placeholder

    padding: 0 2rem 2rem 2rem;
    padding-bottom: 1rem;
    background: #fff;

    .register-form-container-inner {
      box-sizing: border-box;
      padding: 20px 20px 15px 20px;
      padding-top: 0;
      border-radius: 10px;
    }

    .text-field-outer {
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
