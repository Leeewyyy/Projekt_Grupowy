<template>
  <AppPage class="Statistics" name="admin-statistics">
    <AdminNavigation />

    <div class="Statistics_container">
      <div class="Statistics_column column--left">
        <BoxSection class="Statistics_box">
          <template #header>
            <div class="Statistics_header">
              Użytkownicy
            </div>
          </template>
          <template #body>
            <div class="Statistics_box-body">
              <div
                v-if="userStatistics"
                class="Statistics_entry"
              >
                <div>Łącznie zarejestrowanych: {{ userStatistics.registeredUsers }}</div>
                <div>Łącznie zweryfikowanych: {{ userStatistics.verifiedUsers }}</div>
              </div>
            </div>
          </template>
        </BoxSection>
      </div>
      <div class="Statistics_column">
        <BoxSection
          v-if="facilitiesStatistics"
          class="Statistics_box"
        >
          <template #header>
            <div class="Statistics_header">
              Najczęściej wyświetlane placówki
            </div>
          </template>
          <template #body>
            <div class="Statistics_box-body">
              <Table
                :columns="facilitiesColumns"
                :rows="mappedFacilities"
              >
                <template #imageUrl="{ value }">
                  <Avatar
                    size="small"
                    placeholderIcon="place"
                    :imageUrl="value"
                  />
                </template>
                <template #name="{ row }">
                  <nuxt-link
                    class="Statistics_link"
                    :to="`/place/${row.id}`"
                  >
                    {{ row.name }}
                  </nuxt-link>
                </template>
                <template #hits="{ value }">
                  {{ value || 0 }}
                </template>
              </Table>
              <div
                v-if="!isLoadedAllFacilities && mappedFacilities.length >= 15"
                class="Statistics_load-all"
              >
                <ActionButton
                  icon="refresh"
                  backgroundColor="#333"
                  textColor="#fff"
                  @click.native="loadAllPlaces"
                >
                  Wczytaj wszystkie placówki
                </ActionButton>
              </div>
            </div>
          </template>
        </BoxSection>
        <BoxSection
          v-if="searchStatistics"
          class="Statistics_box"
        >
          <template #header>
            <div class="Statistics_header">
              Najczęściej stosowane filtry
            </div>
          </template>
          <template #body>
            <div class="Statistics_box-body">
              <Table
                :columns="searchColumns"
                :rows="mappedSearchs"
              >
                <template #value="{ value }">
                  {{ value || 'N/A' }}
                </template>
                <template #hits="{ value }">
                  {{ value || 0 }}
                </template>
              </Table>
            </div>
          </template>
        </BoxSection>
      </div>
    </div>
  </AppPage>
</template>

<script>
import AppPage from '@/components/AppPage';
import AdminNavigation from '@/components/admin/AdminNavigation';
import ActionButton from '@/components/admin/ActionButton';
import BoxSection from '@/components/BoxSection';
import InputText from '@/components/shared/InputText';
import Select from '@/components/shared/Select';
import SwitchButton from '@/components/shared/SwitchButton';
import Table from '@/components/shared/Table';
import NFZStatus from '@/components/NFZStatus';
import Avatar from '@/components/shared/Avatar';

export default {
  components: {
    AppPage,
    AdminNavigation,
    ActionButton,
    BoxSection,
    InputText,
    Select,
    SwitchButton,
    Table,
    NFZStatus,
    Avatar,
  },

  head() {
    return {
      title: 'Statystyki',
    };
  },

  computed: {
    mappedFacilities() {
      return this.facilitiesStatistics.map((row, idx) => ({
        num: this.facilitiesStatistics.length - idx,
        ...row,
      }));
    },

    mappedSearchs() {
      return this.searchStatistics.map((row, idx) => ({
        num: this.searchStatistics.length - idx,
        ...row,
      }));
    },
  },

  data() {
    return {
      facilitiesColumns: {
        num: {
          name: 'L.p.',
        },
        imageUrl: {
          name: '',
        },
        name: {
          name: 'Nazwa placówki',
        },
        hits: {
          name: 'Liczba wyświetleń',
        },
      },

      searchColumns: {
        num: {
          name: 'L.p.',
        },
        name: {
          name: 'Rodzaj filtra',
        },
        value: {
          name: 'Wartość',
        },
        hits: {
          name: 'Liczba wyszukiwań',
        },
      },

      userStatistics: null,
      facilitiesStatistics: null,
      searchStatistics: null,

      isLoadedAllFacilities: false,
    };
  },

  async fetch() {
    try {
      this.userStatistics = await this.$axios.$get('/api/statistics/users');
      this.facilitiesStatistics = await this.$axios.$get('/api/statistics/medical-facilities/top-15');
      this.searchStatistics = await this.$axios.$get('/api/statistics/search/details');
    } catch (error) {
      this.$notify({ text: 'Wystąpił błąd pobierania danych. Spróbuj odświeżyć stronę.', type: 'error' });
      console.error(error);
    }
  },

  methods: {
    async loadAllPlaces() {
      try {
        this.isLoadedAllFacilities = true;
        this.facilitiesStatistics = await this.$axios.$get('/api/statistics/medical-facilities');
      } catch (error) {
        this.$notify({ text: 'Wystąpił błąd pobierania danych. Spróbuj odświeżyć stronę.', type: 'error' });
        console.error(error);
      }
    },
  },
};
</script>

<style lang="scss">
.Statistics {
  padding: 2rem;
  background: #fafafa;

  .Statistics_box {
    padding: 1.5rem 2rem;
  }

  .Statistics_header {
    margin-bottom: 1rem;
  }

  .Statistics_results {
    margin-top: 2rem;
  }
  
  .Statistics_missing-data {
    color: rgb(var(--color-text), 0.6);
    font-style: italic;
  }

  .Statistics_link {
    font-weight: 400 !important;
    text-decoration: underline;
  }

  .Statistics_container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 2rem;
  }

  .Statistics_column {
    width: 100%;
    display: flex;
    flex-direction: column;
    gap: 2rem;
  }

  .Statistics_load-all {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    margin-top: 1rem;
  }

  @media screen and (min-width: $desktop_breakpoint) {
    padding: 6rem;

    .Statistics_container {
      flex-direction: row;
    }

    .Statistics_column {
      &.column--left {
        width: 30%; 
      }
    }
  }
}
</style>
