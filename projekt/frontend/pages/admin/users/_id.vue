<template>
  <AppPage class="User" name="admin-user">
    <AdminNavigation />
    <div class="User_container">
      <div class="User_column">
        <BoxSection class="User_box">
          <template #header>
            Karta użytkownika
          </template>
          <template #body>
            <div class="User_box-content">
              <div class="User_name">
                {{ user?.fullName || 'Nie znaleziono użytkownika' }}
              </div>
              <SectionName class="User_section">
                Statystyki
              </SectionName>
              <div
                v-if="userStatistics"
                class="User_statistics"
              >
                <div>Dodanych placówek: {{ userStatistics.addedMedicalFacilities }}</div>
                <div>Ulubionych placówek: {{ userStatistics.favoriteMedicalFacilities }}</div>
                <div>Dodanych opinii: {{ userStatistics.addedOpinions }}</div>
              </div>
            </div>
          </template>
        </BoxSection>
        <UserReviews
          v-if="user"
          class="User_box"
          :userId="user.id"
        />
      </div>
      <div
        v-if="user"
        class="User_column"
      >
        <BoxSection class="User_box">
          <template #header>
            Szczegóły użytkownika
          </template>
          <template #body>
            <div class="User_box-content">
              <div class="User_box-wrapper">
                <div class="User_details">
                  <div class="User_details-row">
                    <div class="User_details-name">Imię i nazwisko</div>
                    <div class="User_details-value">{{ user.fullName }}</div>
                  </div>
                  <div class="User_details-row">
                    <div class="User_details-name">Adres e-mail</div>
                    <div class="User_details-value">{{ user.email }}</div>
                  </div>
                  <div class="User_details-row">
                    <div class="User_details-name">Data rejestracji</div>
                    <div class="User_details-value">
                      {{ new Date(user.registrationDate).toLocaleString('pl') }}
                    </div>
                  </div>
                </div>
                <div class="User_avatar-box">
                  <Avatar
                    size="huge"
                    placeholderIcon="person"
                    :imageUrl="user.imageUrl"
                  />
                </div>
              </div>
              <SectionName>
                Rola użytkownika (uprawnienia)
              </SectionName>
              <div class="User_role">
                <div class="User_role-title">
                  Wybierz rolę
                </div>
                <Select
                  name="roleSelect"
                  class="User_role-select"
                  label="name"
                  trackBy="value"
                  :allowEmpty="false"
                  :options="roleOptions"
                  v-model="selectedRole"
                  @input="updateRole"
                />
              </div>
              <div class="User_roles-tutorial">
                <div class="User_roles-title">
                  Objaśnienie
                </div>
                <ul class="User_roles-list">
                  <li
                    v-for="role in roleOptions"
                    :key="role.value"
                    class="User_roles-item"
                  >
                    Rola <b>{{ role.name }}</b> - {{ role.description }}
                  </li>
                </ul>
              </div>
            </div>
          </template>
        </BoxSection>
        <div class="User_actions">
          <ActionButton
            icon="delete"
            backgroundColor="#f00"
            textColor="#fff"
            data-tid="delete-user"
            @click.native="deleteUser"
          >
            Usuń użytkownika
          </ActionButton>
        </div>
      </div>
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import AdminNavigation from '@/components/admin/AdminNavigation';
import UserReviews from '@/components/admin/UserReviews';
import ActionButton from '@/components/admin/ActionButton';
import BoxSection from '@/components/BoxSection';
import SectionName from '@/components/shared/SectionName';
import Avatar from '@/components/shared/Avatar';
import Select from '@/components/shared/Select';

