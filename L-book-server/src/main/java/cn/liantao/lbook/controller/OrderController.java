package cn.liantao.lbook.controller;

import cn.liantao.lbook.entity.*;
import cn.liantao.lbook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value="/order")
public class OrderController {

    // 创建连接数据库的接口实例
    @Autowired
    private OrderService orderService;

    // 监听'/order/get' 返回相应用户的订单数据
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public List<List<Order>> getOrder(@RequestBody User user) {
        String account = user.getAccount();
        List<Order> orders_raw = orderService.getOrder(account);
        List<List<Order>> orders = new LinkedList<>();
        List<Order> list = new LinkedList<>();

        if (orders_raw.size() > 0) {
            int id = orders_raw.get(0).getId();
            for (int i=0;i<orders_raw.size();i++){
                if (orders_raw.get(i).getId() != id){
                    orders.add(list);
                    list = new LinkedList<>();
                    list.add(orders_raw.get(i));
                    id = orders_raw.get(i).getId();
                } else {
                    list.add(orders_raw.get(i));
                }
            }
        }
        orders.add(list);

        return orders;
    }

    // 监听'/order/getall' 返回所有用户的订单数据
    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    @ResponseBody
    public OrderList getAllOrders() {
        List<Order> orders_raw = orderService.getAllOrders();
        List<List<Order>> orders = new LinkedList<>();
        List<Order> list = new LinkedList<>();
        int id = orders_raw.get(0).getId().intValue();
        for (int i=0;i<orders_raw.size();i++){
            if (orders_raw.get(i).getId().intValue() != id){
                orders.add(list);
                list = new LinkedList<>();
                list.add(orders_raw.get(i));
                id = orders_raw.get(i).getId();
            } else {
                list.add(orders_raw.get(i));
            }
        }
        orders.add(list);

        OrderList result = new OrderList();
        result.setOrders(orders);
        return result;
    }

    // 监听'/order/add' 添加订单，写入数据库
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public List<Order> addOrder(@RequestBody Orders orders) {

        int size = orders.getOrders().size();
        for (int i=0;i<size;i++) {
            Date date= new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            orders.getOrders().get(i).setDate(timestamp);
        }

        orderService.addOrder(orders.getOrders());

        List<Order> result = orderService.getOrder(orders.getOrders().get(0).getAccount());

        return result;
    }


    // 监听'/order/search' 接受一个参数 返回过滤后的订单数据
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public OrderList searchOrders(@RequestParam("filter") String filter) {
        List<Order> orders_raw = orderService.searchOrder(filter);
        List<List<Order>> orders = new LinkedList<>();

        if (orders_raw.size() > 0) {
            List<Order> list = new LinkedList<>();
            int id = orders_raw.get(0).getId().intValue();
            for (int i=0;i<orders_raw.size();i++){
                if (orders_raw.get(i).getId().intValue() != id){
                    orders.add(list);
                    list = new LinkedList<>();
                    list.add(orders_raw.get(i));
                    id = orders_raw.get(i).getId();
                } else {
                    list.add(orders_raw.get(i));
                }
            }
            orders.add(list);
        }

        OrderList result = new OrderList();
        result.setOrders(orders);
        return result;
    }

    // 监听'/order/date' 返回日期筛选后的订单数据
    @RequestMapping(value = "/date", method = RequestMethod.GET)
    @ResponseBody
    public List<DateOrder> dateFilter(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate, @RequestParam("account") String account) {
        // 将结束日期+1 便于sql操作
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date = calendar.getTime();
            endDate = format.format(date);
        } catch (Exception e){
            System.out.println(e);
        }

        return orderService.dateFilter(beginDate,endDate,account);
    }

    // 监听'/order/dateDetail' 返回日期筛选后的详细订单数据
    @RequestMapping(value = "/dateDetail", method = RequestMethod.GET)
    @ResponseBody
    public List<DateOrder> dateDetailFilter(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate, @RequestParam("account") String account) {
        // 将结束日期+1 便于sql操作
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(endDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            date = calendar.getTime();
            endDate = format.format(date);
        } catch (Exception e){
            System.out.println(e);
        }

        return orderService.dateDetailFilter(beginDate,endDate,account);
    }
}