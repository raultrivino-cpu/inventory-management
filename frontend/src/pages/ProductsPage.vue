<template>
  <q-page padding>
    <div class="row items-center justify-between q-mb-md">
      <div>
        <div class="text-h4">Productos</div>
        <div class="text-subtitle2 text-grey-7">
          Registro y consulta de productos por empresa
        </div>
      </div>

      <q-chip color="primary" text-color="white" icon="inventory_2">
        {{ authStore.role }}
      </q-chip>
    </div>

    <q-card class="q-mb-lg">
      <q-card-section>
        <div class="text-h6">
          {{ isEditing ? 'Editar producto' : 'Registrar producto' }}
        </div>
      </q-card-section>

      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-12 col-md-3">
            <q-input
              v-model="productForm.nombre"
              label="Nombre del producto"
              outlined
            />
          </div>

          <div class="col-12 col-md-6">
            <q-input
              v-model="productForm.caracteristicas"
              label="Características"
              outlined
            />
          </div>

          <div class="col-12 col-md-4">
            <q-input
              v-model.number="productForm.precioPesos"
              label="Precio COP"
              type="number"
              outlined
            />
          </div>

          <div class="col-12 col-md-4">
            <q-input
              v-model.number="productForm.precioDolares"
              label="Precio USD"
              type="number"
              outlined
            />
          </div>

          <div class="col-12 col-md-4">
            <q-input
              v-model.number="productForm.precioEuros"
              label="Precio EUR"
              type="number"
              outlined
            />
          </div>

          <div class="col-12">
            <q-select
              v-model="productForm.nitEmpresa"
              :options="companyOptions"
              label="Empresa"
              outlined
              emit-value
              map-options
              option-label="label"
              option-value="value"
            />
          </div>

          <div class="col-12">
            <q-select
              v-model="productForm.categorias"
              :options="categoryOptions"
              label="Categorías"
              outlined
              multiple
              use-chips
              emit-value
              map-options
              option-label="label"
              option-value="value"
            />
          </div>
        </div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat color="grey" label="Limpiar" @click="resetForm" />

        <q-btn
          color="primary"
          :label="isEditing ? 'Actualizar producto' : 'Guardar producto'"
          :loading="saving"
          @click="saveProduct"
        />
      </q-card-actions>
    </q-card>

    <q-card>
      <q-card-section>
        <div class="text-h6">Productos registrados</div>
      </q-card-section>

      <q-table
        :rows="products"
        :columns="columns"
        row-key="id"
        :loading="loading"
        flat
        bordered
      >
        <template #body-cell-precioPesos="props">
          <q-td :props="props">
            {{ formatCurrency(props.row.precioPesos, 'COP') }}
          </q-td>
        </template>

        <template #body-cell-precioDolares="props">
          <q-td :props="props">
            {{ formatCurrency(props.row.precioDolares, 'USD') }}
          </q-td>
        </template>

        <template #body-cell-precioEuros="props">
          <q-td :props="props">
            {{ formatCurrency(props.row.precioEuros, 'EUR') }}
          </q-td>
        </template>

        <template #body-cell-actions="props">
          <q-td :props="props">
            <div class="q-gutter-sm">
              <q-btn
                dense
                flat
                color="primary"
                icon="edit"
                @click="editProduct(props.row)"
              />

              <q-btn
                dense
                flat
                color="negative"
                icon="delete"
                @click="confirmDelete(props.row)"
              />
            </div>
          </q-td>
        </template>

        <template #no-data>
          <div class="full-width row flex-center text-grey-7 q-gutter-sm">
            <q-icon name="inventory_2" size="2em" />
            <span>No hay productos registrados</span>
          </div>
        </template>
      </q-table>
    </q-card>
  </q-page>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useQuasar } from 'quasar'
import api from 'src/services/api'
import { useAuthStore } from 'src/stores/auth-store'

const $q = useQuasar()
const authStore = useAuthStore()

const products = ref([])
const companies = ref([])
const categories = ref([])
const loading = ref(false)
const saving = ref(false)
const isEditing = ref(false)

const productForm = ref({
  id: null,
  nombre: '',
  caracteristicas: '',
  precioPesos: null,
  precioDolares: null,
  precioEuros: null,
  nitEmpresa: '',
  categorias: [],
})

