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
      },
      {
        path: 'products',
        component: () => import('pages/ProductsPage.vue'),
      },
      {
        path: 'inventory',
        component: () => import('pages/InventoryPage.vue'),
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