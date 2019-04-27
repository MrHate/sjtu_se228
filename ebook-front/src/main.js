import Vue from 'vue'
import VueRouter from 'vue-router'

import App from './App.vue'

import axios from 'axios'
import VueAxios from 'vue-axios'

import qs from 'qs'

import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import BookList from './components/BookList.vue'
import CartList from './components/CartList.vue'
import OrderList from './components/OrderList.vue'
import ManageList from './components/ManageList.vue'

import Modify from './components/Modify.vue'

import Login from './components/login.vue'
import Register from './components/signup.vue'

axios.defaults.baseURL = 'http://localhost:8090'
axios.defaults.withCredentials = true

Vue.use(VueRouter)
Vue.use(VueAxios, axios)
Vue.use(BootstrapVue)
Vue.prototype.$qs = qs;

Vue.config.productionTip = false

const routes = [
  { path: '/all', component: BookList },
  { path: '/all/:searchText', component: BookList, props:true },
  { path: '/cart', component: CartList },
  { path: '/orders', component: OrderList },
  { path: '/manage', component: ManageList },
  { path: '/modify/:bookid', component: Modify, props:true },
  { path: '/login', component: Login },
  { path: '/register', component: Register }
]

const router = new VueRouter({
  routes 
})

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')