const columns = [
  {
    name: 'id',
    label: 'ID Producto',
    field: (row) => `PROD-${row.id}`,
    align: 'left',
    sortable: true,
  },
  {
    name: 'nombre',
    label: 'Nombre',
    field: 'nombre',
    align: 'left',
    sortable: true,
  },
  {
    name: 'caracteristicas',
    label: 'Características',
    field: 'caracteristicas',
    align: 'left',
  },
  {
    name: 'precioPesos',
    label: 'Precio COP',
    field: 'precioPesos',
    align: 'right',
  },
  {
    name: 'precioDolares',
    label: 'Precio USD',
    field: 'precioDolares',
    align: 'right',
  },
  {
    name: 'precioEuros',
    label: 'Precio EUR',
    field: 'precioEuros',
    align: 'right',
  },
  {
    name: 'nombreEmpresa',
    label: 'Empresa',
    field: 'nombreEmpresa',
    align: 'left',
  },
  {
    name: 'categorias',
    label: 'Categorías',
    field: (row) =>
      row.categorias && row.categorias.length > 0
        ? row.categorias.map((categoria) => categoria.nombre).join(', ')
        : '-',
    align: 'left',
  },
  {
    name: 'actions',
    label: 'Acciones',
    field: 'actions',
    align: 'center',
  },
]

const companyOptions = computed(() =>
  companies.value.map((company) => ({
    label: `${company.nombre} - ${company.nit}`,
    value: company.nit,
  })),
)

const categoryOptions = computed(() =>
  categories.value.map((category) => ({
    label: category.nombre,
    value: category.id,
  })),
)

const getAuthHeaders = () => ({
  Authorization: authStore.authorization,
})

const loadCompanies = async () => {
  const response = await api.get('/empresas', {
    headers: getAuthHeaders(),
  })

  companies.value = response.data
}

const loadCategories = async () => {
  const response = await api.get('/categorias', {
    headers: getAuthHeaders(),
  })

  categories.value = response.data
}

const loadProducts = async () => {
  try {
    loading.value = true

    const response = await api.get('/productos', {
      headers: getAuthHeaders(),
    })

    products.value = response.data
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible cargar los productos',
    })
  } finally {
    loading.value = false
  }
}

const validateForm = () => {
  if (!productForm.value.nombre || !productForm.value.nitEmpresa || productForm.value.categorias.length === 0) {
    $q.notify({
      type: 'warning',
      message: 'nombre, empresa y categoría son obligatorios',
    })

    return false
  }

  return true
}

const saveProduct = async () => {
  if (!validateForm()) {
    return
  }

  try {
    saving.value = true

    const productPayload = {
      ...productForm.value,
      categorias: productForm.value.categorias.map((categoryId) => ({
        id: categoryId,
      })),
    }

    if (isEditing.value) {
      await api.put(`/productos/${productForm.value.id}`, productPayload, {
        headers: getAuthHeaders(),
      })

      $q.notify({
        type: 'positive',
        message: 'Producto actualizado correctamente',
      })
    } else {
      await api.post('/productos', productPayload, {
        headers: getAuthHeaders(),
      })

      $q.notify({
        type: 'positive',
        message: 'Producto registrado correctamente',
      })
    }

    resetForm()
    await loadProducts()
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible guardar el producto',
    })
  } finally {
    saving.value = false
  }
}

const editProduct = (product) => {
  isEditing.value = true

  productForm.value = {
    id: product.id,
    nombre: product.nombre,
    caracteristicas: product.caracteristicas,
    precioPesos: product.precioPesos,
    precioDolares: product.precioDolares,
    precioEuros: product.precioEuros,
    nitEmpresa: product.nitEmpresa,
    categorias: product.categorias?.map((category) => category.id) || [],
  }
}

const confirmDelete = (product) => {
  $q.dialog({
    title: 'Confirmar eliminación',
    message: `¿Deseas eliminar el producto ${product.nombre}?`,
    cancel: true,
    persistent: true,
  }).onOk(() => {
    deleteProduct(product.id)
  })
}

const deleteProduct = async (id) => {
  try {
    await api.delete(`/productos/${id}`, {
      headers: getAuthHeaders(),
    })

    $q.notify({
      type: 'positive',
      message: 'Producto eliminado correctamente',
    })

    await loadProducts()
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible eliminar el producto',
    })
  }
}

const resetForm = () => {
  isEditing.value = false

  productForm.value = {
    id: null,
    nombre: '',
    caracteristicas: '',
    precioPesos: null,
    precioDolares: null,
    precioEuros: null,
    nitEmpresa: '',
    categorias: [],
  }
}

const formatCurrency = (value, currency) => {
  if (value === null || value === undefined || value === '') {
    return '-'
  }

  return new Intl.NumberFormat('es-CO', {
    style: 'currency',
    currency,
  }).format(value)
}

onMounted(async () => {
  await loadCompanies()
  await loadCategories()
  await loadProducts()
})
</script>