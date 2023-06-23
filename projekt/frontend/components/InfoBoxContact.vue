<template>
  <BoxSection class="info-box-outer main-container wider">
    <template #header>
      <div class="header_logo">
        <Branding
          id="contactUsBranding"
          description="Napisz do nas"
        />
      </div>
    </template>
    <template #body>
      <div class="info-box">
        <form class="info-box-container-inner" @submit.prevent="sendMessage">
            <InputText
              id="subject"
              name="name-input"
              v-model="subject"
              label="Temat"
            />

            <InputText
              id="email"
              name="email-input"
              v-model="email"
              label="Adres e-mail"
              placeholder="np. jan@kowalski.pl"
              style="margin-top: 20px;"
            />
            
            <InputText
              id="question"
              name="question-area"
              v-model="question"
              placeholder="Opisz problem, zadaj pytanie"
              label="Tekst wiadomości"
              style="margin-top: 20px"
            />

            <SwitchButton id="consent" v-model="agreement" dir="right">
              Wyrażam zgodę na przetwarzanie moich danych osobowych przez Politechnikę Gdańską,
              zgodnie z
              <a href="#" tabindex="5">polityką prywatności</a> w celu odpowiedzi na pytanie 
              zawarte w tym formularzu i dalszą korespondencję elektroniczą.
            </SwitchButton>

            <Button type="submit" class="submit">
              Wyślij wiadomość
            </Button>
        </form>
      </div>
    </template>
  </BoxSection>
</template>

<script>
// eslint-disable-next-line
import { mapActions } from 'vuex';
import BoxSection from '@/components/BoxSection';
import Icon from '@/components/shared/Icon';
import Button from '@/components/shared/Button';
import InputText from '@/components/shared/InputText';
import Checkbox from '@/components/shared/Checkbox';
import SwitchButton from '@/components/shared/SwitchButton';
import Branding from './Branding.vue';

export default {
  components: {
    BoxSection,
    Icon,
    Button,
    InputText,
    Checkbox,
    SwitchButton,
    Branding,
  },
  data() {
    return {
      subject: '',
      email: '',
      question: '',
      agreement: false,
    };
  },
  methods: {
    ...mapActions('mailSender', ['sendMail']),
    sendMessage() {
      if (this.validate()) {
        this.sendMail({ fromEmail: this.email, subject: this.subject, text: this.question })
          .then(() => {
            this.$notify({ text: 'Wiadomość wysłana pomyślnie', type: 'success' });
            this.subject = '';
            this.email = '';
            this.question = '';
            this.agreement = false;
          });
      }
    },
    validate() {
      if (!/[A-Za-z0-9]*@[A-Za-z0-9]*\.[A-Za-z]*/.test(this.email)) {
        this.$notify({ text: 'Podaj prawidłowy adres e-mail', type: 'error' });
        return false;
      }

      if (!this.subject.length) {
        this.$notify({ text: 'Podaj imię', type: 'error' });
        return false;
      }

      if (!this.question.length) {
        this.$notify({ text: 'Podaj treść wiadomości', type: 'error' });
        return false;
      }

      if (!this.agreement) {
        this.$notify({ text: 'Zaznacz zgodę na przetwarzanie danych osobowych.', type: 'error' });
        return false;
      }

      return true;
    },
  },
};
</script>

<style lang="scss">
@media screen and (max-width: $desktop_breakpoint) {
  #info-box-contact {
    margin-top: 0;
  }
}
</style>
<style lang="scss" scoped>
code {
  background: rgba(221, 221, 221, 0.6);
  font-family: 'Lucida Console', 'Courier New', monospace;
  font-size: 15px;
  padding: 3px 5px;
}

.main-container.wider {
  max-width: 35% !important;
  max-height: unset !important;
  
  @media screen and (max-width: $desktop_breakpoint) {
    max-width: unset !important;
  }
}

.info-box-outer {

  @media screen and (max-width: $desktop_breakpoint) {
    background: #FDFDFD;
    box-shadow: unset;
    padding-top: 20px;
  }
  
  .info-box-title {
    height: 55px;
    line-height: 55px;
    text-indent: 2rem;
    font-size: 1.2rem;
  }

  .info-box {
    padding: 0 0 2rem 0;
    background: #fff;

    .info-box-container-inner {
      box-sizing: border-box;
      width: 70%;
      margin: auto;
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 30px;

      .SwitchButton {
        width: 100%;
        margin-top: 20px;
      }

      .submit {
        width: 100%;
        margin-top: 20px;
      }

      .section {
        width: 50%;
        box-sizing: border-box;
        padding-right: 20px;
        display: flex;
        flex-wrap: wrap;

        .checkbox-label.mobile {
          display: none;
        }

        .text-field-outer, .buttons {
          width: 100%;
          padding-right: 0;
        }
      }
    }

    @media screen and (max-width: $desktop_breakpoint) {
      background: transparent;
      padding: 0 2rem !important;

      #info-box-contact {
        margin-top: 0;
      }

      .info-box-container-inner {
        padding-left: 0;
        padding-right: 0;
        margin: auto;
        width: 100%;

        .section {
          width: 100%;
          padding: 0;

          .checkbox-label {
            display: none;

            &.mobile {
              display: block;
            }
          }
        }
      }
    }

    .text-field-outer {
      margin-top: 20px;
    }
  }

  .buttons button {
    width: 100%;
    margin-top: 30px;
  }

  .relative-icon {
    position: relative;
    top: 3px;
  }

  .margin-top {
    margin-top: 20px;
  }
}
</style>
