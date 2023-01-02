<template>
  <div class="Error">
    <p>
      Wystąpił błąd {{ errorCode }}.<br />
      Można tu dodać jakąś informację i powrót na stronę główną...<br />
    </p>
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
  },
};
</script>

<style lang="scss">
  .Error {
    max-width: 1440px;
    padding: 10rem 0;
    margin: 0 auto;
    text-align: center;
  }
</style>
