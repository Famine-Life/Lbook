<template>
    <div class="container-fluid">
        <div class="mt-5">
            书籍浏览
            <el-button type="primary" class="ml-3" v-if="isManager" @click="dialogVisible = !dialogVisible">
                添加书籍
            </el-button>
        </div>
        <ul  class="mt-5">
            <li v-for="(book, index) in books" :key="index" class="list-unstyled mb-5">
                <el-card shadow="hover">
                    <Book :book="book" :index="index"></Book>
                </el-card>
            </li>
        </ul>
        <el-dialog
                title="添加书籍"
                :visible.sync="dialogVisible"
                width="60%">
            <div>
                <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                    <el-form-item label="书名" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="作者" prop="author">
                        <el-input v-model="form.author"></el-input>
                    </el-form-item>
                    <el-form-item label="ISBN" prop="isbn">
                        <el-input v-model="form.isbn"></el-input>
                    </el-form-item>
                    <el-form-item label="简介" prop="outline">
                        <el-input v-model="form.outline" type="textarea" :rows="5"></el-input>
                    </el-form-item>
                    <el-form-item label="页数" prop="pages">
                        <el-row>
                            <el-col :span="24" class="mb-2"><el-input-number v-model="form.pages" controls-position="right" :min="1" size="mini"></el-input-number></el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="库存" prop="stock">
                        <el-row>
                            <el-col :span="24" class="mb-2"><el-input-number v-model="form.stock" controls-position="right" :min="1" size="mini"></el-input-number></el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="价格" prop="price">
                        <el-row>
                            <el-col :span="24" class="mb-2">
                                <el-input-number v-model="form.price" controls-position="right" :min="1" size="mini"></el-input-number>元
                            </el-col>
                        </el-row>
                    </el-form-item>
                    <el-form-item label="出版社" prop="press">
                        <el-input v-model="form.press"></el-input>
                    </el-form-item>
                    <el-form-item label="出版年" prop="year">
                        <el-input v-model="form.year"></el-input>
                    </el-form-item>
                    <el-form-item label="封面">
                        <el-row>
                            <el-col :span="10">
                                <el-upload
                                        :on-remove="onRemove"
                                        :on-success="onSuccess"
                                        :on-error="onError"
                                        :before-remove="beforeRemove"
                                        :http-request="httpRequest"
                                        list-type="picture"
                                        action="http://fuck"
                                        :file-list="fileList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
                                </el-upload>
                            </el-col>
                        </el-row>
                    </el-form-item>
                </el-form>
                <el-form>
                    <span>
                    <el-row>
                        <el-col :span="6" :offset="18">
                            <el-button type="primary" @click="addBook('form')">确认添加书籍</el-button>
                        </el-col>
                    </el-row>
                </span>
                </el-form>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Book from '../../components/Book/Book'
    import {mapState} from 'vuex'
    import {reqAddBook, reqDeleteImg} from '../../api'
    import axios from 'axios'

    export default {
        name: "Books",

        mounted() {
            this.$store.dispatch('Books/getAllBook')
        },

        components: {
            Book,
        },
        data () {
            return {
                dialogVisible: false,
                fileList: [],
                form: {
                    name: '',
                    author: '',
                    isbn: '',
                    outline: '',
                    stock: undefined,
                    price: undefined,
                    cover: '',
                    press: '',
                    year: undefined,
                    pages: undefined,
                },
                rules: {
                    name: [
                        {required: true, message: '请输入书名', trigger: 'change'},
                    ],
                    author: [
                        {required: true, message: '请输入作者', trigger: 'change'}
                    ],
                    isbn: [
                        {required:true, message: '请输入ISBN', trigger: 'change'}
                    ],
                    outline: [
                        {required:true, message: '请输入简介', trigger: 'change'}
                    ],
                    stock: [
                        {required:true, message: '请输入库存', trigger: 'change'}
                    ],
                    price: [
                        {required:true, message: '请输入价格', trigger: 'change'}
                    ],
                    press: [
                        {required:true, message: '请输入出版商', trigger: 'change'}
                    ],
                    year: [
                        {required:true, message: '请输入出版年', trigger: 'change'}
                    ],
                    pages: [
                        {required:true, message: '请输入页数', trigger: 'change'}
                    ],
                },
                imageUrl: '',
            }
        },
        computed: {
            ...mapState({
                isManager: state => state.Person.isManager,
                books: state => state.Books.books
            })
        },
        methods: {
            addBook (formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        reqAddBook(this.form).then( () => {
                            this.dialogVisible = !this.dialogVisible
                            this.$message.success("成功添加书籍！")
                            this.fileList = []
                            this.$store.dispatch('Books/getAllBook')
                        })
                    } else {
                        return false
                    }
                })
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
                this.form.cover = data
            },

            onRemove () {
                this.form.cover = null
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
        }
    }
</script>

<style scoped>
    .avatar-uploader {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
