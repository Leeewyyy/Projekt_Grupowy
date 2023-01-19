<template>
  <div class="Error">
    <div class="Error_title">Wystąpił błąd {{ errorCode }}</div>
    <div class="Error_message">{{ errorMessage }}</div>
  </div>
</template>

<script>
export default {
  props: ['error'],

  head() {
    return {
      title: 'Wystąpił błąd',
      meta: [
        // Disable indexing on this page
        { hid: 'robots', name: 'robots', content: 'noindex' },
      ],
    };
  },

  computed: {
    errorCode() {
      return this.error.statusCode || 500;
    },

    errorMessage() {
      const messageMap = {
        401: 'Ta strona jest dostępna tylko dla zalogowanych użytkowników.',
        404: 'Nie znaleziono podanej strony.',
      };

      return messageMap[this.errorCode] || 'Wystąpił nieznany błąd.';
    },
  },
};
</script>

<style lang="scss">
.Error {
  max-width: 1440px;
  padding: 10rem 0;
  margin: 0 auto;
  font-size: 1.2rem;
  text-align: center;

  .Error_title {
    font-weight: 600;
  }

  .Error_message {
    margin-top: 1rem;
  }
}
</style>
