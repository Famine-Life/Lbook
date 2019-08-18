<template>
    <div class="container-fluid">
        <div class="row">
            <div class="imgbox col-md-2" style="cursor: pointer" @click="showDetail">
                <img v-lazy="cover" class="img-thumbnail" alt="Responsive image">
            </div>
            <div class="col-md-6" style="cursor: pointer"  @click="showDetail">
                <div class="card-link link">
                    <div class="name mt-3 mb-2">{{book.name}}</div>
                    <div class="author mb-1">作者:{{book.author}}</div>
                    <div class="outline">简介:{{book.outline}}</div>
                </div>
            </div>
            <div class="col-md-2">
                <div class="isbn mt-3 mb-5">ISBN:{{book.isbn}}</div>
                <div class="stock mb-2">库存   {{book.stock}}</div>
                <div class="price">{{Math.round(book.price*100)/100}}元</div>
            </div>
            <div class="col-md-2" v-if="!isManager">
                <div class="row mt-5 mb-2">
                    <i class="iconfont icon-jian col-md-3"
                       @click="substract"
                       :style="count>1? 'cursor: pointer':''"
                       :class="count>1?'text-danger':''"></i>
                    <div class="col-md-6 text-center">{{count}}</div>
                    <i class="iconfont icon-jia col-md-3"
                       @click="add"
                       :style="count<book.stock? 'cursor: pointer':''"
                       :class="count<book.stock?'text-danger':''"></i>
                </div>
                <button class="btn btn-danger btn-block mt-1" @click="addtocart">
                    加入购物车
                </button>
            </div>
            <div class="col-md-2" v-else>
                <button class="btn btn-primary btn-block mt-5" @click="showDetail">
                    修改信息
                </button>
                <button class="btn btn-danger btn-block mt-5" @click="deleteBook">
                    删除书籍
                </button>
            </div>
        </div>
        <hr>
        <el-dialog
                :title="isManager? '书籍信息修改':'书籍详情'"
                :visible.sync="dialogVisible"
                width="60%">
            <div v-if="!isManager">
                <div class="mt-2">
                    <div class="row mb-3">
                        <div class="imgbox col-md-4">
                            <img v-lazy="cover" class="img-thumbnail">
                        </div>
                        <div class="col-md-8">
                            <div class="name">
                                书名：{{detail.name}}
                            </div>
                            <div class="mt-1">
                                作者：{{detail.author}}
                            </div>
                            <div class="mt-1">
                                ISBN：{{newisbn}}
                            </div>
                            <div class="mt-1">
                                出版社:{{detail.press}}
                            </div>
                            <div class="mt-1">
                                出版年:{{detail.year}}
                            </div>
                            <div class="mt-1">
                                页数:{{detail.pages}}
                            </div>
                            <div class="mt-1">
                                库存:{{detail.stock}}
                            </div>
                        </div>
                    </div>
                    <div class="outline mt-1 ml-5 mr-5">
                        简介：{{detail.outline}}
                    </div>
                </div>
                <span slot="footer" class="dialog-footer">
                    <el-row>
                        <el-col :span="6" :offset="18">
                            <el-input-number v-model="count" controls-position="right" :min="1" :max="form.stock"  size="mini"></el-input-number>
                            <el-button type="danger" class="mr-2" @click="addtocart">加入购物车</el-button>
                        </el-col>
                    </el-row>
                </span>
            </div>
            <div v-else>
                <el-form ref="form" :model="detail" label-width="80px">
                    <el-form-item label="书名">
                        <el-input v-model="detail.name"></el-input>
                    </el-form-item>
                    <el-form-item label="作者">
                        <el-input v-model="detail.author"></el-input>
                    </el-form-item>
                    <el-form-item label="ISBN">
                        <el-input v-model="newisbn"></el-input>
                    </el-form-item>
                    <el-form-item label="简介">
                        <el-input v-model="detail.outline" type="textarea" :rows="5"></el-input>
                    </el-form-item>
                    <el-form-item label="库存">
                        <el-row>
                            <el-col :span="24" class="mb-2"><el-input-number v-model="detail.stock" controls-position="right" :min="1" size="mini"></el-input-number></el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="价格">
                        <el-row>
                            <el-col :span="24" class="mb-2">
                                <el-input-number v-model="detail.price" controls-position="right" :min="1" size="mini"></el-input-number>元
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="页数">
                        <el-row>
                            <el-col :span="24" class="mb-2">
                                <el-input-number v-model="detail.pages" controls-position="right" :min="1" size="mini"></el-input-number>页
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="出版社">
                        <el-input v-model="detail.press"></el-input>
                    </el-form-item>
                    <el-form-item label="出版年">
                        <el-row>
                            <el-col :span="24" class="mb-2">
                                <el-input-number v-model="detail.year" controls-position="right" :min="1" size="mini"></el-input-number>年
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="封面">
                        <el-row>
                            <el-col :span="15">
                                <el-upload
                                        :on-remove="onRemove"
                                        :on-success="onSuccess"
                                        :on-error="onError"
                                        :before-remove="beforeRemove"
                                        :http-request="httpRequest"
                                        list-type="picture"
                                        action="/api/upload"
                                        :file-list="fileList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                                </el-upload>
                            </el-col>
                        </el-row>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-row>
                        <el-col :span="6" :offset="18">
                            <el-button type="danger" @click="book_modify">确认修改</el-button>
                        </el-col>
                    </el-row>
                </span>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import {reqModifyBook, reqDeleteImg, reqGetBookDetail, reqDeleteBook} from "../../api"
    import axios from 'axios'

    export default {
        name: "Book",
        props: {
            book: Object,
            index: Number,
        },

        mounted() {
            if (this.book.cover != null && this.book.cover != "") {
                this.fileList = []
                this.fileList.push({
                    name: this.book.cover,
                    url : "/api/images/"+this.book.cover
                })
            }
        },
        data() {
            return {
                count: 1,
                dialogVisible: false,
                fileList: [],
                detail: {
                },
                form: {
                    name: this.book.name,
                    author: this.book.author,
                    isbn: this.book.isbn,
                    outline: this.book.outline,
                    stock: this.book.stock,
                    price: this.book.price,
                },
                newisbn: this.book.isbn,
                cover_return: null,
            }
        },
        methods: {
            substract () {
                if (this.count > 1)
                    this.count--
            },

            add () {
                if (this.count < this.book.stock) {
                    this.count++
                }
            },

            addtocart () {
                this.$store.dispatch('ShopCart/addtocart', {
                    cover: this.book.cover,
                    name: this.book.name,
                    author: this.book.author,
                    isbn: this.book.isbn,
                    count: this.count,
                    price: this.book.price,
                    money: this.count*this.book.price,
                    stock: this.book.stock,
                }).then(() => {
                    this.$message.success("成功加入购物车")
                }).catch(() => {
                    this.$message.error("超过库存，无法加入购物车")
                })
            },

            book_modify () {
                reqModifyBook(this.detail,this.newisbn,this.cover_return).then( () => {
                    this.$store.dispatch('Books/getAllBook')
                    this.form.isbn = this.newisbn
                    this.$message.success("成功修改书籍信息")
                    this.dialogVisible = !this.dialogVisible
                    this.fileList = []
                })
            },

            formUpdate() {
                this.form.name = this.book.name
                this.form.author=this.book.author
                this.form.isbn=this.book.isbn
                this.newisbn=this.book.isbn
                this.form.outline=this.book.outline
                this.form.stock=this.book.stock
                this.form.price=this.book.price
                this.cover_return = null

                if (this.book.cover != null && this.book.cover != "") {
                    let fileList = []
                    fileList.push({
                        name:this.book.cover,
                        url : "/api/images/"+this.book.cover
                    })
                    this.fileList = fileList
                }
            },

            beforeRemove (file) {
                reqDeleteImg(file.name).then((data) => {
                    if (data === "删除失败") {
                        return false
                    }
                }).catch( () => {
                    return false
                })
            },

            onSuccess (data) {
                this.cover_return = data
            },

            onRemove () {
                this.cover_return = null
            },

            onError (err) {
                this.$message.error(err)
            },

            httpRequest(data) {
                let file = data.file
                let url = '/api/upload'
                let formData = new FormData()
                formData.append("file",file)

                axios({
                    method: 'POST',
                    url: url,
                    headers: {
                        'Content-Type': 'multipart/form-data;charset=UTF-8'
                    },
                    data: formData
                }).then((response) => {
                    if (response.data != "文件过大" && response.data != "文件名已存在" && response.data != "不支持的文件类型")
                        data.onSuccess(response.data)
                    else {
                        data.onError(response.data)
                    }
                })
            },

            // ajax显示书籍详情
            showDetail () {
                reqGetBookDetail(this.book.isbn).then((data) => {
                    this.detail = data
                    this.dialogVisible = !this.dialogVisible
                })
            },

            deleteBook () {
                reqDeleteBook(this.book.isbn).then((data) => {
                    if (data == "删除成功") {
                        this.$store.dispatch('Books/getAllBook').then(() => {
                            this.$message.success(data)
                        })
                    } else {
                        this.$message.error(data)
                    }
                }).catch((data) => {
                    this.$message.error(data)
                })
            }
        },
        computed: {
            ...mapState({
                isManager: state => state.Person.isManager
            }),
            cover: function () {
                if (Object.keys(this.book).length != 0 && this.book.cover != null) {
                    let cover = "/api/images/"+ this.book.cover
                    return cover
                }

                return "./"
            }
        },

        watch: {
            book() {
                this.formUpdate()
            },
        }
    }
</script>

<style scoped>
    .imgbox{
        font-size: 0;
        width: 200px;
        height: 200px;
        text-align: center;
    }
    .imgbox img{
        max-height: 100%;
        max-width: 100%;
        vertical-align: middle;
    }

    .name{
        font-family: 黑体;
        font-size: 24px;
    }

    .author{
        font-family: 仿宋;
        font-size: 15px;
    }

    .outline{
        font-family: 等线;
        font-size: 12px;
        opacity: 0.6;
    }

    .isbn{
        font-size:10px;
        font-family: Arial;
    }

    .stock{
        font-size:15px;
        font-family: 仿宋;
    }

    .price{
        font-size: 20px;
        font-family: 黑体;
    }

    .link:hover{
        color: steelblue;
    }

</style>
