<template>
  <q-page padding>
    <div class="text-h4 q-mb-md">Productos</div>

    <q-card>
      <q-card-section>
        <div class="text-h6">Formulario de producto</div>
      </q-card-section>

      <q-card-section>
        <q-input v-model="product.code" label="Código" outlined class="q-mb-md" />
        <q-input v-model="product.name" label="Nombre del producto" outlined class="q-mb-md" />
        <q-input v-model="product.features" label="Características" outlined class="q-mb-md" />
        <q-input v-model="product.priceCop" label="Precio COP" type="number" outlined class="q-mb-md" />
        <q-input v-model="product.priceUsd" label="Precio USD" type="number" outlined class="q-mb-md" />
        <q-input v-model="product.priceEur" label="Precio EUR" type="number" outlined class="q-mb-md" />
        <q-input v-model="product.companyNit" label="NIT Empresa" outlined />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn color="primary" label="Guardar producto" />
      </q-card-actions>
    </q-card>
  </q-page>
</template>

<script setup>
import { ref } from 'vue'

const product = ref({
  code: '',
  name: '',
  features: '',
  priceCop: '',
  priceUsd: '',
  priceEur: '',
  companyNit: '',
})
</script>