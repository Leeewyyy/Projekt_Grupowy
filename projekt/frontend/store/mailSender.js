/* eslint-disable-next-line */
export const actions = {
  async sendMail({}, { fromEmail, subject, text }) {
    return await this.$axios.$post('/api/send-message', {}, { params: { fromEmail, subject, text } });
  },
};
