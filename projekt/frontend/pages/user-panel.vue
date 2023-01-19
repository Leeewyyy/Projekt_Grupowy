<template>
  <AppPage name="user-panel" class="gray-background">
    <div class="user-panel_container">
      <div class="container_column">
        <AvatarBox
          :user="user"
          class="column_avatar-box"
          @avatarUpdated="onAvatarUpdated"
        />
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
          :selectable="false"
          :closable="false"
        />
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

export default {
  components: {
    AppPage,
    MyReviews,
    AvatarBox,
    PlaceList,
    AccountData,
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
  },

  async asyncData({ store, error }) {
    const currentUser = store.getters['user/getUser'];

    // User not logged in - redirect to error page
    if (!currentUser) {
      return error({ statusCode: 401 });
    }

    const user = { ...currentUser };

    return {
      user,
    };
  },

  methods: {
    onAvatarUpdated(newUrl) {
      this.user.imageUrl = newUrl;
    },
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
