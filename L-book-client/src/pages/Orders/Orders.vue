<template>
    <div class="container">
        <div class="row mt-5" v-if="isManager">
            <div class="mt-2">
                <i class="iconfont icon-sousuo col-md-9"></i>
            </div>
            <input placeholder="输入用户名来搜索" type="text" class="form-control col-md-10" v-model="filter">
            <button class="btn btn-primary btn-block ml-2 col-md-1" @click="searchOrder">搜索</button>
        </div>
        <ul class="list-unstyled">
            <li v-for="(order, index) in orders" :key="index">
                <Order :order="order"></Order>
            </li>
        </ul>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import Order from '../../components/Order/Order'

    export default {
        name: "Orders",
        mounted() {
            if (this.$store.state.Person.isManager) {
                this.$store.dispatch('Orders/getAllOrder')
            } else {
                this.$store.dispatch('Orders/getOrder', this.$store.state.Person.account)
            }
        },
        data() {
            return {
                filter: '',
            }
        },
        components: {
            Order
        },
        computed: {
            ...mapState({
                orders: state => state.Orders.orders,
                isManager: state => state.Person.isManager
            })
        },
        methods: {
            searchOrder() {
                this.$store.dispatch('Orders/searchOrder', this.filter)
            }
        }
    }
</script>

<style scoped>

</style>
