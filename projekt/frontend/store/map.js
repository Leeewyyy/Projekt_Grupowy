export const state = () => ({
  key: 0,

  coords: {
    latitude: 54.3739,
    longitude: 18.6214,
  },

  customCoords: false,
  isMapOpened: false,
  isMapMobile: false,
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

  isMapOpened(state) {
    return state.isMapOpened;
  },

  isMapMobile(state) {
    return state.isMapMobile;
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

  setMapState(state, value) {
    state.isMapOpened = value;
  },

  setisMapMobile(state, value) {
    state.isMapMobile = value;
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

  setMapState({ commit }, value) {
    commit('setMapState', value);
  },

  setisMapMobile({ commit }, value) {
    commit('setisMapMobile', value);
  },
};
