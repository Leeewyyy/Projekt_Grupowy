export const state = () => ({
  isExpanded: true,
});

export const getters = {
  getExpanded(state) {
    return state.isExpanded;
  },
};

export const mutations = {
  setExpanded(state, value) {
    state.isExpanded = value;
  },
};

export const actions = {
  setExpanded({ commit }, value) {
    commit('setExpanded', value);
  },
};
