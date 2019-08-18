import {reqGetAllBook, reqSearchBook} from '../../api'

const state = {
    books: [],
    filteredBooks: []
}

const mutations = {
    updateBook(state, books) {
        state.books = books
    },

    updateFilteredBook(state, books) {
        state.filteredBooks = books
    }
}

const actions = {
    getAllBook({commit}) {
        reqGetAllBook().then( (data) => {
            commit('updateBook', data)
        })
    },

    searchBook({commit}, filter) {
        reqSearchBook(filter).then((data) => {
            commit('updateFilteredBook', data)
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
