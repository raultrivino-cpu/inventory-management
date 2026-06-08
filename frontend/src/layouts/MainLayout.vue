<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title>Inventory Management</q-toolbar-title>

        <q-btn flat label="Empresas" to="/companies" />
        <q-btn
          v-if="authStore.isAdmin"
          flat
          label="Productos"
          to="/products"
        />
        <q-btn
          v-if="authStore.isAdmin"
          flat
          label="Inventario"
          to="/inventory"
        />
        <q-btn flat label="Salir" to="/login" />
      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script setup>
  import { useAuthStore } from 'src/stores/auth-store'

  const authStore = useAuthStore()
</script>