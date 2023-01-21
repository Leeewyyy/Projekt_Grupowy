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
  async login({ commit }, payload) {
    const userData = await this.$axios.$post('/api/users/login', payload);
    if (userData) commit('setUser', userData);
  },

  async logout({ commit }) {
    await this.$cookies.remove('userId');
    commit('setUser', null);
  },

  async register({}, payload) {
    return this.$axios.$post('/api/users/register', payload);
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

  async getData({ commit }, userId) {
    const user = await this.$axios.$get(`/api/users/${userId}`);
    commit('setUser', user);
  },

  async changeUserData({ dispatch }, { data, userId }) {
    await this.$axios.patch(`/api/users/edit/${userId}`, data);
    dispatch('getData', userId);
  },
};
