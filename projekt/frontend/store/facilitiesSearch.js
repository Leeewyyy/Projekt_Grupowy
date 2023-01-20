export const state = () => ({
  possibleAddresses: [],
});

export const getters = {
  getPossibleAddresses(state) {
    return state.possibleAddresses;
  },
};
  
export const mutations = {
  setPossibleAddresses(state, list) {
    state.possibleAddresses = list;
  },
};

export const actions = {
  async getAddresses({ commit }, inputVal) {
    const results = inputVal.length ? await this.$axios.$get(`/api/ajax/locations?address=${inputVal}&limit=2`) : [];
    commit('setPossibleAddresses', results);
  },
};
