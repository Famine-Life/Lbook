<template>
    <div>
        <div class="container">
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        prop="account"
                        label="账户"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="用户名"
                        width="180">
                </el-table-column>
                <el-table-column
                        align="right"
                        prop="allow"
                        label="用户状态">
                    <template slot-scope="scope">
                        <div class="user_state">
                            <el-switch
                                    style="display: block"
                                    v-model="scope.row.allowed"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949"
                                    active-text="通行"
                                    inactive-text="禁用"
                                    @change="changeAllow(scope.row.account)">
                            </el-switch>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import {reqChangeUser} from "../../api";

    export default {
        name: "Users",
        data () {
            return {
            }
        },
        computed: {
            ...mapState({
                tableData: state => state.Person.userStates
            })
        },
        methods: {
            changeAllow (account) {
                reqChangeUser(account).then((data)=>{
                    if (data.account) {
                        this.$store.dispatch('Person/getUerState').then( () => {
                            this.$message.success("用户权限更改成功")
                        })
                    } else {
                        this.$message.error("用户权限更改失败")
                    }
                })
            }
        },
        mounted() {
            this.$store.dispatch('Person/getUerState')
        }
    }
</script>

<style scoped>
    .user_state{
        height: 28px;
    }
</style>
