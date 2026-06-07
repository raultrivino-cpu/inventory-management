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
              v-model="email"
              label="Correo"
              type="email"
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
            <q-btn color="primary" label="Ingresar" @click="login" />
          </q-card-actions>
        </q-card>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script setup>
import { ref } from 'vue'

const email = ref('')
const password = ref('')

const login = () => {
  console.log('Login:', email.value, password.value)
}
</script>