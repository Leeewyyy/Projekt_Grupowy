<template>
  <BoxSection class="register-form-outer main-container">
    <template #header>
      <h2 class="register-form-title">Wpisz swoje dane</h2>
    </template>
    <template #body>
      <form class="register-form" @submit.prevent="registerAccount">
        <div class="register-form-container-inner">
          <TextField
            id="full-name"
            name="fullname-input"
            v-model="form.fullName"
            label="Imię i nazwisko"
            :is-flat="true"
            required
          />

          <TextField
            id="email"
            name="email-input"
            v-model="form.email"
            type="email"
            label="Adres e-mail"
            :is-flat="true"
            placeholder="np. jan@kowalski.pl"
            required
          />

          <TextField
            id="password"
            name="password-input"
            v-model="form.password"
            label="Hasło"
            :is-flat="true"
            type="password"
            required
          />

          <TextField
            id="password-repeat"
            name="password-input-repeat"
            v-model="form.passwordConfirm"
            label="Powtórz hasło"
            :is-flat="true"
            type="password"
            required
          />

          <div class="buttons">
            <Button name="submit-button" type="submit" variant="dark">
              Zarejestruj
            </Button>
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

  head() {
    return {
      title: 'Rejestracja',
    };
  },

  data() {
    return {
      form: {
        fullName: null,
        email: null,
        password: null,
        passwordConfirm: null,
      },
    };
  },

  methods: {
    validate() {
      // Validate full name
      if (!this.form.fullName?.length) {
        this.$notify({ text: 'Podaj imię i nazwisko.', type: 'error' });
        return false;
      }

      // Validate password
      if (!this.form.password
      || this.form.password.length < 8
      || this.form.password !== this.form.passwordConfirm) {
        this.$notify({ text: 'Wprowadzone hasła muszą być takie same i mieć co najmniej 8 znaków.', type: 'error' });
        return false;
      }

      return true;
    },

    async registerAccount() {
      if (!this.validate()) return;
      
      const [firstName, ...lastNameParts] = this.form.fullName.split(' ');
      const lastName = lastNameParts?.join(' ') || null;

      const payload = {
        firstName,
        lastName,
        ...this.form,
      };

      try {
        await this.$store.dispatch('user/register', payload);
        this.$notify({ text: 'Konto założone. Możesz się teraz zalogować.', type: 'success' });
        this.$router.push('/login');
      } catch (error) {
        this.$notify({ text: 'Wystąpił problem przy tworzeniu konta.', type: 'error' });
        console.error(error);
      }
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

  @media screen and (max-width: $desktop_breakpoint) {
    background: #FDFDFD;
    box-shadow: unset;

    .register-form {
      background: transparent;
      padding: 2em 0;
    }

    .register-form-title {
      text-align: center;
      font-size: 1.1em;
      text-indent: unset; 
    }
  }
}
</style>
