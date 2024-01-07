<template>
  <BoxSection class="add-place-outer">
    <template #header>
      <div class="header_logo justify-start">
        <Branding
          id="addPlaceHeader"
          :with-image="false"
          :align-left="true"
          :custom-text="placeId ? `Edytuj placówkę <i>${place && place.name || ''}</i>` : 'Dodaj placówkę'"
        />
      </div>
    </template>
    <template #body>
      <div class="info-box">

          <form class="add-place-container-inner" @submit.prevent="save">
            <section name="basic-information">
              <SectionName>Informacje podstawowe</SectionName>

              <div class="display-flex align-center row">
                <div class="row-left">
                  <p>Nazwa</p>
                </div>
                <InputText
                  id="name"
                  name="name-input"
                  placeholder="np. Placówka medyczna"
                  v-model="name"
                  class="w-300px"
                />
              </div>

              <div class="display-flex align-start row">
                <div class="row-left">
                  <p>Adres</p>
                </div>
                <div class="display-flex align-center">
                  <SearchInput
                    ref="searchInput"
                    @toggleAddress="handleToggleAddress"
                    :coords="address && address.location || {}"
                    :show-icon="false"
                    :hide-results="false"
                    placeholder="np. Spacerowa 15, Gdańsk"
                    style="padding-left: 0 !important; width: 300px !important;"
                  />
                </div>
                <Button v-if="address" type="button" :active="true" :dark="true" @click="resetAddress()" class="reset-button">
                  Resetuj
                </Button>
              </div>

              <div class="display-flex align-center row">
                <div class="row-left">
                  <p>Typ placówki</p>
                </div>
                <Select
                  name="place-type"
                  v-model="placeType"
                  :options="facilitiesTypes"
                  placeholder="np. SZPITAL"
                  class="w-300px"
                />
              </div>

              <div class="display-flex align-top row">
                <div class="row-left">
                  <p>Opis placówki</p>
                </div>
                <InputText
                  id="description"
                  name="description-input"
                  v-model="description"
                  placeholder="Co oferuje to miejsce, jaką prowadzi opiekę"
                  class="width-70"
                  :is-text-area="true"  
                />
              </div>

              <div class="display-flex align-top row">
                <div class="row-left">
                  <p>Zdjęcia</p>
                </div>
                <InputFile :show-images="true" class="width-70" @fileUploaded="toggleImages" @delete="deleteImage" :images="images" :multiple="true">
                  Wybierz
                </InputFile>
              </div>
            </section>

            <section name="contact-data">
              <SectionName>Dane kontaktowe</SectionName>

              <div class="display-flex align-center row">
                <div class="row-left">
                  <p>Nr telefonu</p>
                </div>
                <InputText
                  id="phone-number"
                  name="phone-number-input"
                  v-model="phone"
                  placeholder="np. +48001100999"
                  class="w-300px"
                  :label="!isPhoneValid ? 'Poprawny format +48123123123' : ''"
                  :is-label-error="true"
                />
              </div>

              <div class="display-flex align-center row">
                <div class="row-left">
                  <p>Strona www</p>
                </div>
                <InputText
                  id="website-number"
                  name="website-input"
                  v-model="website"
                  placeholder="np. https://stronaplacowki.pl"
                  class="w-300px"
                  :label="!isWebValid ? 'Poprawny format https://examplepage.eu' : ''"
                  :is-label-error="true"
                />
              </div>
            </section>
            
            <section name="additional-information">
              <SectionName>Informacje dodatkowe</SectionName>

              <div class="display-flex align-center row">
                <div class="row-left">
                  <p>Czy placówka obsługuje finansowanie przez Narodowy Fundusz Zdrowia?</p>
                </div>
                <SwitchButton id="isNFZ" v-model="isNFZ" class="w-300px">
                  Nie
                  <template #right>Tak</template>
                </SwitchButton>
              </div>
            </section>

            <div class="display-flex justify-end">
              <div class="buttons display-flex align-center">
                <Button type="button" :active="true" :dark="true" @click="goBack()" class="back-button">
                  Powrót
                </Button>
                <Button type="submit" class="submit">
                  Zapisz placówkę
                </Button>
              </div>
            </div>
        </form>
      </div>
    </template>
  </BoxSection>
</template>

