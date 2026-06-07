<template>
  <q-page padding>
    <div class="text-h4 q-mb-md">Empresas</div>

    <q-card>
      <q-card-section>
        <div class="text-h6">Formulario de empresa</div>
      </q-card-section>

      <q-card-section>
        <q-input v-model="company.nit" label="NIT" outlined class="q-mb-md" />
        <q-input v-model="company.name" label="Nombre de la empresa" outlined class="q-mb-md" />
        <q-input v-model="company.address" label="Dirección" outlined class="q-mb-md" />
        <q-input v-model="company.phone" label="Teléfono" outlined />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn color="primary" label="Guardar empresa" />
      </q-card-actions>
    </q-card>
  </q-page>
</template>

<script setup>
import { ref } from 'vue'

const company = ref({
  nit: '',
  name: '',
  address: '',
  phone: '',
})
</script>