// eslint-disable-next-line
export const actions = {
  async addOpinion({ }, { userId, facilityId, ...payload }) {
    return await this.$axios.$post(`/api/opinions/add/${userId}/${facilityId}`, payload);
  },
};
