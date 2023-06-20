export const state = () => ({
  step: 0,
});

export const getters = {
  getStep(state) {
    return state.step;
  },
};

export const mutations = {
  setStep(state, value) {
    state.step = value;
  },
};

export const actions = {
  setStep({ commit }, value) {
    commit('setStep', value);
  },
};
