export const state = () => ({
  query: null,
});

export const getters = {
  getQuery(state) {
    return state.query;
  },
};

export const mutations = {
  setQuery(state, query) {
    state.query = query;
  },
};

export const actions = {
  setQuery({ commit }, query) {
    commit('setQuery', query);
  },
};
