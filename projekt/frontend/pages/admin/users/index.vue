<template>
  <AppPage class="Users" name="admin-users">
    <AdminNavigation />

    <BoxSection class="Users_box Users_search">
      <template #header>
        <div class="Users_header">
          Wyszukaj użytkownika
        </div>
      </template>
      <template #body>
        <div class="Users_search-content">
          <div class="Users_search-group">
            <div class="Users_search-name">
              Imię i nazwisko
            </div>
            <InputText
              id="searchName"
              class="Users_search-input"
              name="searchName"
              v-model="searchNameTemp"
              placeholder="np. Adam Kowalski"
              @input="onSearchUpdated"
            />
          </div>
          <div class="Users_search-group">
            <div class="Users_search-name">
              Adres e-mail
            </div>
            <InputText
              id="searchEmail"
              class="Users_search-input"
              name="searchEmail"
              v-model="searchEmailTemp"
              placeholder="np. adam@kowalski.pl"
              @input="onSearchUpdated"
            />
          </div>
        </div>
      </template>
    </BoxSection>
    <BoxSection class="Users_box Users_results">
      <template #header>
        <div class="Users_header">
          Lista użytkowników
        </div>
      </template>
      <template #body>
        <div class="Users_results-content">
          <Table
            :columns="columns"
            :rows="filteredUsers"
          >
            <template #imageUrl="{ value }">
              <Avatar
                size="small"
                placeholderIcon="person"
                :imageUrl="value"
              />
            </template>
            <template #email="{ row }">
              <nuxt-link
                class="Users_link"
                :to="`/admin/users/${row.id}`"
              >
                {{ row.email }}
              </nuxt-link>
            </template>
            <template #registrationDate="{ value }">
              {{ new Date(value).toLocaleString('pl') }}
            </template>
            <template #actions="{ row }">
              <ActionButton
                element="nuxt-link"
                :to="`/admin/users/${row.id}`"
                icon="visibility"
              >
                Wyświetl
              </ActionButton>
            </template>
          </Table>
        </div>
      </template>
    </BoxSection>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import AdminNavigation from '@/components/admin/AdminNavigation';
import ActionButton from '@/components/admin/ActionButton';
import BoxSection from '@/components/BoxSection';
import InputText from '@/components/shared/InputText';
import Table from '@/components/shared/Table';
import Avatar from '@/components/shared/Avatar';

export default {
  components: {
    AppPage,
    AdminNavigation,
    ActionButton,
    BoxSection,
    InputText,
    Table,
    Avatar,
  },

  head() {
    return {
      title: 'Zarządzanie użytkownikami',
    };
  },

  computed: {
    filteredUsers() {
      // eslint-disable-next-line
      return this.users.filter((row) => {
        const { fullName, email } = row;
        const nameSearch = this.searchName?.toLowerCase();
        const emailSearch = this.searchEmail?.toLowerCase();
        if (nameSearch && !fullName.toLowerCase().includes(nameSearch)) return false;
        if (emailSearch && !email.toLowerCase().includes(emailSearch)) return false;
        return true;
      });
    },
    userId() {
      return this.$store.getters['user/getUserId'];
    },
  },
  async mounted() {
    if (!this.userId) {
      //eslint-disable-next-line
      confirm('Brak dostępu. Zaloguj się.');
      this.$router.push('/');
    }
  },
  data() {
    return {
      columns: {
        id: {
          name: 'ID',
        },
        imageUrl: {
          name: '',
          inline: true,
        },
        email: {
          name: 'Adres e-mail',
        },
        fullName: {
          name: 'Imię i nazwisko',
        },
        role: {
          name: 'Rola (uprawnienia)',
        },
        registrationDate: {
          name: 'Data rejestracji',
        },
        actions: {
          name: 'Akcje',
        },
      },

      users: [],

      searchNameTemp: null,
      searchEmailTemp: null,

      searchName: null,
      searchEmail: null,
      searchTimeout: null,
    };
  },

  async fetch() {
    try {
      if (!this.userId) return;
      this.users = await this.$axios.$get('/api/users');
    } catch (error) {
      console.error(error);
    }
  },

  methods: {
    onSearchUpdated() {
      // Debounce search
      if (this.searchTimeout) clearTimeout(this.searchTimeout);

      this.searchTimeout = setTimeout(() => {
        this.searchName = this.searchNameTemp;
        this.searchEmail = this.searchEmailTemp;
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
    padding: 1.5rem 2rem;
  }

  .Users_header {
    margin-bottom: 1rem;
  }

  .Users_search-content {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  .Users_search-group {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 1rem;
  }

  .Users_search-name {
    width: max-content;
    min-width: 100px;
  }

  .Users_search-input {
    max-width: 400px;
  }

  .Users_results {
    margin-top: 2rem;
  }

  .Users_link {
    font-weight: 400 !important;
    text-decoration: underline;
  }

  @media screen and (min-width: $desktop_breakpoint) {
    padding: 6rem;

    .Users_search-name {
      min-width: 200px;
    }
  }
}
</style>
