<template>
  <div class="outer-welcome">
    <p>Aplikacja <code>lokalnylekarz.pl</code> to miejsce, gdzie znajdziesz placówki
    medyczne w oklicy swojej lokalzacji lub wybranego przez siebie adresu.</p>

    <p>Wystarczy, że wpiszesz kilka liter, a system podpowie Ci
    najbliższe miejsca. Możesz też filtrować placówki po typie placówki,
    specjaliście, do którego chcesz się udać, możliwości refundowania przez Narodowy
    Fundusz Zdrowia i odległości od wskazanej lokalizacji.</p>

    <form @submit.prevent="handleSubmit">
      <Button name="submit-button" type="submit" variant="dark"> Sprawdzam </Button>
    </form>
  </div>
</template>

<script>
import Button from '@/components/shared/Button';

export default {
  components: {
    Button,
  },
  methods: {
    handleSubmit() {
      this.$store.commit('cookie/setCookie', { name: 'showWelcomeBox', value: true });
    },
  },
};
</script>

<style lang="scss" scoped>
.outer-welcome {
  box-sizing: border-box;
  padding-left: 31px;
  padding-right: 31px;

  p:nth-child(2) {
    margin: 15px 0;
  }
}

code {
  background: rgba(221, 221, 221, 0.6);
  font-family: 'Lucida Console', 'Courier New', monospace;
  font-size: 15px;
  padding: 3px 5px;
}

button[name=submit-button] {
  margin: 20px 0;
  width: 100%;
}
</style>