export default {
  components: {
    AppPage,
    AdminNavigation,
    UserReviews,
    ActionButton,
    BoxSection,
    SectionName,
    Avatar,
    Select,
  },

  head() {
    return {
      title: 'Karta użytkownika',
    };
  },

  data() {
    return {
      user: null,
      userStatistics: null,

      roleOptions: [
        {
          name: 'USER',
          value: 'USER',
          description: 'przeglądanie i wyszukiwanie placówek, dodawanie do ulubionych, wystawianie opinii',
        },
        {
          name: 'VERIFIED',
          value: 'VERIFIED',
          description: 'USER + dodatkowo tworzenie, edycja i usuwanie swoich placówek',
        },
        {
          name: 'ADMIN',
          value: 'ADMIN',
          description: 'pełna kontrola i dostęp do panelu administratora',
        },
      ],

      selectedRole: null,
    };
  },

  async fetch() {
    try {
      const id = +this.$route.params.id;
      this.user = await this.$axios.$get(`/api/users/${id}`);
      this.userStatistics = await this.$axios.$get(`/api/users/${id}/statistics`);
      this.updateSelectedRole();
    } catch (error) {
      this.$notify({ text: 'Nie udało się pobrać szczegółów użytkownika.', type: 'error' });
      console.error(error);
    }
  },

  methods: {
    updateSelectedRole() {
      this.selectedRole = this.roleOptions.find((role) => this.user.role === role.value);
    },

    async deleteUser() {
      const { id, fullName } = this.user;
      const confirm = window.confirm(`Potwierdź usunięcie użytkownika #${id} "${fullName}"...`); // eslint-disable-line
      if (!confirm) return;

      try {
        await this.$axios.$delete(`/api/users/${id}`);
        this.$notify({ text: `Użytkownik "${fullName}" został usunięty.`, type: 'success' });
        this.$router.push('/admin/users');
      } catch (error) {
        this.$notify({ text: 'Wystąpił błąd podczas usuwania użytkownika.', type: 'error' });
        console.error(error);
      }
    },

    async updateRole() {
      const payload = {
        role: this.selectedRole?.value,
      };

      try {
        await this.$axios.$patch(`/api/users/edit/${this.user.id}`, payload);
        this.$notify({ text: 'Zaktualizowano rolę użytkownika.', type: 'success' });
      } catch (error) {
        this.$notify({ text: 'Wystąpił błąd podczas aktualizacji roli.', type: 'error' });
        console.error(error);
      }
    },
  },
};
</script>

<style lang="scss">
.User {
  padding: 2rem;
  background: #fafafa;
  
  .User_box {
    padding: 1.5rem 2rem;
  }

  .User_box-content {
    margin-top: 1rem;
  }

  .User_name {
    font-size: 1.2rem;
  }
  
  .User_container {
    display: flex;
    flex-direction: column;
    gap: 2rem;
  }

  .User_column {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 2rem;
  }

  .User_actions {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
  }

  .User_details {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin: 2rem 0;
    color: #333;
  }

  .User_details-row {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 1rem;
  }
  
  .User_details-name {
    font-weight: 600;
  }

  .User_box-wrapper {
    display: flex;
    flex-direction: column-reverse;
    gap: 1rem;
  }

  .User_role {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 1rem;
    margin-top: 1rem;
  }

  .User_role-title {
    white-space: nowrap;
  }

  .User_role-select {
    max-width: 300px;
  }

  .User_roles-tutorial {
    margin-top: 1rem;
    padding: 1rem;
    background: #efefef;
    border-radius: 0.5rem;
  }

  .User_roles-list {
    list-style-type: none;
  }

  .User_roles-title {
    margin-bottom: 0.5rem;
  }

  .User_section {
    margin: 1rem 0;
  }

  @media screen and (min-width: $desktop_breakpoint) {
    padding: 6rem;

    .User_container {
      flex-direction: row;
      flex-wrap: nowrap;
    }

    .User_column:first-child {
      width: 40%;
    }

    .User_details-name {
      min-width: 150px;
    }

    .User_box-wrapper {
      flex-direction: row;
      align-items: center;
      justify-content: space-between;
    }
  }
}
</style>
