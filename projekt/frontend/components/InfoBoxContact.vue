<template>
  <BoxSection class="info-box-outer main-container wider">
    <template #header>
      <h2 class="info-box-title">Zadaj nam pytanie</h2>
    </template>
    <template #body>
      <div class="info-box">
        <form class="info-box-container-inner" @submit.prevent="sendMessage">
          <div class="section">
            <TextField
              :tab-index="1"
              id="name"
              name="name-input"
              v-model="name"
              label="Imię"
              :is-flat="true"
            />

            <TextField
              id="email"
              name="email-input"
              v-model="email"
              label="Adres e-mail"
              :is-flat="true"
              placeholder="np. jan@kowalski.pl"
              :tab-index="2"
            />

            <div class="checkbox-label">
              <Checkbox v-model="agreement" :tab-index="4" class="margin-top">
                Wyrażam zgodę na przetwarzanie moich danych osobowych przez Politechnikę Gdańską,
                zgodnie z
                <a href="#" tabindex="5">polityką prywatności</a> w celu odpowiedzi na pytanie 
                zawarte w tym formularzu i dalszą korespondencję elektroniczą.
              </Checkbox>
            </div>
          </div>
          <div class="section">
            <TextField
              id="question"
              name="question-area"
              v-model="question"
              label="Jakie masz pytanie?"
              :is-flat="true"
              :is-textarea="true"
              :tab-index="3"
            />

            <div class="checkbox-label mobile">
              <Checkbox v-model="agreement" :tab-index="4" class="margin-top">
                Wyrażam zgodę na przetwarzanie moich danych osobowych przez Politechnikę Gdańską,
                zgodnie z
                <a href="#" tabindex="5">polityką prywatności</a> w celu odpowiedzi na pytanie 
                zawarte w tym formularzu i dalszą korespondencję elektroniczą.
              </Checkbox>
            </div>

            <div class="buttons">
              <Button tabindex="6" name="submit-button" type="submit" variant="dark">
                Wyślij wiadomość
              </Button>
            </div>
          </div>
        </form>
      </div>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import Icon from '@/components/shared/Icon';
import Button from '@/components/shared/Button';
import TextField from '@/components/shared/TextField';
import Checkbox from '@/components/shared/Checkbox';

export default {
  components: {
    BoxSection,
    Icon,
    Button,
    TextField,
    Checkbox,
  },
  data() {
    return {
      name: '',
      email: '',
      question: '',
      agreement: false,
    };
  },
  methods: {
    sendMessage() {
      if (this.validate()) {
        this.$notify({ text: 'Wiadomość wysłana pomyślnie', type: 'success' });
        this.name = '';
        this.email = '';
        this.question = '';
        this.agreement = false;
      }
    },
    validate() {
      if (!/[A-Za-z0-9]*@[A-Za-z0-9]*\.[A-Za-z]*/.test(this.email)) {
        this.$notify({ text: 'Podaj prawidłowy adres e-mail', type: 'error' });
        return false;
      }

      if (!this.name.length) {
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

<style lang="scss" scoped>
code {
  background: rgba(221, 221, 221, 0.6);
  font-family: 'Lucida Console', 'Courier New', monospace;
  font-size: 15px;
  padding: 3px 5px;
}

.main-container.wider {
  max-width: 66vw !important;
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
    padding: 0 2rem;
    padding-bottom: 1rem;
    background: #fff;

    .info-box-container-inner {
      box-sizing: border-box;
      padding: 20px 20px 15px 20px;
      padding-top: 0;
      border-radius: 10px;
      display: flex;
      flex-wrap: wrap;

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

      .info-box-container-inner {
        padding-left: 0;
        padding-right: 0;

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
