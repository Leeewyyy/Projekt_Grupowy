<template>
  <BoxSection class="account-data">
    <template #header>
      <h2 class="account-data-title">Zmień dane konta</h2>
    </template>
    <template #body>
      <form class="account-data-container" @submit.prevent="submitSearch">
        <InputText 
          name="fullname-input"
          v-model="form.fullName"
          label="Imię i nazwisko"
        />

        <InputText 
          name="email-input"
          v-model="form.email"
          label="Adres e-mail"
          class="mt-1 element"
        />

      <InputText
          name="current-password-input"
          v-model="form.currentPassword"
          label="Obecne hasło"
          :type="showPass.current ? 'text' : 'password'"
          :icon="{
            show: true,
            name: 'remove_red_eye',
            size: 23,
          }"
          class="element"
          @iconClicked="showPass.current = !showPass.current"
        />
        <InputText
          name="new-password-input"
          v-model="form.newPassword"
          label="Nowe hasło"
          :type="showPass.new ? 'text' : 'password'"
          :icon="{
            show: true,
            name: 'remove_red_eye',
            size: 23,
          }"
          @iconClicked="showPass.new = !showPass.new"
          class="mt-1 element"
        />
        <InputText
          name="new-password-confirmed-input"
          v-model="form.newPasswordConfirmed"
          label="Potwierdź nowe hasło"
          :type="showPass.repeat ? 'text' : 'password'"
          :icon="{
            show: true,
            name: 'remove_red_eye',
            size: 23,
          }"
          @iconClicked="showPass.repeat = !showPass.repeat"
          class="mt-1 element"
        />
        <div class="buttons">
          <Button
            name="submit-button"
            type="submit"
            :disabled="isButtonDisabled"
            class="width-100"
          >
            Zapisz
          </Button>
        </div>
      </form>
    </template>
  </BoxSection>
</template>

<script>
// eslint-disable-next-line
import { mapGetters } from 'vuex';
import BoxSection from '@/components/BoxSection';
import InputText from '@/components/shared/InputText';
import Button from '@/components/shared/Button';

export default {
  components: {
    BoxSection,
    InputText,
    Button,
  },

  data() {
    return {
      form: {
        fullName: '',
        email: '',
        currentPassword: '',
        newPassword: '',
        newPasswordConfirmed: '',
      },
      showPass: {
        current: false,
        new: false,
        repeat: false,
      },
      isButtonDisabled: true,
    };
  },

  mounted() {
    const { fullName, email } = this.user;
    this.form = { fullName, email };
  },

  watch: {
    form: {
      deep: true,
      handler() {
        this.isButtonDisabled = false;
      },
    },
  },

  computed: {
    ...mapGetters('user', {
      user: 'getUser',
    }),
  },

  methods: {
    async submitSearch() {
      if (!this.validate()) return;
      const data = {
        fullName: this.form.fullName,
        email: this.form.email,
        password: this.form.newPassword,
      };

      this.$store.dispatch('user/changeUserData', { 
        userId: this.user.id,
        data,
      })
        .then(() => {
          this.$notify({
            text: 'Dane zostały zmienione pomyślnie!',
            type: 'success',
          });
          this.$store.dispatch('user/getData', this.user.id);
        })
        .catch(() => {
          this.$notify({
            text: 'Wystąpił błąd podczas edycji danych.',
            type: 'error',
          });
        });
    },

    validate() {
      if (!this.form.fullName?.length) {
        this.$notify({ text: 'Podaj imię i nazwisko', type: 'error' });
        return false;
      }

      if (!/[A-Za-z0-9]*@[A-Za-z0-9]*\.[A-Za-z]*/.test(this.form.email)) {
        this.$notify({ text: 'Podaj prawidłowy adres e-mail', type: 'error' });
        return false;
      }

      if (!!this.form.newPassword?.length && this.form.newPassword.length < 8) {
        this.$notify({ text: 'Nowe hasło powinno zawierać minimum 8 znaków', type: 'error' });
        return false;
      }

      if (this.form.newPassword !== this.form.newPasswordConfirmed) {
        this.$notify({ text: 'Nowe hasła muszą być takie same', type: 'error' });
        return false;
      }

      if (
        !this.form.currentPassword?.length
        && this.form.newPassword?.length 
        && this.form.newPasswordConfirmed?.length
      ) {
        this.$notify({ text: 'Podaj obecne hasło', type: 'error' });
        return false;
      }

      return true;
    },
  },
};
</script>

<style lang="scss">
.account-data {
  .account-data-title {
    height: 55px;
    line-height: 55px;
    text-indent: 2rem;
    font-size: 1.2rem;
  }

  .account-data-container {
    padding: .5rem 2rem 2rem 2rem;

    .element {
      margin-top: 10px;
    }
  }

  .buttons {
    margin-top: 20px;
  }
}
</style>
