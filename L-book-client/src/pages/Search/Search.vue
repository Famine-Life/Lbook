<template>
    <div class="container-fluid">
        <div class="mt-5">
            书籍搜索
        </div>
        <div class="row mt-5">
            <div class="mt-2">
                <i class="iconfont icon-sousuo col-md-9"></i>
            </div>
            <input placeholder="输入书名或者作者名来搜索" type="text" class="form-control col-md-10" v-model="filter">
            <button class="btn btn-primary btn-block ml-2 col-md-1" @click="searchBook">搜索</button>
        </div>
        <div v-show="!init">
            <ul class="mt-5" v-show="books.length > 0">
                <li v-for="(book, index) in books" :key="index" class="list-unstyled mb-5">
                    <Book :book="book" :index="index"></Book>
                </li>
            </ul>
            <div v-show="books.length === 0" class="container mt-5">
                <div class="row">
                    <div style="font-family: 等线" v-show="!init">
                        暂时没有您要搜索的书籍~
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import Book from '../../components/Book/Book'

    export default {
        name: "Search",
        components: {
            Book,
        },
        mounted() {
            this.filter = ""
            this.$store.dispatch('Books/searchBook', this.filter)
            this.init = true
        },
        data() {
            return {
                filter: "",
                init: true,
            }
        },
        computed: {
            ...mapState({
                books: state => state.Books.filteredBooks
            }),
        },
        methods: {
            searchBook() {
                this.$store.dispatch('Books/searchBook', this.filter)
                this.init = false
            }
        }
    }
</script>

<style scoped>

</style>
