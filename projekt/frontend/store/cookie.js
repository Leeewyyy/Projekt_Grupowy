export const mutations = {
  setCookie(state, { name, value }) {
    this.$cookies.set(name, value, { maxAge: 60 * 60 * 24 });
  },
};

export const actions = {
  async getCookie({}, name) {
    return await this.$cookies.get(name);
  },

  async removeCookie({}, name) {
    return await this.$cookies.remove(name);
  },
};
