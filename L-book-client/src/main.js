import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import axios from 'axios'
import VCharts from 'v-charts'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import VueLazyload from 'vue-lazyload'

Vue.use(ElementUI)
Vue.use(VCharts)
Vue.use(VueLazyload)

Vue.config.productionTip = false
Vue.prototype.axios = axios

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
