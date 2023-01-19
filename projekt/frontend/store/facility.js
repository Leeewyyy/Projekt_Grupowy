export const state = () => ({
  facilities: [],
});

export const getters = {
  getFacilities(state) {
    return state.facilities;
  },
};
  
export const mutations = {
  setFacilities(state, list) {
    state.facilities = list;
  },
};

export const actions = {
  async fetchAll({ commit }) {
    const facilities = await this.$axios.$get('/api/medical-facilities');
    commit('setFacilities', facilities);
    return facilities;
  },
  
  async fetchDetails({ }, facilityId) {
    return await this.$axios.$get(`/api/medical-facilities/${facilityId}`);
  },
};
