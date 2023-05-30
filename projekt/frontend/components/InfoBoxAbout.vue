<template>
  <BoxSection class="info-box-outer main-container wider">
    <template #header>
      <h2 class="info-box-title">O aplikacji</h2>
    </template>
    <template #body>
      <div class="info-box">
        <div class="info-box-container-inner">
          <p>
            Aplikacja <code>lokalnylekarz.pl</code> powstała w celu rozwiązania problemu jaki dotyka
            większość ludzi, gdy chcą umówić się na badanie - wyszukanie placówki, która jest
            najbliżej nich.
          </p>
          <br />
          <p>
            Znając potrzeby ludzi wychodzimy im naprzeciw. Dzięki tej aplikacji znajdziesz
            najbliższe placówki medyczne, zaczynając od przychodni rodzinnych, przez placówki
            lekarzy specjalistów, aż na szpitalach kończąc.
          </p>
          <br />
          <p>
            Już nigdy więcej nie będziesz musiał/a szukać informacji o możliwości przyjmowania na
            NFZ. W naszej aplikacji szybko wyszukasz konkretne placówki, które mają podpisaną umowę
            z Narodowym Funduszem Zdrowia. Wystarczy, że zaznaczysz ikonkę <code>NFZ+</code>przy
            formularzu.
          </p>
          <br />
          <p>
            Wszystko odbywa się w prosty sposób. Wpisz adres lub wyszukaj swoją lokalizację przez
            naciśnięcie ikonki <code><Icon name="location_on" class="relative-icon" /></code>.
            Wybierz specjalistę, do którego chcesz się udać lub zostaw puste pole, jeśli chcesz
            zobaczyć placówki wszystkich możliwych lekarzy w okolicy. Możesz też wybrać typ
            placówki, do której chcesz się udać i odległóść od miejsca wyszukiwania. Domyślnie jest
            ona ustawiona na <code>25 km</code>.
          </p>
          <br />
          <p>To jak? Sprawdzasz?</p>
          <div class="buttons">
            <form @submit.prevent="$router.push({ name: 'index' })">
              <Button name="submit-button" type="submit" variant="dark"> Sprawdzam </Button>
            </form>
          </div>
        </div>
      </div>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import Icon from '@/components/shared/Icon';
import Button from '@/components/shared/Button';

export default {
  components: {
    BoxSection,
    Icon,
    Button,
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
  padding-top: 20px;
  
  @media screen and (max-width: $desktop_breakpoint) {
    background: #FDFDFD;
    box-shadow: unset;
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
    }

    @media screen and (max-width: $desktop_breakpoint) {
      background: transparent;  

      .info-box-container-inner {
        padding-left: 0;
      }
    }
  }

  .buttons {
    width: 400px;
    margin-top: 20px;
  }

  .relative-icon {
    position: relative;
    top: 3px;
  }
}
</style>
