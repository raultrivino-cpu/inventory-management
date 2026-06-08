import { defineStore } from 'pinia'
import api from 'src/services/api'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    login: localStorage.getItem('login') || '',
    role: localStorage.getItem('role') || '',
    authorization: localStorage.getItem('authorization') || '',
  }),

  getters: {
    isAuthenticated: (state) => !!state.authorization,
    isAdmin: (state) => state.role === 'ROLE_ADMIN',
    isExternal: (state) => state.role === 'ROLE_EXTERNAL',
  },

  actions: {
    async signIn(login, password) {
        const authorization = `Basic ${btoa(`${login}:${password}`)}`

        const response = await api.get('/auth/me', {
            headers: {
            Authorization: authorization,
            },
        })

        const currentUser = response.data

        this.login = currentUser.login
        this.role = currentUser.rol
        this.authorization = authorization

        localStorage.setItem('login', this.login)
        localStorage.setItem('role', this.role)
        localStorage.setItem('authorization', this.authorization)

        return currentUser
    }, 

    logout() {
      this.login = ''
      this.role = ''
      this.authorization = ''

      localStorage.removeItem('login')
      localStorage.removeItem('role')
      localStorage.removeItem('authorization')
    },
  },
})