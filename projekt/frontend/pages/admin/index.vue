<template>
  <AppPage class="Admin" name="admin-navigation">
    <BoxSection class="Admin_box">
      <template #header>
        Admin: Nawigacja
      </template>
      <template #body>
        <nav class="Admin_nav">
          <ul class="Admin_nav-list">
            <li
              v-for="tab in tabs"
              :key="tab.to"
              class="Admin_nav-item"
            >
              <nuxt-link
                class="Admin_nav-link"
                :to="tab.to"
              >
                {{ tab.name }}
              </nuxt-link>
            </li>
          </ul>
        </nav>
      </template>
    </BoxSection>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import BoxSection from '@/components/BoxSection';
import InputText from '@/components/shared/InputText';
import Table from '@/components/shared/Table';

export default {
  components: {
    AppPage,
    BoxSection,
    InputText,
    Table,
  },

  head() {
    return {
      title: 'Panel admina',
    };
  },

  data() {
    return {
      tabs: [
        {
          name: 'Zarządzanie użytkownikami',
          to: '/admin/users',
        },
        {
          name: 'Zarządzanie placówkami',
          to: '/admin/facilities',
        },
      ],
    };
  },
};
</script>

<style lang="scss">
.Admin {
  padding: 2rem;
  background: #fafafa;

  .Admin_box {
    padding: 1rem;
  }

  .Admin_nav {
    margin-top: 1rem;
  }

  .Admin_nav-list {
    display: flex;
    flex-direction: row;
    align-items: center;
    flex-wrap: wrap;
    gap: 1rem;
    list-style-type: none;
  }

  .Admin_nav-link {
    display: block;
    padding: 1rem 2rem;
    width: max-content;
    color: rgb(var(--color-white)) !important;
    background: $dark-grey;
    border-radius: 1rem;
  }

  @media screen and (min-width: $tablet_breakpoint) {

  }

  @media screen and (min-width: $desktop_breakpoint) {
    padding: 6rem;
  }
}
</style>
