<template>
  <IconToggleButton
    icon-name="edit"
    :size="24"
    variant="dark"
    @click="$router.push(`/place/edit/${placeId}`)"
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
};
</script>
