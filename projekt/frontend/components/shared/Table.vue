<template>
  <div class="Table">
    <header
      v-if="!!$slots.header"
      class="Table_header"
    >
      <slot name="header"></slot>
    </header>
    <div class="Table_table-container">
      <table class="Table_table" cellspacing="0">
        <thead class="table_head">
          <tr class="table_row">
            <th
              v-for="(options, key) in columns"
              :key="key"
              class="table_column"
              :class="{
                'column--centered': options.centered,
                'column--inline': options.inline,
              }"
            >
              {{ options.name }}
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="row in rows"
            :key="row.id"
            class="table_row-body"
          >
            <td
              v-for="key in columnsList"
              :key="key"
              class="table_column"
              :class="{
                'column--centered': columns[key].centered,
                'column--inline': columns[key].inline,
              }"
            >
              <template v-if="$scopedSlots[key]">
                <slot
                  :row="row"
                  :name="key"
                  :value="getProp(row, key)"
                  :columnKey="key"
                  :columnOptions="columns[key]"
                >
                </slot>
              </template>
              <template v-else>
                {{ getProp(row, key) || "" }}
              </template>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <footer v-if="!!$slots.footer" class="Table_footer">
      <slot name="footer"></slot>
    </footer>
  </div>
</template>

<script>
export default {
  props: {
    columns: {
      type: Object,
      required: true,
    },
    rows: {
      type: Array,
      required: true,
    },
  },

  computed: {
    columnsList() {
      return Object.keys(this.columns);
    },
  },

  methods: {
    getProp(obj, path) {
      return path
        .split('.')
        .reduce((acc, part) => acc && acc[part], obj);
    },
  },
};
</script>

<style lang="scss">
.Table {
  border: 1px solid rgba(var(--color-text), 0.1);
  border-radius: 0.5rem;
  overflow-x: auto;
  overflow-y: hidden;

  .Table_header, .Table_footer {
    display: flex;
    align-items: center;
    margin-bottom: 1rem;
  }
  
  .Table_header {
    border-bottom: 1px solid rgba(var(--color-text), 0.1);
  }

  .Table_footer {
    border-top: 1px solid rgba(var(--color-text), 0.1);
  }

  .Table_table-container {
    display: flex;
  }

  .Table_table {
    min-width: 100%;
    position: relative;

    .table_head {
      position: sticky;
      top: 0;
      font-size: 0.8rem;
      color: rgba(var(--color-black));
      background: rgb(var(--color-main));
      z-index: 10;

      .table_column {
        border-bottom: 1px solid rgb(var(--color-text), 0.12);
      }
    }

    .table_column {
      padding: 0.8rem;
      font-size: 0.9rem;
      border-top: 1px solid rgba(var(--color-text), 0.1);
      text-align: left;

      &.column--centered {
        text-align: center;
      }
      
      &.column--inline {
        width: 1%;
        white-space: nowrap;
      }
    }

    .table_row:first-child .table_column {
      border-top: none;
    }
  }
}
</style>
