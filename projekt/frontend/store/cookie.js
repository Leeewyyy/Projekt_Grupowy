const cookiesTimes = {
  showWelcomeBox: 24 * 356,
  userId: 24,
};

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
  setCookie(state, { name, value }) {
    this.$cookies.set(
      name, 
      value, 
      cookiesTimes[name] ? { maxAge: 60 * 60 * cookiesTimes[name] } : {},
    );
    state[name] = value;
  },

  setCookieState(state, { name, value }) {
    state[name] = value;
  },
};

export const actions = {
  async getCookie({ commit }, name) {
    const value = await this.$cookies.get(name) ?? null;
    if (value) commit('setCookieState', { name, value });
    return value;
  },

  async removeCookie({}, name) {
    return await this.$cookies.remove(name);
  },
};
