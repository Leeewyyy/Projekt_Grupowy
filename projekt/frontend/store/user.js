export const state = () => ({
  user: null,
});

export const getters = {
  getUser(state) {
    return state.user;
  },

  getUserId(state) {
    return state.user?.id;
  },

  isLoggedIn(state) {
    return !!state.user?.id;
  },
};
  
export const mutations = {
  setUser(state, user) {
    state.user = user;
  },
};

export const actions = {
  async login({}) {
    /* logowanie */
  },

  async fetchOpinions({}, userId) {
    return this.$axios.$get(`/api/users/${userId}/opinions`);
  },

  async uploadAndSetAvatar({}, { userId, avatarFile }) {
    const formData = new FormData();
    const headers = { 'content-type': 'multipart/form-data' };
    formData.append('file', avatarFile);
    await this.$axios.$post(`/api/users/${userId}/image/update`, formData, { headers });
  },
  
  // TO DO USUNIĘCIA
  // Dałem to tymczasowo abym mógł kontynuować pracę z podpinaniem
  // Wywoływane w pliku store/index.js
  async temporaryFakeLogin({ commit }) {
    const user = await this.$axios.$get('/api/users/1');
    commit('setUser', user);
  },
};
