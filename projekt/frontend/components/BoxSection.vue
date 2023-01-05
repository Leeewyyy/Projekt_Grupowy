<template>
  <section class="BoxSection">
    <header class="BoxSection_header">
      <slot name="header"></slot>
    </header>
    <div class="BoxSection_body">
      <slot name="body"></slot>
    </div>
  </section>
</template>

<style lang="scss">
.BoxSection {
  background: rgb(var(--color-white));
  border-radius: 10px;
  box-shadow: 0px 2px 4px rgba(var(--color-black), 0.125);

  .BoxSection_header {
    font-family: 'Montserrat', sans-serif;
    font-weight: 600;
    font-size: 1.3rem;
    color: rgb(var(--color-text));
  }
}
</style>
