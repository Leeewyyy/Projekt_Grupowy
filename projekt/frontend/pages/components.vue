<template>
  <AppPage name="components">
    <h1>Test komponentów</h1>
    <div class="components-box">
      <h2>Button</h2><br />
      <Button>Button</Button>
      <Button active>Button - active</Button>
      <Button disabled>Button - disabled</Button>
      <Button type="submit">Button akcji</Button>
      <Button type="submit" disabled>Button akcji - disabled</Button>
      <br /><br /><h2>SwitchButton</h2><br />
      <SwitchButton id="switch1" v-model="s1" /> <br />
      <SwitchButton id="switch2" v-model="s2">Opcja po lewej</SwitchButton> <br />
      <SwitchButton id="switch3" v-model="s3" dir="right">Opcja po prawej</SwitchButton> <br />
      <SwitchButton id="switch4" v-model="s4" :description="desc" /> <br />
      {{ [s1, s2, s3, s4].map((value, idx) => `switch ${idx + 1}: ${value}`).join(' // ') }}
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import Button from '@/components/shared/Button';
import SwitchButton from '@/components/shared/SwitchButton';

export default {
  components: {
    AppPage,
    Button,
    SwitchButton,
  },

  head() {
    return {
      title: 'Test komponentów',
    };
  },

  data() {
    return {
      s1: false,
      s2: true,
      s3: false,
      s4: false,
      desc: 'Button z opisem na dole',
    };
  },
};
</script>

<style lang="scss" scoped>
.AppPage--components {
  padding: 6rem;

  .components-box {
    margin-top: 1rem;
  }
}
</style>
