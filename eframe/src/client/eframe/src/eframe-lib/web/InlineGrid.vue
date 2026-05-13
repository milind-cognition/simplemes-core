<!--
  - Copyright (c) Michael Houston 2021. All rights reserved.
  -->

<template>
  <DataTable :value="this.$attrs.records" ref="inlineGrid" class="p-datatable-sm editable-cells-table"
             responsiveLayout="scroll"
             v-model:selection="selectedRow" selectionMode="single"
             data-testid="inlineGrid"
             stateStorage="local" :stateKey="storageKey" :rowHover="false"
             editMode="row" v-model:editingRows="editingRows"
             @rowEditInit="onRowEditInit" @rowEditCancel="onRowEditCancel" @rowClick="onRowClick">
    <template #header>
      <div class="flex justify-content-between">
        <Button type="button" icon="pi pi-plus" class="p-button-outlined" @click="addRow"
                :title="$t('tooltip.addRow')"/>
        <Button type="button" icon="pi pi-minus" class="p-button-outlined" @click="removeRow"
                :title="$t('tooltip.removeRow')"/>
      </div>
    </template>
    <Column v-for="col of columns" :field="col.fieldName" :header="$t(col.fieldLabel)" :key="col.fieldName"
            :sortable="col.sort">
      <template #editor="slotProps">
        <div v-if="col.fieldFormat===$page().domainService.fieldFormats.ENUM">
          <Dropdown v-model="slotProps.data[col.fieldName]" :options="col.validValues" optionLabel="label"
                    optionValue="value">
            <template #option="slotProps">
              <span>{{ slotProps.option.label }}</span>
            </template>
          </Dropdown>
        </div>
        <div v-else-if="col.fieldFormat===$page().domainService.fieldFormats.BOOLEAN">
          <Checkbox v-model="slotProps.data[col.fieldName]" :value="slotProps.data[slotProps.column.props.field]"
                    :binary="true"/>
        </div>
        <div v-else>
          <InputText v-model="slotProps.data[slotProps.column.props.field]"/>
        </div>
      </template>
      <template #body="slotProps">
        <div v-if="col.fieldFormat===$page().domainService.fieldFormats.ENUM">
          {{ getDropDownLabel(col, slotProps.data[col.fieldName]) }}
        </div>
        <div v-else-if="col.fieldFormat===$page().domainService.fieldFormats.BOOLEAN" class="p-checkbox p-highlight"
             role="checkbox">
          <span v-if="slotProps.data[col.fieldName]" class="p-checkbox-icon pi pi-check"/>
        </div>
        <div v-else>
          {{ getDisplayValue(col, slotProps.data[col.fieldName]) }}
        </div>
      </template>
    </Column>
    <Column :rowEditor="true" bodyStyle="text-align:center" style="width: 10%; min-width:8rem"></Column>

  </DataTable>
</template>

<script>

import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import Dropdown from 'primevue/dropdown'
import Checkbox from 'primevue/checkbox'

let lastRowClickTime = 0

export default {
  name: 'InlineGrid',
  components: {
    DataTable, Column, Button, InputText, Dropdown, Checkbox
  },
  data() {
    return {
      checked: null,
      editingRows: [],
      selectedRow: null,
      selectedRowIndex: null,
    }
  },
  props: {
    columns: Array,
    storageKey: {
      type: String,
      required: true
    },
  },
  originalEditRecord: null,
  created() {
    this.originalEditRecord = {}
  },
  methods: {
    addRow() {
      var row = {sequence: 10}
      for (let col of this.columns) {
        if (col.defaultValue) {
          // eslint-disable-next-line no-unused-vars
          let theRecords = this.$attrs.records
          let value = eval(col.defaultValue)
          if (value) {
            row[col.fieldName] = value
          }
        }
      }
      if (this.$attrs.records == undefined) {
        this.$attrs.records = []

      }
      this.$attrs.records[this.$attrs.records.length] = row
    },
    $page() {
      // Returns the global $page element for access to common services
      return window.$page
    },
    getDisplayValue(column, fieldValue) {
      return fieldValue
    },
    getDropDownLabel(column, fieldValue) {
      const validValues = column.validValues
      if (validValues) {
        for (let row of validValues) {
          if (row.value == fieldValue) {
            return row.label
          }
        }
      }

      return fieldValue
    },
    onRowClick(event) {
      this.selectedRowIndex = event.index
      // Check for double-click on the row.
      const now = Date.now()
      if (now - lastRowClickTime < 300) {
        // A double click on a row
        this.$refs.inlineGrid.onRowEditInit(event)
      }
      lastRowClickTime = now

    },
    onRowEditInit(event) {
      console.log(event);

      this.originalEditRecord[event.index] = {...this.$attrs.records[event.index]};
    },
    onRowEditCancel(event) {
      this.$attrs.records[event.index] = this.originalEditRecord[event.index];
    },
    removeRow() {
      if (this.selectedRowIndex != null) {
        this.$attrs.records.splice(this.selectedRowIndex, 1)
        this.selectedRowIndex = null
      }
    },
  },

}
</script>
