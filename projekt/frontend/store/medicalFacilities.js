export const state = () => ({
  medicalFacilities: [],
});

export const getters = {
  getMedicalFacilities(state) {
    return state.medicalFacilities;
  },
};
  
export const mutations = {
  setMedicalFacilities(state, list) {
    state.medicalFacilities = list;
  },
};

export const actions = {
  async fetchAll({ commit }) {
    const medicalFacilities = await this.$axios.$get('/api/medical-facilities');
    commit('setMedicalFacilities', medicalFacilities);
    return medicalFacilities;
  },
};
