<template>
  <q-layout view="lHh Lpr lFf">
    <q-page-container>
      <q-page class="flex flex-center bg-grey-2">
        <q-card style="width: 400px; max-width: 90vw">
          <q-card-section>
            <div class="text-h5 text-center">Inventory Management</div>
            <div class="text-subtitle2 text-center text-grey-7">
              Inicio de sesión
            </div>
          </q-card-section>

          <q-card-section>
            <q-input
              v-model="username"
              label="Usuario"
              outlined
              class="q-mb-md"
            />

            <q-input
              v-model="password"
              label="Contraseña"
              type="password"
              outlined
            />
          </q-card-section>

          <q-card-actions align="right">
            <q-btn
              color="primary"
              label="Ingresar"
              :loading="loading"
              @click="handleLogin"
            />
          </q-card-actions>
        </q-card>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useQuasar } from 'quasar'
import { useAuthStore } from 'src/stores/auth-store'

const router = useRouter()
const $q = useQuasar()
const authStore = useAuthStore()

const username = ref('')
const password = ref('')
const loading = ref(false)

const handleLogin = async () => {
  try {
    loading.value = true

    await authStore.signIn(username.value, password.value)

    $q.notify({
      type: 'positive',
      message: 'Inicio de sesión exitoso',
    })

    await router.push('/companies')
  } catch (error) {
    console.error(error)

    $q.notify({
      type: 'negative',
      message: 'Usuario o contraseña incorrectos',
    })
  } finally {
    loading.value = false
  }
}
</script>