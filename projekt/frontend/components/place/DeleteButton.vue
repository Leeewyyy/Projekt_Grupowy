<template>
  <IconToggleButton
    icon-name="delete"
    :size="24"
    variant="dark"
    data-tid="delete-place-button"
    @click="deletePlace"
  />
</template>

<script>
import IconToggleButton from '@/components/shared/IconToggleButton';

export default {
  components: {
    IconToggleButton,
  },

  props: {
    placeId: {
      type: Number,
      required: true,
    },
  },
  methods: {
    async deletePlace() {
      try {
        await this.$store.dispatch('myFacility/delete', this.placeId);
      } catch (e) {
        this.$notify({ text: 'Wystąpił błąd przy usuwaniu placówki', type: 'error' });
      }

      //eslint-disable-next-line
      if (confirm('Czy na pewno chcesz usunąć swoją placówkę?')) {
        this.$notify({ text: 'Placówka usunięta pomyślnie!', type: 'success' });
        this.$emit('placeDeleted');
      }
    },
  },
};
</script>
