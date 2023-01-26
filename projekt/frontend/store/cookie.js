export const state = () => ({
  /* names of the cookies */
  showWelcomeBox: null,
  userId: null,
});

export const getters = {
  getShowWelcomeBoxCookie(state) {
    return state.showWelcomeBox;
  },
  getUserIdCookie(state) {
    return state.userId;
  },
};

export const mutations = {
  setCookie(state, { name, value, time }) {
    this.$cookies.set(name, value, time ? { maxAge: 60 * 60 * time } : {});
    state[name] = value;
  },
};

export const actions = {
  async getCookie({ commit }, name) {
    const value = await this.$cookies.get(name) ?? null;
    if (value) commit('setCookie', { name, value, time: (name === 'userId' ? 24 : null) });
  },

  async removeCookie({}, name) {
    return await this.$cookies.remove(name);
  },
};
