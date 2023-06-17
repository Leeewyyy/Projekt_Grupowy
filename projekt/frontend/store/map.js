export const state = () => ({
  key: 0,

  coords: {
    latitude: 54.3739,
    longitude: 18.6214,
  },

  customCoords: false,
});

export const getters = {
  getKey(state) {
    return state.key;
  },

  getCoords(state) {
    return state.coords;
  },

  getCustomCoords(state) {
    return state.customCoords;
  },
};

export const mutations = {
  incrementKey(state) {
    state.key++;
  },

  setCoords(state, coords) {
    state.coords = coords;
  },

  setCustomCoords(state, value) {
    state.customCoords = value;
  },
};

export const actions = {
  incrementKey({ commit }) {
    commit('incrementKey');
  },

  setCoords({ commit }, coords) {
    commit('setCoords', coords);
  },

  setCustomCoords({ commit }, value) {
    commit('setCustomCoords', value);
  },
};