<script>
// eslint-disable-next-line
import { mapGetters } from 'vuex';
import BoxSection from '@/components/BoxSection';
import Button from '@/components/shared/Button';
import InputText from '@/components/shared/InputText';
import SwitchButton from '@/components/shared/SwitchButton';
import Branding from './Branding';
import SectionName from '@/components/shared/SectionName';
import Select from '@/components/shared/Select';
import InputFile from '@/components/shared/InputFile';
import SearchInput from '@/components/SearchInput';

export default {
  components: {
    BoxSection,
    Button,
    InputText,
    SwitchButton,
    Branding,
    SectionName,
    Select,
    InputFile,
    SearchInput,
  },
  data() {
    return {
      name: '',
      address: null,
      placeType: null,
      description: '',
      phone: '',
      email: '',
      website: '',
      isNFZ: true,
      services: [],
      images: [],
      place: null,
    };
  },
  async mounted() {
    this.resetAddress();
    this.getData();
  },
  computed: {
    ...mapGetters('facilitiesSearch', {
      facilitiesTypes: 'getFacilitiesTypes',
    }),
    userId() {
      return this.$store.getters['user/getUserId'];
    },
    placeId() {
      return +this.$route?.params?.id;
    },
    payload() {
      return {
        name: this.name,
        type: this.placeType?.name ?? null,
        address: this.address ? this.$refs.searchInput.buildAddress(this.address) : null,
        phone: this.phone || null,
        websiteUrl: this.website || null,
        description: this.description || null,
        nfzStatus: this.isNFZ ? 'FULL' : 'NONE',
        lat: this.address?.location.latitude,
        lon: this.address?.location.longitude,
        addedBy: this.userId ?? null,
        image: this.images[0],
        additionalImages: this.images.slice(1),
      };
    },
    isPhoneValid() {
      return this.phone?.length ? /^\+\d{2}\d{9}$/.test(this.phone) : true;
    },
    isWebValid() {
      return this.website?.length ? /^(https?:\/\/)?[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(this.website) : true;
    },
  },
  methods: {
    async getData() {
      try {
        await this.$store.dispatch('facilitiesSearch/getFacilitiesTypes');
        if (this.placeId) {
          this.place = await this.$store.dispatch('facility/fetchDetails', this.placeId);
        }
      } catch (e) {
        this.$notify({ text: 'Wystąpił błąd pobierania danych. Spróbuj odświeżyć stronę.', type: 'error' });
      }

      if (this.place) {
        this.assign(this.place);
      }
    },
    async validate() {
      return new Promise((resolve) => {
        [{
          name: 'Nazwa',
          val: this.payload.name,
        }, {
          name: 'Typ placówki',
          val: this.payload.type,
        }, {
          name: 'Adres',
          val: this.payload.address,
        }].forEach(({ val, name }) => {
          if (val === null || !val.length) {
            throw new Error(`Uzupełnij pole ${name}`);
          }
        });

        if (!this.isPhoneValid) {
          throw new Error('Podaj poprawny format numeru telefonu.');
        }

        if (!this.isWebValid) {
          throw new Error('Podaj poprawny format adresu strony internetowej.');
        }
        resolve(true);
      });
    },
    goBack() {
      // eslint-disable-next-line
      if (confirm("Czy na pewno chcesz powrócić? Utracisz niezapisane dane.")) {
        this.$router.go();
      }
    },
    handleToggleAddress(res) {
      this.address = res;
    },
    toggleImages(ev) {
      if (ev.target.files.length > 1) {
        this.images = [...ev.target.files];
        return;
      }

      const file = ev.target.files[0];
      const fileIndex = this.images.findIndex(({ lastModified }) => lastModified === file.lastModified);

      if (fileIndex > -1) {
        this.images.splice(fileIndex, 1, file);
      } else {
        this.images.push(file);
      }
    },
    deleteImage(image) {
      const fileIndex = this.images.findIndex(({ lastModified }) => lastModified === image.lastModified);
      this.images.splice(fileIndex, 1);
    },
    resetAddress() {
      this.$refs.searchInput.clearAddresses();
    },
    async save() {
      let res = null;
      try {
        await this.validate();
      } catch (error) {
        this.$notify({ text: error.message, type: 'error' });
        return;
      }

      try {
        if (this.placeId) {
          res = await this.$store.dispatch('myFacility/edit', { facilityId: this.placeId, payload: this.payload });
        } else {
          res = await this.$store.dispatch('myFacility/add', this.payload);
        }
      } catch (e) {
        this.$notify({ text: 'Wystąpił problem przy zapisywaniu placówki.', type: 'error' });
        console.error(e, e.message);
      }

      if (this.placeId) {
        this.$notify({ text: 'Placówka edytowana pomyślnie.', type: 'success' });
        this.images = [];
        this.getData();
      } else if (res?.id) {
        this.$router.push(`/place/edit/${res.id}`);
        this.$notify({ text: 'Placówka zapisana pomyślnie. Teraz możesz ją edytować.', type: 'success' });
      } 
    },
    assign(place) {
      this.name = place.name;
      this.placeType = this.facilitiesTypes.find((el) => el.name === place.type);
      this.address = {
        address: {
          road: place.address,
        },
        location: place.location,
      };
      this.description = place.description;
      this.isNFZ = place.nfzStatus === 'FULL'; 
      this.phone = place.phone;
      this.website = place.websiteUrl;
      
      this.images.push(this.createFile(place.imageUrl));

      place.images.forEach(({ url }) => {
        this.images.push(this.createFile(url));
      });
  
      this.$refs.searchInput.setValue(place.address);
    },

    createFile(url) {
      const mainImageName = `${url.split('/').at(-1)
        .split('--')[0]}.${url.split('.').at(-1)}`;
      const mainType = `image/${mainImageName.split('.').at(-1)}`;
      const lastModifiedMain = url.split('/').at(-1)
        .split('--').at(-1)
        .split('.')[0];
      const blobMain = new Blob([{
        name: mainImageName,
        lastModified: lastModifiedMain,
      }], { type: `image/${mainImageName.split('.').at(-1)}` });
      
      return new File([blobMain], url, { type: mainType });
    },
  },
};
</script>

<style lang="scss">
@media screen and (max-width: $desktop_breakpoint) {
  #info-box-contact {
    margin-top: 0;
  }
}
</style>
<style lang="scss" scoped>
.justify-start {
  justify-content: flex-start !important;
}

