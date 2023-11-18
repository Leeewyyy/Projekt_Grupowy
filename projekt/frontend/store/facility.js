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

  deleteFacilityById(state, facilityId) {
    const idx = state.facilities.findIndex((facility) => facility.id === facilityId);
    if (idx > -1) state.facilities.splice(idx, 1);
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

  async deleteFacilityById({ commit }, facilityId) {
    commit('deleteFacilityById', facilityId);
  },
};
