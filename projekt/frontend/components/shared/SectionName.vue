<template>
  <div class="SectionName">
    <div class="SectionName_title">
      <slot />
    </div>
  </div>
</template>

<script>

</script>

<style lang="scss">
.SectionName {
  .SectionName_title {
    display: flex;
    flex-direction: row;
    align-items: center;
    color: #333;
    white-space: nowrap;
    
    &::before {
      content: '';
      display: block;
      width: 3rem;
      height: 1px;
      margin-right: 1rem;
      background: #c4c4c4;
    }

    &::after {
      content: '';
      display: block;
      width: 100%;
      height: 1px;
      margin-left: 1rem;
      background: #c4c4c4;
    }
  }
}
</style>
