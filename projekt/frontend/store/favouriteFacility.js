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
    const facilities = await this.$axios.$get(`/api/users/${userId}/favorite-facilities`);
    commit('setFacilities', facilities);
    return facilities;
  },

  async add({ dispatch }, { userId, facilityId }) {
    const payload = { facilitiesId: [facilityId] };
    await this.$axios.$post(`/api/users/${userId}/add-favourite`, payload);
    await dispatch('fetchAll', userId);
  },
  
  async remove({ commit }, { userId, facilityId }) {
    const payload = { facilitiesId: [facilityId] };
    await this.$axios.$post(`/api/users/${userId}/remove-favourite`, payload);
    commit('removeById', facilityId);
  },
};
