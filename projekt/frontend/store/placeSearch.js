export const state = () => ({
  step: 0,
  placeholder: '',
  specialization: null,
  type: null,
  distance: 50,
  isNFZ: true,
  latitude: null,
  longitude: null,
  tmpPlaceholder: '',
});

export const getters = {
  getStep(state) {
    return state.step;
  },
  
  getFormState(state) {
    return {
      specialization: state.specialization,
      type: state.type,
      distance: state.distance,
      isNFZ: state.isNFZ,
      latitude: state.latitude,
      longitude: state.longitude,
    };
  },

  getPlaceholder(state) {
    return state.tmpPlaceholder;
  },
};

export const mutations = {
  setStep(state, value) {
    state.step = value;
  },

  setPlaceholder(state, value) {
    state.tmpPlaceholder = value;
  },

  setFormState(state, { key, value }) {
    state[key] = value;
  },
};

export const actions = {
  setStep({ commit }, value) {
    commit('setStep', value);
  },

  setFormState({ commit }, { key, value }) {
    commit('setFormState', { key, value });
  },

  setPlaceholder({ commit }, value) {
    commit('setPlaceholder', value);
  },

  resetFormState({ commit }) {
    commit('setFormState', { key: 'specialization', value: null });
    commit('setFormState', { key: 'type', value: null });
    commit('setFormState', { key: 'distance', value: 50 });
    commit('setFormState', { key: 'nfzStatus', value: true });
    commit('setFormState', { key: 'latitude', value: null });
    commit('setFormState', { key: 'longitude', value: null });
  },
};
