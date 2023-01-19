<template>
  <BoxSection class="inner-padding" v-if="user">
    <template #header> Witaj, {{ user.firstname }} :) </template>
    <template #body>
      <div class="avatar-section d-flex-between margin-top">
        <div class="avatar d-flex-center section-element">
          <img
            v-if="user.imageUrl"
            class="account_avatar"
            :src="user.imageUrl"
            alt=""
          />
          <Icon
            v-else
            name="account_circle"
            :size="150"
            color="#8AA9CE"
          />
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
  components: {
    BoxSection,
    Icon,
    InputFile,
  },

  props: {
    user: {
      type: Object,
      required: true,
    },
  },

  data() {
    return {
      file: null,
    };
  },

  methods: {
    async uploadImage({ target: { files } }) {
      const [avatarFile] = files;

      if (!['image/png', 'image/png', 'image/jpeg'].includes(avatarFile.type)) {
        this.$notify({ text: 'Wybrano nieprawidłowy plik (dopuszczalne rozszerzenia: png, jpg, jpeg).', type: 'error' });
        return; // eslint-disable-line
      }

      const payload = {
        userId: this.user?.id,
        avatarFile,
      };

      try {
        await this.$store.dispatch('user/uploadAndSetAvatar', payload);

        // Set new user image
        const localImageUrl = URL.createObjectURL(avatarFile);
        this.$notify({ text: 'Pomyślnie zmieniono awatar!', type: 'success' });
        this.$emit('avatarUpdated', localImageUrl);
      } catch (error) {
        this.$notify({ text: 'Nie udało się wgrać obrazka.', type: 'error' });
        console.error(error);
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

.avatar-section {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-around;

  .account_avatar {
    width: 150px;
    height: 150px;
    border-radius: 50%;
  }
}

.section-element {
  .file-place {
    flex-direction: column;
    // white-space: nowrap;
    
    span {
      font-weight: 600;
    }
  }
}

.margin-top {
  margin-top: 10px;
}
</style>
