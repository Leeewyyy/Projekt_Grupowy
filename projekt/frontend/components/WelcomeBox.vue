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
        <p v-if="section.isOpen">{{ section.fullValue }}</p>
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
          header: 'Jak to działa?',
          description: 'Dowiedz się więcej, jak korzystać z aplikacji',
          fullValue: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam iaculis lorem in ligula blandit, a ultricies neque pulvinar. Sed semper faucibus nibh sit amet rhoncus. Maecenas at augue iaculis, commodo ex eu, tincidunt dui. Aenean rhoncus, erat quis viverra egestas, dui ex interdum nisi, vitae convallis odio sapien non elit',
          isOpen: false,
          visible: true,
        },
        {
          header: 'Brak placówki?',
          description: 'Dodaj ją ręcznie przy pomocy prostego formularza',
          fullValue: 'Morbi quam massa, pulvinar vel orci nec, fermentum feugiat dui. Donec venenatis nisi vel velit scelerisque pulvinar. Aliquam fermentum elit neque, ac pellentesque tellus porttitor eget.',
          isOpen: false,
          visible: true,
        },
        {
          header: 'Co mi daje konto w systemie?',
          description: 'Zobacz możliwości, jakie daje konto użytkownika',
          fullValue: 'Donec tincidunt sed nunc ut sollicitudin. Vestibulum iaculis maximus nulla nec rutrum. Phasellus elit justo, semper eu orci sit amet, sollicitudin placerat augue. In dignissim dolor et tincidunt pulvinar. Aliquam dapibus faucibus',
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
