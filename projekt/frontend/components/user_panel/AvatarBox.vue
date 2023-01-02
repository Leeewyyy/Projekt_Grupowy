<template>
  <BoxSection class="inner-padding">
    <template #header> Witaj, {{ username }} :) </template>
    <template #body>
      <div class="avatar-section d-flex-between margin-top">
        <div class="avatar d-flex-center section-element">
          <Icon name="account_circle" :size="150" color="#8AA9CE" />
        </div>
        <div class="upload-file d-flex-center section-element">
          <div class="d-flex-center file-place">
            <span>Zmień zdjęcie</span>
            <InputFile variant="dark" class="margin-top" @fileUploaded="uploadImage">
              Wybierz
            </InputFile>
          </div>
        </div>
      </div>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import Icon from '@/components/shared/Icon';
import InputFile from '@/components/shared/InputFile';

export default {
  components: { BoxSection, Icon, InputFile },
  data() {
    return {
      file: null,
      username: 'Marek',
    };
  },
  methods: {
    uploadImage({ target: { files } }) {
      const [file] = files;

      if (!['image/png', 'image/png', 'image/jpeg'].includes(file.type)) {
        this.$notify({ text: 'Dodaj plik o poprawnym typie: png, jpg lub jpeg', type: 'error' });
      } else {
        this.file = file;
        this.$notify({ text: `Plik o nazwie: ${files[0].name} został dodany`, type: 'success' });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.inner-padding {
  box-sizing: border-box;
  padding: 20px 30px;
}

.section-element {
  width: 50%;

  .file-place {
    flex-direction: column;
    width: 65%;

    span {
      font-weight: 600;
    }
  }
}

.margin-top {
  margin-top: 10px;
}
</style>
