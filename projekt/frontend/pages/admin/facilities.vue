<template>
  <AppPage class="Facilities" name="admin-facilities">
    <BoxSection class="Facilities_box">
      <template #header>
        Admin: Zarządzanie placówkami
      </template>
      <template #body>
        <div class="Facilities_box-body">
          <InputText
            id="facilitySearch"
            name="facilitySearch"
            v-model="searchTemp"
            placeholder="Wyszukaj placówkę..."
            @input="onSearch"
          />
        </div>
      </template>
    </BoxSection>
    <BoxSection class="Facilities_box Facilities_results">
      <template #header>
        <span v-if="searchValue">
          Wyniki wyszukiwania: "{{ searchValue }}"
        </span>
        <span v-else>
          Lista placówek
        </span>
      </template>
      <template #body>
        <div class="Facilities_box-body">
          <Table
            :columns="columns"
            :rows="filteredRows"
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
                :to="`/admin/facilities/${row.id}`"
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
import NFZStatus from '@/components/NFZStatus';
import Avatar from '@/components/shared/Avatar';

export default {
  components: {
    AppPage,
    BoxSection,
    InputText,
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
    filteredRows() {
      // eslint-disable-next-line
      return this.rows.filter((row) => {
        return !this.searchValue || [
          row.name,
          row.type,
          row.address,
        ].some((v) => v.toLowerCase().includes(this.searchValue));
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
      },

      rows,
      searchTemp: null,
      searchValue: null,
      searchTimeout: null,
    };
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
.Facilities {
  padding: 2rem;
  background: #fafafa;

  .Facilities_box {
    padding: 1rem;
  }

  .Facilities_box-body {
    margin-top: 1rem;
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

  @media screen and (min-width: $tablet_breakpoint) {

  }

  @media screen and (min-width: $desktop_breakpoint) {
    padding: 6rem;
  }
}
</style>
