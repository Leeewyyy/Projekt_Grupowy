<template>
  <BoxSection class="account-data">
    <template #header>
      <h2 class="account-data-title">Dane konta</h2>
    </template>
    <template #body>
      <form class="account-data-container" @submit.prevent="submitSearch">
        <TextField name="fullname-input" v-model="form.fullName" label="Imię i nazwisko" />
        <TextField name="email-input" v-model="form.email" label="Adres e-mail" class="mt-1" />

        <div class="account-data-container-inner">
          <TextField
            name="current-password-input"
            v-model="form.currentPassword"
            label="Obecne hasło"
            type="password"
          />
          <TextField
            name="new-password-input"
            v-model="form.newPassword"
            label="Nowe hasło"
            type="password"
            class="mt-1"
          />
          <TextField
            name="new-password-confirmed-input"
            v-model="form.newPasswordConfirmed"
            label="Potwierdź nowe hasło"
            type="password"
            class="mt-1"
          />
        </div>

        <div class="buttons">
          <Button
            name="submit-button"
            type="submit"
            variant="dark"
            :disabled="isButtonDisabled"
            class="w-100"
          >
            Szukaj
          </Button>
        </div>
      </form>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import TextField from '@/components/shared/TextField';
import Button from '@/components/shared/Button';

export default {
  components: {
    BoxSection,
    TextField,
    Button,
  },

  data() {
    return {
      form: {
        fullName: 'Marek Niewiestny',
        email: 'marek.niewiestny@gmail.com',
        currentPassword: '',
        newPassword: '',
        newPasswordConfirmed: '',
      },
      isButtonDisabled: true,
    };
  },
  watch: {
    form: {
      deep: true,
      handler() {
        this.isButtonDisabled = false;
      },
    },
  },
  methods: {
    submitSearch() {
      if (this.validate()) {
        this.$notify({
          text: 'Dane zostały zmienione pomyślnie!',
          type: 'success',
        });
      }
    },
    validate() {
      if (!this.form.fullName.length) {
        this.$notify({ text: 'Podaj imię i nazwisko', type: 'error' });
        return false;
      }

      if (!/[A-Za-z0-9]*@[A-Za-z0-9]*\.[A-Za-z]*/.test(this.form.email)) {
        this.$notify({ text: 'Podaj prawidłowy adres e-mail', type: 'error' });
        return false;
      }

      if (!!this.form.newPassword.length && this.form.newPassword.length < 8) {
        this.$notify({ text: 'Nowe hasło powinno zawierać minimum 8 znaków', type: 'error' });
        return false;
      }

      if (this.form.newPassword !== this.form.newPasswordConfirmed) {
        this.$notify({ text: 'Nowe hasła muszą być takie same', type: 'error' });
        return false;
      }

      if (
        !this.form.currentPassword.length
        && this.form.newPassword.length
        && this.form.newPasswordConfirmed.length
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
    padding: 2rem;
    padding-bottom: 1rem;
    background: rgb(var(--color-main));

    .account-data-container-inner {
      box-sizing: border-box;
      background: rgb(var(--color-side));
      padding: 20px;
      border-radius: 10px;
      margin-top: 20px;
    }
  }

  .buttons {
    margin-top: 20px;
  }
}
</style>
