const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {
        path: 'companies',
        component: () => import('pages/CompaniesPage.vue'),
        meta: { requiresAuth: true }
      },
      {
        path: 'products',
        component: () => import('pages/ProductsPage.vue'),
        meta: { requiresAuth: true, requiresAdmin: true }
      },
      {
        path: 'inventory',
        component: () => import('pages/InventoryPage.vue'),
        meta: { requiresAuth: true, requiresAdmin: true }
      },
    ],
  },
  {
    path: '/login',
    component: () => import('pages/LoginPage.vue'),
  },
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue'),
  },
]

export default routes