<template>
  <BoxSection class="AdminNavigation">
    <template #header>
      <div class="AdminNavigation_header">
        Panel administratora
      </div>
    </template>
    <template #body>
      <SectionName class="AdminNavigation_section-name">
        Nawigacja
      </SectionName>
      <nav class="AdminNavigation_nav">
        <ul class="AdminNavigation_nav-list">
          <li
            v-for="tab in tabs"
            :key="tab.to"
            class="AdminNavigation_nav-item"
          >
            <nuxt-link
              class="AdminNavigation_nav-link"
              :class="{ 'nav-link--active': $route.fullPath === tab.to }"
              :to="tab.to"
            >
              {{ tab.name }}
            </nuxt-link>
          </li>
        </ul>
      </nav>
    </template>
  </BoxSection>
</template>

<script>
import BoxSection from '@/components/BoxSection';
import SectionName from '@/components/shared/SectionName';

export default {
  components: {
    BoxSection,
    SectionName,
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
.AdminNavigation {
  padding: 1.5rem 2rem;
  margin-bottom: 2rem;

  .AdminNavigation_section-name {
    margin: 2rem 0 1rem 0;
  }

  .AdminNavigation_nav-list {
    display: flex;
    flex-direction: row;
    align-items: center;
    flex-wrap: wrap;
    gap: 1rem;
    list-style-type: none;
  }

  .AdminNavigation_nav-link {
    display: block;
    padding: 1rem 0;
    width: max-content;
    font-weight: 400 !important;
    border-radius: 1rem;

    &.nav-link--active {
      font-weight: 600 !important;
      text-decoration: underline !important;
    }
  }

  @media screen and (min-width: $desktop_breakpoint) {
    .AdminNavigation_nav-list {
      gap: 2rem;
    }
  }
}
</style>
