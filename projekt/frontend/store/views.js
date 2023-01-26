export const state = () => ({
  view: 'placeSearch',
});

export const getters = {
  getView(state) {
    return state.view;
  },
};

export const mutations = {
  setView(state, view) {
    state.view = view;
  },
};
