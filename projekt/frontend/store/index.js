// eslint-disable-next-line
export const actions = {
  // Fetch all facilities & favourite facilities
  // because they are being used globally on the site

  async nuxtServerInit({ rootGetters, dispatch }) {
    try {
      const cookieUserId = await dispatch('cookie/getCookie', 'userId');
      if (cookieUserId) {
        const data = await dispatch('user/getData', cookieUserId);
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
