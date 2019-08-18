<template>
    <div class="container-fluid mb-5 mt-5 border">
        <div class="container-fluid mt-4 mb-4">
            <el-row class="mb-3 mt-3">
                <el-col :span="2"><i class="iconfont icon-dingdan"></i></el-col>
                <el-col :span="5" v-if="isManager"><div>用户名：{{order[0].userName}}</div></el-col>
                <el-col :span="12"><div>时间：{{order[0] != null?order[0].date:null}}</div></el-col>
            </el-row>
            <div v-for="(book, index) in order" :key="book.name" class="">
                <div class="row">
                    <div class="imgbox col-md-2">
                        <img v-lazy="urls[index]" class="img-thumbnail" alt="Responsive image">
                    </div>
                    <div class="col-md-4">
                        <div class="name mb-3 mt-3">
                            {{book.name}}
                        </div>
                        <div class="author">
                            {{book.author}}
                        </div>
                    </div>
                    <div class="col-md-3 row">
                        <div class="col-md-6">
                            ×{{book.count}}
                        </div>
                        <div class="col-md-6">
                            {{Math.round(book.price*100)/100}}元
                        </div>
                    </div>
                    <div class="col-md-2 text-right mt">
                        <div class="money text-danger">
                            ￥{{Math.round(book.total*100)/100}}
                        </div>
                    </div>
                </div>
                <hr>
            </div>
            <div class="text-left">
                <el-row>
                    <el-col :span="3" class="mt-1">总计</el-col>
                    <el-col :span="6" class="money text-danger mt-1">{{Math.round(total*100)/100}}元</el-col>
                </el-row>
            </div>
        </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    export default {
        name: "Order",
        props: {
            order: Array
        },
        computed: {
            ...mapState({
                isManager: state => state.Person.isManager
            }),
            urls: function () {
                let urls = []
                for (let book of this.order) {
                    if (Object.keys(book).length != 0 && book.cover != null) {
                        let url = "/api/images/"+book.cover
                        urls.push(url)
                        continue
                    }

                    urls.push("./")
                }

                return urls
            },
            total: function () {
                let total = 0
                for(let item of this.order) {
                    total += item.total
                }
                return total
            }
        }
    }
</script>

<style scoped>
    .imgbox{
        font-size: 0;
        width: 100px;
        height: 100px;
        text-align: center;
    }
    .imgbox img{
        max-height: 100%;
        max-width: 100%;
        vertical-align: middle;
    }

    .name{
        font-family: 黑体;
        font-size: 18px;
    }

    .author{
        font-family: 仿宋;
        font-size: 12px;
    }

    .total {
        font-family: 等线;
        font-size: 16px;
    }

    .money{
        font-weight: 500;
        font-size: 20px;
        font-family: tohoma,arial;
    }

</style>
