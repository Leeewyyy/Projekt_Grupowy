export const state = () => ({
  facilities: [],
});

export const getters = {
  getFacilities(state) {
    return state.facilities;
  },

  getFacilityById(state) {
    return (id) => state.facilities.find((facility) => facility.id === id);
  },
};
  
export const mutations = {
  setFacilities(state, ids) {
    state.facilities = ids;
  },

  removeById(state, id) {
    const idx = state.facilities.findIndex((facility) => facility.id === id);
    if (idx > -1) state.facilities.splice(idx, 1);
  },
};

export const actions = {
  async fetchAll({ commit }, userId) {
    const facilities = await this.$axios.$get(`/api/medical-facilities?addedBy=${userId}`);
    commit('setFacilities', facilities);
    return facilities;
  },

  async add({}, payload) {
    await this.$axios.$post('/api/medical-facilities', payload);
  },

  async edit({}, { facilityId, payload }) {
    await this.$axios.$patch(`/api/medical-facilities/${facilityId}`, payload);
  },

  async delete({}, facilityId) {
    await this.$axios.$delete(`/api/medical-facilities/${facilityId}`);
  },
};
