<template>
  <q-page padding>
    <div class="row items-center justify-between q-mb-md">
      <div>
        <div class="text-h4">Empresas</div>
        <div class="text-subtitle2 text-grey-7">
          {{ authStore.isAdmin ? 'Administración de empresas' : 'Consulta de empresas' }}
        </div>
      </div>

      <q-chip
        :color="authStore.isAdmin ? 'primary' : 'secondary'"
        text-color="white"
        icon="person"
      >
        {{ authStore.role }}
      </q-chip>
    </div>

    <!-- Formulario visible solo para ADMIN -->
    <q-card v-if="authStore.isAdmin" class="q-mb-lg">
      <q-card-section>
        <div class="text-h6">
          {{ isEditing ? 'Editar empresa' : 'Registrar empresa' }}
        </div>
      </q-card-section>

      <q-card-section>
        <div class="row q-col-gutter-md">
          <div class="col-12 col-md-3">
            <q-input
              v-model="companyForm.nit"
              label="NIT"
              outlined
              :disable="isEditing"
            />
          </div>

          <div class="col-12 col-md-3">
            <q-input
              v-model="companyForm.nombre"
              label="Nombre de la empresa"
              outlined
            />
          </div>

          <div class="col-12 col-md-3">
            <q-input
              v-model="companyForm.direccion"
              label="Dirección"
              outlined
            />
          </div>

          <div class="col-12 col-md-3">
            <q-input
              v-model="companyForm.telefono"
              label="Teléfono"
              outlined
            />
          </div>
        </div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn
          flat
          color="grey"
          label="Limpiar"
          @click="resetForm"
        />

        <q-btn
          color="primary"
          :label="isEditing ? 'Actualizar empresa' : 'Guardar empresa'"
          :loading="saving"
          @click="saveCompany"
        />
      </q-card-actions>
    </q-card>

    <!-- Tabla visible para ADMIN y EXTERNAL -->
    <q-card>
      <q-card-section>
        <div class="text-h6">Empresas registradas</div>
      </q-card-section>

      <q-table
        :rows="companies"
        :columns="columns"
        row-key="nit"
        :loading="loading"
        flat
        bordered
      >
        <template #body-cell-actions="props">
          <q-td :props="props">
            <div v-if="authStore.isAdmin" class="q-gutter-sm">
              <q-btn
                dense
                flat
                color="primary"
                icon="edit"
                @click="editCompany(props.row)"
              />

              <q-btn
                dense
                flat
                color="negative"
                icon="delete"
                @click="confirmDelete(props.row)"
              />
            </div>

            <span v-else class="text-grey-6">Solo lectura</span>
          </q-td>
        </template>

        <template #no-data>
          <div class="full-width row flex-center text-grey-7 q-gutter-sm">
            <q-icon name="business" size="2em" />
            <span>No hay empresas registradas</span>
          </div>
        </template>
      </q-table>
    </q-card>
  </q-page>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useQuasar } from 'quasar'
import api from 'src/services/api'
import { useAuthStore } from 'src/stores/auth-store'

const $q = useQuasar()
const authStore = useAuthStore()

const companies = ref([])
const loading = ref(false)
const saving = ref(false)
const isEditing = ref(false)

const companyForm = ref({
  nit: '',
  nombre: '',
  direccion: '',
  telefono: '',
})

const columns = [
  {
    name: 'nit',
    label: 'NIT',
    field: 'nit',
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
    name: 'direccion',
    label: 'Dirección',
    field: 'direccion',
    align: 'left',
  },
  {
    name: 'telefono',
    label: 'Teléfono',
    field: 'telefono',
    align: 'left',
  },
  {
    name: 'actions',
    label: 'Acciones',
    field: 'actions',
    align: 'center',
  },
]

const getAuthHeaders = () => ({
  Authorization: authStore.authorization,
})

const loadCompanies = async () => {
  try {
    loading.value = true

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
  } finally {
    loading.value = false
  }
}

const saveCompany = async () => {
  if (!companyForm.value.nit || !companyForm.value.nombre) {
    $q.notify({
      type: 'warning',
      message: 'El NIT y el nombre son obligatorios',
    })
    return
  }

  try {
    saving.value = true

    if (isEditing.value) {
      await api.put(`/empresas/${companyForm.value.nit}`, companyForm.value, {
        headers: getAuthHeaders(),
      })

      $q.notify({
        type: 'positive',
        message: 'Empresa actualizada correctamente',
      })
    } else {
      await api.post('/empresas', companyForm.value, {
        headers: getAuthHeaders(),
      })

      $q.notify({
        type: 'positive',
        message: 'Empresa registrada correctamente',
      })
    }

    resetForm()
    await loadCompanies()
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible guardar la empresa',
    })
  } finally {
    saving.value = false
  }
}

const editCompany = (company) => {
  isEditing.value = true

  companyForm.value = {
    nit: company.nit,
    nombre: company.nombre,
    direccion: company.direccion,
    telefono: company.telefono,
  }
}

const confirmDelete = (company) => {
  $q.dialog({
    title: 'Confirmar eliminación',
    message: `¿Deseas eliminar la empresa ${company.nombre}?`,
    cancel: true,
    persistent: true,
  }).onOk(() => {
    deleteCompany(company.nit)
  })
}

const deleteCompany = async (nit) => {
  try {
    await api.delete(`/empresas/${nit}`, {
      headers: getAuthHeaders(),
    })

    $q.notify({
      type: 'positive',
      message: 'Empresa eliminada correctamente',
    })

    await loadCompanies()
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'No fue posible eliminar la empresa',
    })
  }
}

const resetForm = () => {
  isEditing.value = false

  companyForm.value = {
    nit: '',
    nombre: '',
    direccion: '',
    telefono: '',
  }
}

onMounted(() => {
  loadCompanies()
})
</script>