<template>
  <BoxSection class="add-place-outer">
    <template #header>
      <div class="header_logo justify-start">
        <Branding
          id="addPlaceHeader"
          :with-image="false"
          :align-left="true"
          custom-text="Dodaj placówkę"
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

              <div class="display-flex align-center row">
                <div class="row-left">
                  <p>Adres</p>
                </div>
                <div class="display-flex align-center">
                  <InputText
                    id="address-main"
                    name="address-street-input"
                    label="ulica i numer"
                    placeholder="np. Spacerowa 15"
                    v-model="address.main"
                    class="w-300px"
                  />
                  <InputText
                    id="address-city"
                    name="address-city-input"
                    label="miasto"
                    placeholder="np. Gdańsk"
                    v-model="address.city"
                    class="w-150px ml-1"
                  />
                </div>
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
                  placeholder="np. +48 001 100 999"
                  class="w-300px"
                />
              </div>

              <div class="display-flex align-center row">
                <div class="row-left">
                  <p>E-mail</p>
                </div>
                <InputText
                  id="email"
                  name="email-input"
                  placeholder="np. example@example.pl"
                  v-model="email"
                  class="w-300px"
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

              <div class="display-flex align-center row">
                <div class="row-left">
                  <p class="mr-1">Zakres oferowanych specjalistów przez placówkę</p>
                </div>
                  <div class="display-flex align-center justify-center flex-wrap services-buttons">
                     <Button 
                      v-for="service in services"
                      :key="service.id"
                      type="button"
                      :active="service.active"
                      @click="service.active = !service.active"
                    >
                      {{ service.name }}
                    </Button>
                  </div>
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
  },
  data() {
    return {
      name: '',
      address: {
        main: '',
        city: '',
      },
      placeType: null,
      description: '',
      phone: '',
      email: '',
      website: '',
      isNFZ: true,
      services: [],
      images: [],
      facility: null,
    };
  },
  async mounted() {
    try {
      await this.$store.dispatch('facilitiesSearch/getFacilitiesTypes');
      await this.$store.dispatch('facilitiesSearch/getSpecialistsTypes');

      //this.facility = await this.$store.dispatch('facility/fetchDetails', placeid);
    } catch (e) {
      this.$notify({ text: 'Wystąpił błąd pobierania danych. Spróbuj odświeżyć stronę.', type: 'error' });
    }

    this.services = this.specialistsTypes.map(({ id, name }) => ({
      id,
      name,
      active: false,
    }));
  },
  computed: {
    ...mapGetters('facilitiesSearch', {
      facilitiesTypes: 'getFacilitiesTypes',
      specialistsTypes: 'getSpecialistsTypes',
    }),
    servicesSelected() {
      return this.services.filter((service) => service.active);
    },
    payload() {
      return {
        name: this.name,
        type: this.placeType?.type || null,
        address: `${this.address.main}, ${this.address.city}`,
        images: this.images,
        phone: this.phone,
        websiteUrl: this.websiteUrl,
        description: this.description,
        nfzStatus: this.isNFZ ? 'FULL' : 'NONE',
        specialists: this.services,
      };
    },
  },
  methods: {
    goBack() {
      // eslint-disable-next-line
      if (confirm("Czy na pewno chcesz powrócić? Utracisz niezapisane dane.")) {
        this.$router.go(-1);
      }
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
    async save() {
      try {
        await this.$store.dispatch('myFacility/add', this.payload);
      } catch (e) {
        this.$notify({ text: 'Wystąpił problem przy zapisywaniu placówki.', type: 'error' });
      }
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
