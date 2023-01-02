<template>
  <AppPage name="userPanel">
    <div class="panel-container">
      <div class="panel">
        <div class="panel-element">
          <AvatarBox />
          <MyReviews class="my-3" />
        </div>
        <AccountData class="panel-element" />
        <PlaceList
          class="panel-element-wider"
          customTitle="Ulubione placówki"
          :places="favouritePlaces"
          :selectable="false"
          :closable="false"
          @updateList="getPlaces()"
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
import FavoritePlacesTestList from '~/assets/favorite-places.js';
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

  data() {
    return {
      favouritePlaces: FavoritePlacesTestList,
    };
  },
  methods: {
    getPlaces() {
      // some ajax get
      // this.favouritePlaces = new Array()
    },
  },
};
</script>

<style lang="scss" scoped>
.panel-container {
  padding: 30px;

  .panel {
    width: 100%;
    box-sizing: border-box;

    .panel-element {
      width: 28%;
      display: block;
      float: left;
      margin-right: 3.5%;
    }

    .panel-element-wider {
      max-width: 35%;
      display: block;
      float: left;
    }
  }
}

.bg-white {
  background: #fff;
}

.my-3 {
  margin-top: 30px;
  margin-bottom: 30px;
}
</style>
