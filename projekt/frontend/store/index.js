// eslint-disable-next-line
export const actions = {
  // Fetch all facilities & favourite facilities
  // because they are being used globally on the site

  async nuxtServerInit({ rootGetters, dispatch, getters }) {
    try {
      dispatch('cookie/getCookie', 'userId');
      const userId = getters['cookie/getUserIdCookie'];
      if (userId) {
        const data = await dispatch('user/getData', userId);
        if (data) this.$notify({ text: 'Przywrócono dane zalogowanego użytkownika', type: 'success' });
      }
    } catch (error) {
      console.error('LoginError!');
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
