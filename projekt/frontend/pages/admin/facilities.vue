<template>
  <AppPage class="Facilities" name="admin-facilities">
    <AdminNavigation />

    <BoxSection
      class="Facilities_box Facilities_search"
      :noOverflow="false"
    >
      <template #header>
        <div class="Facilities_header">
          Wyszukaj placówkę
        </div>
      </template>
      <template #body>
        <div class="Facilities_search-content">
          <div class="Facilities_search-group">
            <div class="Facilities_search-name">
              Nazwa placówki
            </div>
            <InputText
              id="searchName"
              class="Facilities_search-input"
              name="searchName"
              v-model="searchNameTemp"
              placeholder="np. Szpital im. Mikołaja Kopernika"
              @input="onSearchUpdated"
            />
          </div>
          <div class="Facilities_search-group">
            <div class="Facilities_search-name">
              Typ placówki
            </div>
            <Select
              name="placeType"
              class="Facilities_search-input"
              label="name"
              trackBy="type"
              placeholder="np. SZPITAL"
              :options="facilityTypeOptions"
              v-model="searchType"
            />
          </div>
          <div class="Facilities_search-group">
            <div class="Facilities_search-name">
              Pełne finansowanie przez NFZ
            </div>
            <SwitchButton
              id="nfzSwitch"
              v-model="searchNFZ"
            />
          </div>
        </div>
      </template>
    </BoxSection>
    <BoxSection class="Facilities_box Facilities_results">
      <template #header>
        <div class="Facilities_header-wrapper">
          <div class="Facilities_header">
            Lista placówek
          </div>
          <ActionButton
            element="nuxt-link"
            class="Facilities_add-button"
            to="/place/edit/0"
            icon="add"
            backgroundColor="#333"
            textColor="#fff"
          >
            Dodaj
          </ActionButton>
        </div>
      </template>
      <template #body>
        <div class="Facilities_box-body">
          <Table
            :columns="columns"
            :rows="filteredRows"
            data-tid="facilities-table"
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
                class="Facilities_link"
                :to="`/place/${row.id}`"
              >
                {{ row.name }}
              </nuxt-link>
            </template>
            <template #nfzStatus="{ value }">
              <NFZStatus :status="value" hide-label />
            </template>
            <template #rating="{ row }">
              <span v-if="row.rating">
                {{ row.rating.toFixed(2) }} (z {{ row.ratingCount }} ocen)
              </span>
              <span v-else class="Facilities_missing-data">
                brak ocen
              </span>
            </template>
            <template #websiteUrl="{ value }">
              <a
                class="Facilities_link"
                :href="value"
                target="_blank"
                rel="noreferrer noopener"
              >
                {{ value }}
              </a>
            </template>
            <template #addedAt="{ value }">
              <span v-if="value">
                {{ new Date(value).toLocaleString('pl') }}
              </span>
              <span v-else class="Facilities_missing-data">
                brak danych
              </span>
            </template>
            <template #actions="{ row }">
              <div class="Facilities_actions">
                <ActionButton
                  element="nuxt-link"
                  :to="`/place/edit/${row.id}`"
                  icon="edit"
                >
                  Edytuj
                </ActionButton>
                <ActionButton
                  icon="delete"
                  backgroundColor="#f00"
                  textColor="#fff"
                  data-tid="delete-user"
                  @click.native="deleteFacility(row)"
                >
                  Usuń
                </ActionButton>
              </div>
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
      title: 'Zarządzanie placówkami',
    };
  },

  computed: {
    facilityTypeOptions() {
      const facilityTypes = this.$store.getters['facilitiesSearch/getFacilitiesTypes'];
      return facilityTypes.map(({ name }) => ({ name, type: name }));
    },

    filteredRows() {
      // eslint-disable-next-line
      return this.rows.filter((row) => {
        const { name, type, nfzStatus } = row;
        const nameSearch = this.searchName?.toLowerCase();
        const typeSearch = this.searchType?.type;
        if (nameSearch && !name.toLowerCase().includes(nameSearch)) return false;
        if (typeSearch && type !== typeSearch) return false;
        if (this.searchNFZ && nfzStatus !== 'FULL') return false;
        return true;
      });
    },
  },

  data() {
    const rows = this.$store.getters['facility/getFacilities'];
    
    return {
      columns: {
        id: {
          name: 'ID',
        },
        imageUrl: {
          name: '',
          inline: true,
        },
        name: {
          name: 'Nazwa',
        },
        type: {
          name: 'Typ',
        },
        nfzStatus: {
          name: 'NFZ',
        },
        address: {
          name: 'Adres',
        },
        rating: {
          name: 'Ocena',
        },
        websiteUrl: {
          name: 'Witryna',
        },
        addedAt: {
          name: 'Data dodania',
        },
        actions: {
          name: 'Akcje',
        },
      },

      rows,
      searchNameTemp: null,
      searchName: null,
      searchType: null,
      searchNFZ: false,
      searchTimeout: null,
    };
  },

  async fetch() {
    try {
      await this.$store.dispatch('facilitiesSearch/getFacilitiesTypes');
    } catch (error) {
      this.$notify({ text: 'Wystąpił błąd pobierania danych. Spróbuj odświeżyć stronę.', type: 'error' });
      console.error(error);
    }
  },
  
  methods: {
    onSearchUpdated() {
      // Debounce search
      if (this.searchTimeout) clearTimeout(this.searchTimeout);

      this.searchTimeout = setTimeout(() => {
        this.searchName = this.searchNameTemp;
      }, 1000);
    },

    async deleteFacility(facility) {
      const { id, name } = facility;
      const confirm = window.confirm(`Potwierdź usunięcie placówki #${id} "${name}"...`); // eslint-disable-line
      if (!confirm) return;

      try {
        await this.$store.dispatch('facility/deleteFacilityById', id);
        this.$notify({ text: `Placówka "${name}" została usunięta.`, type: 'success' });
      } catch (error) {
        this.$notify({ text: 'Wystąpił błąd podczas usuwania placówki.', type: 'error' });
        console.error(error);
      }
    },
  },
};
</script>

<style lang="scss">
.Facilities {
  padding: 2rem;
  background: #fafafa;

  .Facilities_box {
    padding: 1.5rem 2rem;
  }
  
  .Facilities_search-content {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin-top: 1rem;
  }

  .Facilities_search-group {
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 1rem;
  }

  .Facilities_search-name {
    width: max-content;
    min-width: 100px;
  }

  .Facilities_search-input {
    max-width: 400px;
  }

  .Facilities_results {
    margin-top: 2rem;
  }

  .Facilities_missing-data {
    color: rgb(var(--color-text), 0.6);
    font-style: italic;
  }

  .Facilities_link {
    font-weight: 400 !important;
    text-decoration: underline;
  }

  .Facilities_actions {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    gap: 1rem;
  }

  @media screen and (min-width: $desktop_breakpoint) {
    padding: 6rem;

    .Facilities_search-name {
      min-width: 260px;
    }

    .Facilities_header-wrapper {
      display: flex;
      flex-direction: row;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 1rem;
    }
  }
}
</style>
