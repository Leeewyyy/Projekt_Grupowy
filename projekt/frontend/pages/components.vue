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
      <br /><br /><h2>Input</h2><br />
      <InputText
          name="example-input"
          v-model="searchValue"
          placeholder="Zwykły input"
          style="width: 50%;"
        />
        <InputText
          name="example-input"
          v-model="searchValue"
          placeholder="Przykładowy placeholder"
          label="Z labelką"
          style="width: 50%; margin-top: 20px;"
        />
        <InputText
          name="example-input"
          v-model="searchValue"
          placeholder="Z klikalną ikonką"
          :icon="{ 
            show: true,
            name: 'my_location',
            tooltip: 'Przykładowy dymek',
            size: 23,
          }"
          @iconClicked="() => $notify({ text: 'Icon clicked!' })"
          style="width: 50%; margin-top: 20px;"
        />
        <InputText
          name="example-input"
          v-model="searchValue"
          placeholder="z labelką i z klikalną ikonką"
          :icon="{ 
            show: true,
            name: 'add',
            tooltip: 'Przykładowy dymek',
            size: 23,
          }"
          label="Łączony input"
          @iconClicked="() => $notify({ text: 'Icon clicked!' })"
          style="width: 50%; margin-top: 20px;"
        />
        <br /><br /><h2>Select</h2><br />
        <Select
            name="exmple-select-1"
            v-model="selectedValue"
            :options="[]"
            label="name"
            style="width: 50%; margin-top: 20px;"
            placeholder="Pusty select"
        />
        <Select
            name="exmple-select-2"
            v-model="selectedValue"
            :options="[]"
            label="name"
            select-label="Z labelką"
            style="width: 50%; margin-top: 20px;"
        />
        <Select
            name="exmple-select-3"
            v-model="selectedValue"
            :options="optionsForSelect"
            label="name"
            select-label="Z opcjami"
            style="width: 50%; margin-top: 20px;"
        />
        <br /><br /><br /><br /><br /><br /><br /><br />
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import Button from '@/components/shared/Button';
import SwitchButton from '@/components/shared/SwitchButton';
import InputText from '@/components/shared/InputText';
import Select from '@/components/shared/Select';

export default {
  components: {
    AppPage,
    Button,
    SwitchButton,
    InputText,
    Select,
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
      searchValue: '',
      selectedValue: null,
      optionsForSelect: [
        { id: 0, name: 'Opcja 1' },
        { id: 1, name: 'Opcja 2' },
        { id: 2, name: 'Opcja 3' },
      ],
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
