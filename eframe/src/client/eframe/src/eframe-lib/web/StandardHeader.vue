<!--
  - Copyright (c) Michael Houston 2021. All rights reserved.
  -->

<template>
  <Toast :autoZIndex="false" :baseZIndex="1000" class="toast-class"/>
  <Toolbar>
    <template #start>
      <Button icon="pi pi-bars" class="mr-2"/>
    </template>

    <template #end>
      <Button icon="pi pi-cog" class="mr-2"/>
      <Button icon="pi pi-power-off" class="mr-2"/>
      <Button icon="pi pi-home" class="p-button-success" @click="handleHomeClick($event)"/>
    </template>
  </Toolbar>
</template>

<script>

import Button from 'primevue/button';
import Toast from 'primevue/toast';
import Toolbar from 'primevue/toolbar';

export default {
  name: "StandardHeader",
  components: {
    Button, Toast, Toolbar
  },
  methods: {
    handleHomeClick() {
      console.log('Home clicked');
      window.location = "/"
    },
  }


}
</script>
