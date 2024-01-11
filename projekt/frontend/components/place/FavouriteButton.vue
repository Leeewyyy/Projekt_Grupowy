<template>
  <IconToggleButton
    :icon-name="isFavorite ? 'favorite' : 'favorite_border'"
    :size="24"
    variant="dark"
    class="favourite"
    data-tid="toggle-favourite"
    @click="toggleFavorite"
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

  computed: {
    userId() {
      return this.$store.getters['user/getUserId'];
    },

    isFavorite() {
      return !!this.$store.getters['favouriteFacility/getFacilityById'](this.placeId);
    },
  },

  methods: {
    async toggleFavorite() {
      if (!this.userId) {
        this.$notify({ text: 'Nie jesteś zalogowany!', type: 'error' });
        return;
      }

      const payload = {
        userId: this.userId,
        facilityId: this.placeId,
      };

      const action = this.isFavorite
        ? 'remove'
        : 'add';
      
      const message = this.isFavorite
        ? 'Usunięto placówkę z listy ulubionych.'
        : 'Dodano placówkę do listy ulubionych.';

      try {
        await this.$store.dispatch(`favouriteFacility/${action}`, payload);
        this.$notify({ text: message, type: 'success' });
      } catch (error) {
        this.$notify({ text: 'Wystąpił błąd przy dodawaniu placówki do ulubionych!', type: 'error' });
        console.error(error);
      }
    },
  },
};
</script>
