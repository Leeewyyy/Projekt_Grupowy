<template>
  <div class="padding-2rem" style="padding-top: 1rem;">
    <div 
      v-for="(section, index) in sections"
      :key="index"
    >
      <SingleWelcomeBox
        v-if="section.visible"
        :header="section.header"
        :description="section.description"
        :value="section.isOpen"
        @input="toggleSection"
        :style="index && !section.isOpen ? 'margin-top: 1rem' : null"
      >
        <div v-if="section.isOpen">
          <div v-if="section.name === 'how-to'">
            <p>
              To proste! Potrzebujesz udać się do lekarza, ale nie wiesz, gdzie znajdziesz najbliższą placówkę?
              <b>Po to jest LokalnyLekarz.pl</b> Wpisz w powyższym polu wyszukiwania oczekiwany adres i wybierz z dostępnych pól
              lub naciśnij ikonkę lokalizacji, a system sam Cię znajdzie. Następnie wybierz dostępne filtry i naciśnij Szukaj. Pokażą Ci się
              wszystkie placówki medyczne o wybranych parametrach.
            </p>
          </div>
          <div v-else-if="section.name === 'no-place'">
            <p>
              W celu dodania placówki utwórz konto użytkownika, zweryfikuj je, a w panelu użytkownika będziesz mógł dodać swoją placówkę.
            </p>
          </div>
          <div v-else-if="section.name === 'account'">
            <p>
              Konto w systemie otwiera drzwi do świata zarządzania placówkami.
              Główne funkcjonalnosci to:
            </p>
            <ul class="list">
              <li>Dodawanie placówek do ulubionych</li>
              <li>Wystawianie opinii placówkom</li>
              <li>Dostęp do panelu użytkownika</li>
            </ul>
          </div>
        </div>
      </SingleWelcomeBox>
    </div>
  </div>
</template>

<script>
import SingleWelcomeBox from '@/components/SingleWelcomeBox';

export default {
  components: {
    SingleWelcomeBox,
  },
  data() {
    return {
      sections: [
        {
          name: 'how-to',
          header: 'Jak to działa?',
          description: 'Dowiedz się więcej, jak korzystać z aplikacji',
          isOpen: false,
          visible: true,
        },
        {
          name: 'no-place',
          header: 'Brak placówki?',
          description: 'Dodaj ją przy pomocy prostego formularza',
          isOpen: false,
          visible: true,
        },
        {
          name: 'account',
          header: 'Co mi daje konto w systemie?',
          description: 'Zobacz możliwości, jakie daje konto użytkownika',
          isOpen: false,
          visible: true,
        },
      ],
    };
  },
  mounted() {
    window.addEventListener('popstate', () => this.reset());
  },
  methods: {
    toggleSection({ header, isOpen }) {
      this.hideOtherSections(header, isOpen);
      if (isOpen) {
        /* eslint-disable-next-line */
         history.pushState({}, "", '');
      } else {
        /* eslint-disable-next-line */
        history.back();
      }
    },
    hideOtherSections(header, isOpen) { 
      this.sections = this.sections.map((section) => ({
        ...section,
        isOpen: section.header === header && isOpen,
        visible: section.header === header ? true : !isOpen,
      }));
    },
    reset() {
      this.sections = this.sections.map((section) => ({
        ...section,
        isOpen: false,
        visible: true,
      }));
    },
  },
};
</script>

<style scoped>
.list {
  padding-left: 20px;
  margin-top: 20px;
}
</style>
