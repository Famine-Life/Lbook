import {reqGetUserState} from '../../api'

const state = {
    isManager: false,
    isLogin: false,
    account: '',
    userStates: [],
}

const mutations = {
    changeManager (state) {
        state.isManager = !state.isManager
    },
    changeLogin (state) {
        state.isLogin = !state.isLogin
    },

    updateUserState (state, userStates) {
        state.userStates = userStates
    },

    setAccount (state, account) {
        state.account = account
    }
}

const actions = {
    getUerState ({commit}) {
        reqGetUserState().then((data) => {
            commit('updateUserState', data)
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
