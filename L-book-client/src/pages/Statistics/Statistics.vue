<template>
    <div class="container mt-5">
        <el-row>
            <el-col :span="8">
                <div class="mb-2">开始日期</div>
                <el-date-picker
                        v-model="beginDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        :picker-options="beginPickerOptions"
                        placeholder="选择日期">
                </el-date-picker>
            </el-col>
            <el-col :span="8">
                <div class="mb-2">结束日期</div>
                <el-date-picker
                        v-model="endDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        :picker-options="endPickerOptions"
                        placeholder="选择日期">
                </el-date-picker>
            </el-col>
        </el-row>
        <ve-line :data="chartData" :settings="chartSettings" class="mt-5" v-if="datePicked"></ve-line>
        <div v-if="datePicked">
            <el-card  class="mt-5" v-for="(object, index) in activities" :key="index">
                <div>用户 {{object.account}} 的购买记录</div>
                <el-timeline :reverse="reverse" class="mt-4 mb-2">
                    <el-timeline-item
                            v-for="(activity, index) in object.list"
                            :key="index"
                            :timestamp="activity.timestamp">
                        <div class="text-wrapper">{{activity.content}}</div>
                    </el-timeline-item>
                </el-timeline>
            </el-card>
        </div>
    </div>
</template>

<script>
    import {reqDateOrderFilter, reqDateDetailOrderFilter} from "../../api"

    export default {
        name: "Statistics",
        data() {
            this.chartSettings = {
                axisSite: { right: ['购买书目'] },
                yAxisType: ['KMB', 'KMB'],
                yAxisName: ['元', '本']
            }
            return {
                beginDate: null,
                endDate: null,
                datePicked: false,
                reverse: true,
                beginPickerOptions: {
                    disabledDate: (time) => {
                        return this.dateConstrain(time, "BEGIN")
                    }
                },
                endPickerOptions: {
                    disabledDate: (time) => {
                        return this.dateConstrain(time, "END")
                    }
                },
                chartData: {
                    columns:['日期','花费金额', '购买书目'],
                    rows: []
                },
                activities: [],
            }
        },
        methods: {
            // 判断开始日期与结束日期时间跨度是否在1一个月内
            dateConstrain: function(time, order) {
                let date = new Date(time)

                if (date.getTime() >= Date.now())
                    return true

                if (this.beginDate != null || this.endDate != null) {
                    if (order == "BEGIN") {
                        let begin = new Date(time)
                        let end = new Date(this.endDate)
                        return this.amongOneMonth(begin, end)
                    } else {
                        let begin = new Date(this.beginDate)
                        let end = new Date(time)
                        return this.amongOneMonth(begin, end)
                    }
                }

                return false
            },

            amongOneMonth: function (begin, end) {
                if (begin.getTime() < end.getTime()
                    && (begin.getMonth() == end.getMonth()
                        || ((end.getMonth()-begin.getMonth() === 1 || (begin.getMonth() == 11 && end.getMonth() == 0))
                            && begin.getDate() >= end.getDate())))
                    return false
                return true
            }
        },
        computed: {
            // 计算两个日期之间的天数
            dateDiff: function () {
                if (this.beginDate != null && this.endDate != null) {
                    let beginDate = new Date(this.beginDate)
                    let endDate = new Date(this.endDate)
                    let minus = endDate.getTime()-beginDate.getTime()
                    let days = parseInt(minus / (1000*60*60*24))
                    return days+1
                } else {
                    return null
                }
            },
        },
        watch: {
            // 当用户选择完开始日期与结束日期后，对v-charts表中数据进行更新
            dateDiff() {
                this.chartData.rows = []
                // 获取日期筛选后的订单数据
                reqDateOrderFilter(this.beginDate,this.endDate, this.$store.state.Person.account)
                    .then((data) => {
                        let date = new Date(this.beginDate)
                        for (let i=0;i<this.dateDiff;i++) {
                            let exist = false
                            for (let order of data) {
                                let orderDate = new Date(order.date)
                                if (orderDate.getDate() == date.getDate()) {
                                    exist = true
                                    this.chartData.rows.push({
                                        '日期': (date.getMonth()+1).toString()+"/"+date.getDate(),
                                        '花费金额': order.amount,
                                        '购买书目': order.count
                                    })
                                    break
                                }
                            }

                            // 处理未购买书的天数
                            if (!exist) {
                                this.chartData.rows.push({
                                    '日期': (date.getMonth()+1).toString()+"/"+date.getDate(),
                                    '花费金额': 0,
                                    '购买书目': 0
                                })
                            }
                            date.setDate(date.getDate() + 1)
                        }
                        // 获取日期筛选后的订单详细数据，用于制作时间线
                        reqDateDetailOrderFilter(this.beginDate,this.endDate, this.$store.state.Person.account).then((data) => {
                            let list = []
                            let accountList = []
                            let dateObject = {content: ''}
                            let accountObject = {}
                            if (data.length > 0) {
                                dateObject.timestamp = data[0].date
                                accountObject.account = data[0].account
                                for (let order of data) {
                                    if (order.account == accountObject.account) {
                                        if (order.date == dateObject.timestamp) {
                                            dateObject.content += "购买《"+order.bookName+"》×"+order.count+"  花费"+Math.round(order.amount*100)/100+"元 \n"
                                        } else {
                                            accountList.push(dateObject)
                                            dateObject = {content: ''}
                                            dateObject.timestamp = order.date
                                            dateObject.content += "购买《"+order.bookName+"》×"+order.count+"  花费"+Math.round(order.amount*100)/100+"元 \n"
                                        }
                                    } else {
                                        accountList.push(dateObject)
                                        accountObject.list = accountList
                                        list.push(accountObject)
                                        accountObject = {}
                                        accountObject.account = order.account
                                        accountList = []
                                        dateObject = {content: ''}
                                        dateObject.timestamp = order.date
                                        dateObject.content += "购买《"+order.bookName+"》×"+order.count+"  花费"+Math.round(order.amount*100)/100+"元 \n"
                                    }
                                }
                                accountList.push(dateObject)
                                accountObject.list = accountList
                                list.push(accountObject)
                            }

                            this.activities = list
                            this.datePicked = true
                        })
                    })
            },
        }
    }
</script>

<style scoped>
    .text-wrapper {
        white-space: pre-wrap;
    }
</style>
