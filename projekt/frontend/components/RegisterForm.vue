<template>
  <BoxSection class="register-form-outer main-container">
    <template #header>
      <div class="header_logo mobile-hidden">
        <Branding
          id="registerBranding"
          description="Utwórz konto w serwisie"
        />
      </div>
    </template>
    <template #body>
      <form class="register-form" @submit.prevent="registerAccount">
        <div class="register-form-container-inner">
          <InputText
            id="full-name"
            name="fullname-input"
            v-model="form.fullName"
            label="Imię i nazwisko"
            class="element"
            required
          />

          <InputText
            id="email"
            name="email-input"
            v-model="form.email"
            type="email"
            label="Adres e-mail"
            placeholder="np. jan@kowalski.pl"
            class="element"
            required
          />

          <InputText
            id="password"
            name="password-input"
            v-model="form.password"
            label="Hasło"
            :type="showPass ? 'text' : 'password'"
            class="element"
            :icon="{
              show: true,
              name: 'remove_red_eye',
              size: 23,
            }"
            @iconClicked="showPass = !showPass"
            required
          />

          <InputText
            id="password-repeat"
            name="password-input-repeat"
            v-model="passwordConfirm"
            label="Powtórz hasło"
            :type="showConfirmPass ? 'text' : 'password'"
            class="element"
            :icon="{
              show: true,
              name: 'remove_red_eye',
              size: 23,
            }"
            @iconClicked="showConfirmPass = !showConfirmPass"
            required
          />

          <SwitchButton class="register-switch" id="consent-regulations" v-model="form.regulations" dir="right">
            Akceptuję <a href="#" target="_blank">regulamin</a> serwisu
          </SwitchButton>

          <SwitchButton class="register-switch" id="consent-policy" v-model="form.privatePolicy" dir="right">
            Akceptuję <a href="#" target="_blank">politykę prywatności</a>
          </SwitchButton>

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
import InputText from '@/components/shared/InputText';
import Select from '@/components/shared/Select';
import Button from '@/components/shared/Button';
import IconToggleButton from '@/components/shared/IconToggleButton';
import Icon from '@/components/shared/Icon';
import Branding from './Branding.vue';
import SwitchButton from './shared/SwitchButton.vue';

export default {
  components: {
    BoxSection,
    InputText,
    Select,
    Button,
    IconToggleButton,
    Icon,
    Branding,
    SwitchButton,
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
        regulations: false,
        privatePolicy: false,
      },
      showPass: false,
      showConfirmPass: false,
      passwordConfirm: null,
    };
  },

  methods: {
    validate() {
      // Validate regulations and policy
      if (!this.form.regulations || !this.form.privatePolicy) {
        this.$notify({ text: 'Zaznacz zgodę dot. regulaminu i polityki prywatności.', type: 'error' });
        return false;
      }

      // Validate full name
      if (!this.form.fullName?.length) {
        this.$notify({ text: 'Podaj imię i nazwisko.', type: 'error' });
        return false;
      }

      // Validate password
      if (!this.form.password
      || this.form.password.length < 8
      || this.form.password !== this.passwordConfirm) {
        this.$notify({ text: 'Wprowadzone hasła muszą być takie same i mieć co najmniej 8 znaków.', type: 'error' });
        return false;
      }

      return true;
    },

    async registerAccount() {
      if (!this.validate()) return;
      
      const payload = {
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

<style>
.register-switch.SwitchButton {
  width: 100%;
  margin-top: 20px;
}

.register-switch .SwitchButton_wrapper {
  justify-content: start !important;
}
</style>

<style lang="scss" scoped>
#registerBranding {
  max-width: 300px;
}

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

    .element {
      margin-top: 10px;
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
