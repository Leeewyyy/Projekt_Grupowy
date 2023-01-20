export const state = () => ({
  possibleAddresses: [],
  facilities: [],
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
  async getAddresses({ commit }, inputVal) {
    const results = inputVal.length ? await this.$axios.$get(`/api/ajax/locations?address=${inputVal}`) : [];
    commit('setPossibleAddresses', results);
  },

  /* eslint-disable-next-line */
  async searchFacilities({ commit }, { doctor, isNFZ, maxDistance, placeType, longitude, latitude }) {
    const params = {
      type: placeType?.name || null,
      isNFZ: isNFZ ?? null,
      specialization: doctor?.name || null,
      latitude: latitude ?? null,
      longitude: longitude ?? null,
      distance: maxDistance?.val ?? null,
    };

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
};
