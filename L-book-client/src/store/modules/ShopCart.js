const state = {
    books: [],
}

const mutations = {
    deletebook(state, index) {
        state.books.splice(index, 1)
    },

    add(state, index){
        state.books[index].count++
        state.books[index].money+= state.books[index].price
    },

    substract(state, index){
        state.books[index].count--
        state.books[index].money-= state.books[index].price
    },

    clearShopCart (state) {
        state.books = []
    }
}

const actions = {
    addtocart({state}, book) {
        return new Promise((resolve, reject) => {
            let repeat = false;
            state.books.forEach((item) => {
                if (book.name === item.name && book.author === item.author) {
                    if (book.count+item.count <= item.stock) {
                        item.money += book.money
                        item.count += book.count
                    } else {
                        reject("exceed")
                    }

                    repeat = true
                }
            })

            if (!repeat) {
                if (book.stock === 0) {
                    reject("exceed")
                } else {
                    state.books.push(book)
                }
            }

            resolve("succeed")
        })
    },
}


export default {
    namespaced: true,
    state,
    mutations,
    actions
}
