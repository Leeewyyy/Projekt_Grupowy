<template>
  <div class="PlaceDescription">
    <article
      ref="article"
      class="PlaceDescription_markdown"
      v-html="textHtml"
    />
  </div>
</template>

<script>
import Showdown from 'showdown';

export default {
  props: {
    text: {
      type: String,
      required: true,
    },
  },

  computed: {
    textHtml() {
      const converter = new Showdown.Converter();
      return converter.makeHtml(this.text);
    },
  },
};
</script>

<style lang="scss">
  .PlaceDescription {
    padding-bottom: 1rem;

    .PlaceDescription_markdown {
      margin: 0 1rem;

      h1, h2, h3, h4, h5, h6 {
        margin: 1rem 0;
        font-family: 'Montserrat', sans-serif;
        color: #000;
        font-weight: 600;
      }

      h1 {
        font-size: 1.5rem;
        line-height: 2rem;
      }

      h2 {
        font-size: 1.4rem;
        line-height: 2rem;
      }

      h3 {
        font-size: 1.3rem;
        line-height: 1.7rem;
      }

      h4 {
        font-size: 1.2rem;
        line-height: 1.7rem;
      }

      h5 {
        font-size: 1.1rem;
        line-height: 1.3rem;
      }

      h6 {
        font-size: 1rem;
        line-height: 1.25rem;
      }

      p {
        font-size: 1rem;
        font-family: 'Montserrat', sans-serif;
        color: #000;
        font-weight: 400;
        line-height: 1.5rem;

        &:first-child {
          margin-top: 0;
        }
      }

      img {
        width: 100%;
        height: auto;
      }

      a {
        text-decoration: underline;
        font-weight: 600;
      }

      ul, ol {
        margin-top: 1rem;
        padding-left: 1.5rem;
      }

      li {
        font-size: 1rem;
        font-family: 'Montserrat', sans-serif;
        color: #000;
        font-weight: 400;
        margin-bottom: 0.5rem;
      }
    }
  }
</style>
