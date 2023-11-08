<template>
  <AppPage class="Users" name="admin-users">
    <BoxSection class="Users_box">
      <template #header>
        Admin: Zarządzanie użytkownikami
      </template>
      <template #body>
        <div class="Users_box-body">
          <InputText
            id="userSearch"
            name="userSearch"
            v-model="searchTemp"
            placeholder="Wyszukaj użytkownika..."
            @input="onSearch"
          />
        </div>
      </template>
    </BoxSection>
    <BoxSection class="Users_box Users_results">
      <template #header>
        <span v-if="searchValue">
          Wyniki wyszukiwania: "{{ searchValue }}"
        </span>
        <span v-else>
          Lista użytkowników
        </span>
      </template>
      <template #body>
        <div class="Users_box-body">
          <Table
            :columns="columns"
            :rows="filteredRows"
          >
            <template #createdAt="{ value }">
              {{ new Date(value).toLocaleString('pl') }}
            </template>
          </Table>
        </div>
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
      title: 'Zarządzanie użytkownikami',
    };
  },

  computed: {
    filteredRows() {
      return this.rows.filter((row) => {
        if (!this.searchValue) return true;
        if (row.fullName.toLowerCase().includes(this.searchValue)) return true;
        if (row.email.toLowerCase().includes(this.searchValue)) return true;
        return false;
      });
    },
  },

  data() {
    return {
      columns: {
        id: {
          name: 'ID',
        },
        email: {
          name: 'Adres e-mail',
        },
        fullName: {
          name: 'Imię i nazwisko',
        },
        createdAt: {
          name: 'Data rejestracji',
        },
      },

      // Temporary data
      rows: [
        {
          id: 1,
          email: 'alan@example.com',
          fullName: 'Alan',
          createdAt: new Date(),
        },
        {
          id: 2,
          email: 'jakub@example.com',
          fullName: 'Jakub',
          createdAt: new Date(),
        },
        {
          id: 3,
          email: 'adam@example.com',
          fullName: 'Adam',
          createdAt: new Date(),
        },
        {
          id: 4,
          email: 'mateusz@example.com',
          fullName: 'Mateusz',
          createdAt: new Date(),
        },
      ],

      searchTemp: null,
      searchValue: null,
      searchTimeout: null,
    };
  },

  async fetch() {
    // Fetch user list
  },

  methods: {
    onSearch() {
      // Debounce search
      if (this.searchTimeout) clearTimeout(this.searchTimeout);

      this.searchTimeout = setTimeout(() => {
        this.searchValue = this.searchTemp;
      }, 1000);
    },
  },
};
</script>

<style lang="scss">
.Users {
  padding: 2rem;
  background: #fafafa;

  .Users_box {
    padding: 1rem;
  }

  .Users_box-body {
    margin-top: 1rem;
  }

  .Users_results {
    margin-top: 2rem;
  }

  @media screen and (min-width: $tablet_breakpoint) {

  }

  @media screen and (min-width: $desktop_breakpoint) {
    padding: 6rem;
  }
}
</style>
