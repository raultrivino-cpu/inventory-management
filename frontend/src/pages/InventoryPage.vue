<template>
  <q-page padding>
    <div class="row items-center justify-between q-mb-md">
      <div>
        <div class="text-h4">Inventario</div>
        <div class="text-subtitle2 text-grey-7">
          Consulta de productos registrados por empresa
        </div>
      </div>

      <div class="q-gutter-sm">
        <q-btn
          color="primary"
          icon="picture_as_pdf"
          label="Descargar PDF"
          :disable="products.length === 0"
          :loading="downloading"
          @click="downloadPdf"
        />

        <q-btn
          color="secondary"
          icon="email"
          label="Enviar por correo"
          :disable="products.length === 0"
          @click="showEmailDialog = true"
        />
      </div>
    </div>

    <q-card class="q-mb-lg">
      <q-card-section>
        <div class="text-h6">Buscar inventario por empresa</div>
      </q-card-section>

      <q-card-section>
        <div class="row q-col-gutter-md items-center">
          <div class="col-12 col-md-8">
            <q-select
              v-model="selectedCompanyNit"
              :options="companyOptions"
              label="Empresa"
              outlined
              clearable
              emit-value
              map-options
              option-label="label"
              option-value="value"
            />
          </div>

          <div class="col-12 col-md-4">
            <q-btn
              color="primary"
              icon="search"
              label="Buscar"
              class="full-width"
              :loading="loading"
              @click="loadInventoryByCompany"
            />
          </div>
        </div>
      </q-card-section>
    </q-card>

    <q-card v-if="selectedCompanyInfo" class="q-mb-lg">
      <q-card-section>
        <div class="text-h6">{{ selectedCompanyInfo.nombre }}</div>
        <div class="text-subtitle2 text-grey-7">
          NIT: {{ selectedCompanyInfo.nit }}
        </div>
      </q-card-section>
    </q-card>

    <q-card>
      <q-card-section>
        <div class="text-h6">Productos encontrados</div>
      </q-card-section>

      <q-table
        :rows="products"
        :columns="columns"
        row-key="id"
        :loading="loading"
        flat
        bordered
      >
        <template #body-cell-codigoInterno="props">
          <q-td :props="props">
            PROD-{{ props.row.id }}
          </q-td>
        </template>

        <template #body-cell-categorias="props">
          <q-td :props="props">
            {{ formatCategories(props.row.categorias) }}
          </q-td>
        </template>

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

        <template #no-data>
          <div class="full-width row flex-center text-grey-7 q-gutter-sm">
            <q-icon name="inventory_2" size="2em" />
            <span>
              {{
                selectedCompanyNit
                  ? 'No hay productos registrados para esta empresa'
                  : 'Selecciona una empresa para consultar el inventario'
              }}
            </span>
          </div>
        </template>
      </q-table>
    </q-card>

    <q-dialog v-model="showEmailDialog">
      <q-card style="width: 420px; max-width: 90vw">
        <q-card-section>
          <div class="text-h6">Enviar inventario por correo</div>
          <div class="text-subtitle2 text-grey-7">
            Se enviará el inventario de la empresa seleccionada.
          </div>
        </q-card-section>

        <q-card-section>
          <q-input
            v-model="email"
            label="Correo destino"
            type="email"
            outlined
          />
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="Cancelar" color="grey" v-close-popup />

          <q-btn
            color="secondary"
            label="Enviar"
            :loading="sending"
            @click="sendPdfByEmail"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-page>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useQuasar } from 'quasar'
import api from 'src/services/api'
import { useAuthStore } from 'src/stores/auth-store'

const $q = useQuasar()
const authStore = useAuthStore()

const companies = ref([])
const products = ref([])

const selectedCompanyNit = ref(null)
const loading = ref(false)
const downloading = ref(false)
const sending = ref(false)

const showEmailDialog = ref(false)
const email = ref('')

const columns = [
  {
    name: 'codigoInterno',
    label: 'Código interno',
    field: 'id',
    align: 'left',
    sortable: true,
  },
  {
    name: 'nombre',
    label: 'Producto',
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
    name: 'categorias',
    label: 'Categorías',
    field: 'categorias',
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
]

const getAuthHeaders = () => ({
  Authorization: authStore.authorization,
})

const companyOptions = computed(() =>
  companies.value.map((company) => ({
    label: `${company.nombre} - ${company.nit}`,
    value: company.nit,
  })),
)

const selectedCompanyInfo = computed(() =>
  companies.value.find((company) => company.nit === selectedCompanyNit.value),
)

const loadCompanies = async () => {
  try {
    const response = await api.get('/empresas', {
      headers: getAuthHeaders(),
    })

    companies.value = response.data
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible cargar las empresas',
    })
  }
}

const loadInventoryByCompany = async () => {
  if (!selectedCompanyNit.value) {
    $q.notify({
      type: 'warning',
      message: 'Debes seleccionar una empresa',
    })
    return
  }

  try {
    loading.value = true

    const response = await api.get(
      `/inventario/empresa/${selectedCompanyNit.value}`,
      {
        headers: getAuthHeaders(),
      },
    )

    products.value = response.data
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible cargar el inventario',
    })
  } finally {
    loading.value = false
  }
}

const downloadPdf = async () => {
  if (!selectedCompanyNit.value) {
    $q.notify({
      type: 'warning',
      message: 'Debes seleccionar una empresa',
    })
    return
  }

  try {
    downloading.value = true

    const response = await api.get(
      `/inventario/empresa/${selectedCompanyNit.value}/pdf`,
      {
        headers: getAuthHeaders(),
        responseType: 'blob',
      },
    )

    const blob = new Blob([response.data], { type: 'application/pdf' })
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')

    link.href = url
    link.setAttribute('download', `inventario-${selectedCompanyNit.value}.pdf`)
    document.body.appendChild(link)
    link.click()

    link.remove()
    window.URL.revokeObjectURL(url)

    $q.notify({
      type: 'positive',
      message: 'PDF descargado correctamente',
    })
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible descargar el PDF',
    })
  } finally {
    downloading.value = false
  }
}

const sendPdfByEmail = async () => {
  if (!selectedCompanyNit.value) {
    $q.notify({
      type: 'warning',
      message: 'Debes seleccionar una empresa',
    })
    return
  }

  if (!email.value) {
    $q.notify({
      type: 'warning',
      message: 'Debes ingresar un correo destino',
    })
    return
  }

  try {
    sending.value = true

    await api.post(
      `/inventario/empresa/${selectedCompanyNit.value}/email`,
      {
        email: email.value,
      },
      {
        headers: getAuthHeaders(),
      },
    )

    $q.notify({
      type: 'positive',
      message: 'Correo enviado correctamente',
    })

    email.value = ''
    showEmailDialog.value = false
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible enviar el correo',
    })
  } finally {
    sending.value = false
  }
}

const formatCategories = (categories) => {
  if (!categories || categories.length === 0) {
    return '-'
  }

  return categories.map((category) => category.nombre).join(', ')
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

onMounted(() => {
  loadCompanies()
})
</script>