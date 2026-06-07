<template>
  <q-page padding>
    <div class="row items-center justify-between q-mb-md">
      <div class="text-h4">Inventario</div>

      <div class="q-gutter-sm">
        <q-btn color="primary" label="Descargar PDF" />
        <q-btn color="secondary" label="Enviar por correo" />
      </div>
    </div>

    <q-table
      title="Productos por empresa"
      :rows="rows"
      :columns="columns"
      row-key="id"
    />
  </q-page>
</template>

<script setup>
const columns = [
  { name: 'company', label: 'Empresa', field: 'company', align: 'left' },
  { name: 'product', label: 'Producto', field: 'product', align: 'left' },
  { name: 'code', label: 'Código', field: 'code', align: 'left' },
  { name: 'price', label: 'Precio', field: 'price', align: 'right' },
]

const rows = [
  {
    id: 1,
    company: 'Empresa demo',
    product: 'Producto demo',
    code: 'P001',
    price: 100000,
  },
]
</script>