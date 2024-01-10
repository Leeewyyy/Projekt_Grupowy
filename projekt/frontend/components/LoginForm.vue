<template>
  <BoxSection class="login-form-outer main-container">
    <template #header>
      <div class="header_logo">
        <Branding
          id="loginBranding"
          description="Wpisz dane logowania"
        />
      </div>
    </template>
    <template #body>
      <form class="login-form" @submit.prevent="submitLogin">
        <div class="login-form-container-inner">
          <InputText
            id="login"
            name="login-input"
            v-model="inputLogin"
            label="Adres e-mail"
            class="element"
          />

          <InputText
            id="password"
            name="password-input"
            v-model="inputPassword"
            label="Hasło"
            :icon="{
              show: true,
              name: 'remove_red_eye',
              size: 23,
            }"
            :type="showPass ? 'text' : 'password'"
            @iconClicked="showPass = !showPass"
          />

          <div class="buttons">
            <Button name="submit-button" type="submit" variant="dark"> Zaloguj się</Button>
          </div>
        </div>
        <NuxtLink to="register" class="register-link" data-tid="login-go-to-register-button"> Nie masz konta? Zarejestruj się! </NuxtLink>
      </form>
    </template>
  </BoxSection>
</template>

<script>
// eslint-disable-next-line
import { mapGetters } from 'vuex';
import BoxSection from '@/components/BoxSection';
import InputText from '@/components/shared/InputText';
import Select from '@/components/shared/Select';
import Button from '@/components/shared/Button';
import IconToggleButton from '@/components/shared/IconToggleButton';
import Icon from '@/components/shared/Icon';
import Branding from './Branding.vue';

export default {
  components: {
    BoxSection,
    InputText,
    Select,
    Button,
    IconToggleButton,
    Icon,
    Branding,
  },

  data() {
    return {
      inputLogin: '',
      inputPassword: '',
      showPass: false,
    };
  },
  computed: {
    ...mapGetters('user', {
      isLoggedIn: 'isLoggedIn',
      user: 'getUser',
    }),
  },
  methods: {
    async submitLogin() {
      if (!this.validate()) {
        this.$notify({ text: 'Wypełnij wszystkie pola zanim spróbujesz się zalogować.', type: 'error' });
        return;
      }

      const payload = {
        email: this.inputLogin,
        password: this.inputPassword,
      };
      
      try {
        await this.$store.dispatch('user/login', payload);
        this.$notify({ text: 'Zalogowano pomyślnie!', type: 'success' });
        this.$store.commit('cookie/setCookie', { name: 'userId', value: this.user.id });
        this.$router.push({ name: 'user-panel' });
      } catch (error) {
        this.$notify({ text: 'Wstąpił błąd logowania. Spóbuj ponownie.', type: 'error' });
      }
    },

    validate() {
      return this.inputLogin.length && this.inputPassword.length;
    },
  },
};
</script>

<style lang="scss">
#loginBranding {
  max-width: 300px;
}

.login-form-outer {
  min-height: 60% !important;

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

    #password {
      margin-top: 25px;
    }
  }

  .register-link {
    margin-left: 20px;
    font-size: 13px;
    font-weight: 400;
    position: relative;
    display: block;
    text-align: center !important;
    font-weight: 400 !important;
  }

  .buttons {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 20px;

    button[name='submit-button'] {
      width: 100%;
    }
  }

  @media screen and (max-width: $desktop_breakpoint) {
    background: #FDFDFD;
    box-shadow: unset;

    .login-form {
      background: transparent;
      padding: 2em 0;
    }

    .login-form-title {
      text-align: center;
      font-size: 1.1em;
      text-indent: unset; 
    }
  }
}
</style>
