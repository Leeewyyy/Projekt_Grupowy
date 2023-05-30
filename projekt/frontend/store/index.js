// eslint-disable-next-line
export const actions = {
  // Fetch all facilities & favourite facilities
  // because they are being used globally on the site
  async nuxtServerInit({ dispatch, commit }) {
    let userId = null;

    try {
      userId = await dispatch('cookie/getCookie', 'userId');

      if (userId) {
        const data = await dispatch('user/getData', userId);

        if (data) {
          commit('cookie/setCookie', { name: 'userId', value: userId });
          this.$notify({ text: 'Przywrócono dane zalogowanego użytkownika', type: 'success' });
        }
      }
    } catch (error) {
      console.error('Wystąpił problem podczas logowania, error: ', error);
    }

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
