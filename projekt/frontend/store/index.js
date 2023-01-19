// eslint-disable-next-line
export const actions = {
  // Fetch all facilities & favourite facilities
  // because they are being used globally on the site
  async nuxtServerInit({ rootGetters, dispatch }) {
    try {
      await dispatch('user/temporaryFakeLogin');
    } catch (error) {
      console.error('FakeLoginError!');
    }
    
    const userId = rootGetters['user/getUserId'];

    try {
      await dispatch('facility/fetchAll');
    } catch (error) {
      console.error('Error occured while fetching facilities: ', error);
    }

    try {
      // User is logged in - fetch favourite places
      if (userId) await dispatch('favouriteFacility/fetchAll', userId);
    } catch (error) {
      console.error('Error occured while fetching favourite facilities: ', error);
    }
  },
};