.buttons {
  width: 320px;
  gap: 20px;
  margin-top: 3em;

  .back-button {
    width: 35%;
  }

  .submit {
    width: 65%;
  }
}

.reset-button {
  height: 45px;
  margin-left: 10px;
}

.services-buttons {
  gap: 15px;
  flex-wrap: wrap;
}

code {
  background: rgba(221, 221, 221, 0.6);
  font-family: 'Lucida Console', 'Courier New', monospace;
  font-size: 15px;
  padding: 3px 5px;
}

.main-container.wider {
  max-width: 35% !important;
  max-height: unset !important;
  
  @media screen and (max-width: $desktop_breakpoint) {
    max-width: unset !important;
  }
}

.add-place-outer {
  margin: 5em 2em 3em 2em !important;

  @media screen and (max-width: $desktop_breakpoint) {
    background: #FDFDFD;
    box-shadow: unset;
    padding-top: 20px;
  }
  
  .info-box-title {
    height: 55px;
    line-height: 55px;
    text-indent: 2rem;
    font-size: 1.2rem;
  }

  .info-box {
    padding: 0 2em 2em 2em;
    background: #fff;

    section:not(:first-of-type) {
      margin-top: 3em;
    }

    .row {
      margin-top: 2em;

      .row-left {
        width: 25%;
      }
    }

    .add-place-container-inner {
      box-sizing: border-box;
      margin: auto;
      display: flex;
      flex-direction: column;
      margin-top: 30px;
      padding-left: 3em;

      .section {
        width: 50%;
        box-sizing: border-box;
        padding-right: 20px;
        display: flex;
        flex-wrap: wrap;

        .checkbox-label.mobile {
          display: none;
        }

        .text-field-outer, .buttons {
          width: 100%;
          padding-right: 0;
        }
      }
    }

    @media screen and (max-width: $desktop_breakpoint) {
      background: transparent;
      padding: 0 2rem !important;

      #info-box-contact {
        margin-top: 0;
      }

      .add-place-container-inner {
        padding-left: 0;
        padding-right: 0;
        margin: auto;
        width: 100%;

        .section {
          width: 100%;
          padding: 0;

          .checkbox-label {
            display: none;

            &.mobile {
              display: block;
            }
          }
        }
      }
    }

    .text-field-outer {
      margin-top: 20px;
    }
  }

  .relative-icon {
    position: relative;
    top: 3px;
  }

  .margin-top {
    margin-top: 20px;
  }
}
</style>
