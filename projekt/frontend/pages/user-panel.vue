<template>
  <AppPage name="user-panel" class="gray-background">
    <div class="user-panel_container">
      <div class="container_column">
        <AvatarBox class="column_avatar-box" />
        <MyReviews :user="user" class="column_reviews" />
      </div>
      <div class="container_column column--account-data">
        <AccountData />
      </div>
      <div class="container_column column--favorite-places">
        <PlaceList
          class="container_column"
          customTitle="Ulubione placówki"
          :places="favouritePlaces"
          :selectable="true"
          :closable="false"
          @onPlaceSelected="(place) => $router.push({ name: 'place-id', params: { id: place.id } })"
        />
        <PlaceList
          class="container_column"
          customTitle="Moje placówki"
          :places="myPlaces"
          :selectable="true"
          :closable="false"
          :are-own-places="true"
          @updateList="$store.dispatch('myFacility/fetchAll', userId);"
        >
          <template #header-side>
            <Button type="submit" @click.prevent="$router.push('/place/edit/0')" class="add-button">
              <span class="display-flex align-center">
                Dodaj
                <i class="material-icons">add</i>
              </span>
            </Button>
          </template>
        </PlaceList>
      </div>
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import MyReviews from '@/components/user_panel/MyReviews';
import AvatarBox from '@/components/user_panel/AvatarBox';
import PlaceList from '@/components/place/PlaceList';
import AccountData from '@/components/user_panel/AccountData';
import Button from '@/components/shared/Button';

export default {
  components: {
    AppPage,
    MyReviews,
    AvatarBox,
    PlaceList,
    AccountData,
    Button,
  },

  head() {
    return {
      title: 'Moje konto',
    };
  },

  computed: {
    favouritePlaces() {
      return this.$store.getters['favouriteFacility/getFacilities'];
    },
    myPlaces() {
      return this.$store.getters['myFacility/getFacilities'];
    },
    user() {
      return this.$store.getters['user/getUser'];
    },
    userId() {
      return this.$store.getters['user/getUserId'];
    },
  },
  async mounted() {
    if (!this.userId) {
      //eslint-disable-next-line
      confirm('Brak dostępu. Zaloguj się.');
      this.$router.push('/login');
    } else {
      try {
        await this.$store.dispatch('myFacility/fetchAll', this.userId);
        await this.$store.dispatch('favouriteFacility/fetchAll', this.userId);
      } catch (e) {
        this.$notify({ text: 'Błąd pobierania placówek', type: 'error' });
      }
    }
  },
};
</script>

<style lang="scss">
.AppPage--user-panel {
  display: flex;
  flex-direction: row;
  justify-content: center;
  background: #fafafa;

  .user-panel_container {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 2rem;

    .container_column {
      width: 100%;
      margin-bottom: 2rem;

      .column_avatar-box {
        margin-bottom: 2rem;
      }

      &:last-child {
        margin-bottom: 0;
      }
    }
  }
}

 .add-button {
    padding: 8px 12px !important;
    margin-left: 1rem !important;
  }

@media screen and (min-width: $tablet_breakpoint) {
  .AppPage--user-panel {
    .user-panel_container {
      .container_column {
        max-width: 500px;
      }
    }
  }
}

@media screen and (min-width: $desktop_breakpoint) {
  .AppPage--user-panel {
    padding-top: calc(50px + 2rem); // header padding

    .user-panel_container {
      flex-direction: row;
      align-items: flex-start;
      flex-wrap: nowrap;

      .container_column {
        margin-right: 2rem;

        &:last-child {
          margin-right: 0;
        }

        &.column--favorite-places {
          flex-shrink: 0;
        }
      }
    }
  }
}
</style>
