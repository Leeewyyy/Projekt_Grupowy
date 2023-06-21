export const state = () => ({
  possibleAddresses: [],
  facilities: null,
  facilitiesTypes: [],
  specialistsTypes: [],
});

export const getters = {
  getPossibleAddresses(state) {
    return state.possibleAddresses;
  },
  getFacilities(state) {
    return state.facilities;
  },
  getFacilitiesTypes(state) {
    return state.facilitiesTypes;
  },
  getSpecialistsTypes(state) {
    return state.specialistsTypes;
  },
};
  
export const mutations = {
  setPossibleAddresses(state, list) {
    state.possibleAddresses = list;
  },
  setFacilities(state, list) {
    state.facilities = list;
  },
  setFacilitiesTypes(state, list) {
    state.facilitiesTypes = list;
  },
  setSpecialistsTypes(state, list) {
    state.specialistsTypes = list;
  },
};

export const actions = {
  async getAddresses({ commit }, address) {
    if (!address?.length) return null;

    const results = await this.$axios.$get('/api/ajax/locations', {
      params: { address },
    }) ?? [];

    const filteredResults = results?.filter((result) => {
      const requiredKeys = ['city', 'road', 'neighbourhood', 'postcode'];
      return requiredKeys.some((key) => !!result.address[key]);
    }) ?? [];

    commit('setPossibleAddresses', filteredResults);
    return filteredResults;
  },

  async searchFacilities({ commit }, params) {
    if (!params.distance) params.distance = 1000;
    if (params.isNFZ) params.nfzStatus = 'FULL,PARTIAL';
    const results = await this.$axios.$get('/api/medical-facilities', { params }) ?? [];
    commit('setFacilities', results);
  },

  async getFacilitiesTypes({ commit }) {
    const results = await this.$axios.$get('/api/medical-facilities/types') ?? [];
    commit('setFacilitiesTypes', results.map((name, id) => ({ name, id })));
  },

  async getSpecialistsTypes({ commit }) {
    const results = await this.$axios.$get('/api/specialists/types') ?? [];
    commit('setSpecialistsTypes', results.map((name, id) => ({ name, id })));
  },

  async reset({ commit }) {
    commit('setFacilities', null);
  },
};
