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
    // eslint-disable-next-line no-use-before-define
    const formData = new FormData();
    Object.entries(payload).forEach(([name, value]) => {
      if (value === undefined || value === null) {
        return;
      }

      if (name === 'additionalImages') {
        value.forEach((image) => formData.append(name, image));
      } else {
        formData.append(name, value);
      }
    });
    return await this.$axios.$post('/api/medical-facilities', formData);
  },

  async edit({}, { facilityId, payload }) {
    // eslint-disable-next-line no-use-before-define
    const formData = new FormData();
    Object.entries(payload).forEach(([name, value]) => {
      if (value === undefined || value === null) {
        return;
      }

      if (name === 'additionalImages') {
        value.forEach((image) => formData.append(name, image));
      } else {
        formData.append(name, value);
      }
    });
    await this.$axios.$patch(`/api/medical-facilities/${facilityId}`, formData);
  },

  async delete({}, facilityId) {
    await this.$axios.$delete(`/api/medical-facilities/${facilityId}`);
  },
};
