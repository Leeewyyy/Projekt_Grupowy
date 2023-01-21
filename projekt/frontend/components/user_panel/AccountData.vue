<template>
  <BoxSection class="account-data">
    <template #header>
      <h2 class="account-data-title">Dane konta</h2>
    </template>
    <template #body>
      <form class="account-data-container" @submit.prevent="submitSearch">
        <TextField 
          name="fullname-input"
          v-model="form.fullName"
          label="Imię i nazwisko"
        />

        <TextField 
          name="email-input"
          v-model="form.email"
          label="Adres e-mail"
          class="mt-1"
        />

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
            Zapisz
          </Button>
        </div>
      </form>
    </template>
  </BoxSection>
</template>

<script>
/*eslint-disable-next-line*/
import { mapGetters } from 'vuex';
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
        fullName: '',
        email: '',
        currentPassword: '',
        newPassword: '',
        newPasswordConfirmed: '',
      },
      isButtonDisabled: true,
    };
  },
  mounted() {
    this.form.fullName = `${this.user.firstname} ${this.user.lastname}`;
    this.form.email = this.user.email;
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
      if (this.validate()) {
        try {
          await this.$store.dispatch('user/changeUserData', { 
            data: {
              firstName: this.form.fullName.split(' ')[0],
              lastName: this.form.fullName.split(' ').slice(1).join(' '),
              email: this.form.email,
              password: this.form.newPassword,
            }, 
            userId: this.user.id,
          });
          this.$notify({
            text: 'Dane zostały zmienione pomyślnie!',
            type: 'success',
          });
        } catch (err) {
          this.$notify({
            text: 'Wystąpił błąd podczas edycji danych.',
            type: 'error',
          });
        }
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
