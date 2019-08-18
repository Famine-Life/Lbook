import {reqGetOrder, reqGetAllOrder, reqSearchOrder} from "../../api";

const state = {
    orders: [],
    filteredOrders: [],
}

const mutations = {
    updateOrder(state, orders) {
        state.orders = orders
    }
}

const actions = {
    getOrder({commit}, account) {
        reqGetOrder(account).then((data) => {
            const orders = []
            for (let items of data) {
                const order = []
                for (let item of items) {
                    const book = {
                        author: item.author,
                        name: item.bookName,
                        count: item.count,
                        price: item.price,
                        total: item.price*item.count,
                        cover: item.cover,
                        userName: item.userName,
                        date: item.date
                     }
                     order.push(book)
                }
                orders.push(order)
            }
            commit('updateOrder', orders)
        })
    },

    getAllOrder({commit}) {
        reqGetAllOrder().then((data) => {
            const orders = []
            for (let items of data.orders) {
                const order = []
                for (let item of items) {
                    const book = {
                        author: item.author,
                        name: item.bookName,
                        count: item.count,
                        price: item.price,
                        total: item.price*item.count,
                        cover: item.cover,
                        userName: item.userName,
                        date: item.date
                    }
                    order.push(book)
                }
                orders.push(order)
            }
            commit('updateOrder', orders)
        })
    },

    searchOrder({commit}, filter) {
        reqSearchOrder(filter).then((data) => {
            const orders = []
            for (let items of data.orders) {
                const order = []
                for (let item of items) {
                    const book = {
                        author: item.author,
                        name: item.bookName,
                        count: item.count,
                        price: item.price,
                        total: item.price*item.count,
                        cover: item.cover,
                        userName: item.userName,
                        date: item.date
                    }
                    order.push(book)
                }
                orders.push(order)
            }
            commit('updateOrder', orders)
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
