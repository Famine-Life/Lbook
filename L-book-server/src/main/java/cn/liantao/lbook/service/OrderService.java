package cn.liantao.lbook.service;

import cn.liantao.lbook.entity.DateOrder;
import cn.liantao.lbook.entity.Order;

import java.util.List;

public interface OrderService {

    // 添加订单
    void addOrder(List<Order> order);

    // 获取所有订单
    List<Order> getAllOrders();

    // 获取指定用户订单
    List<Order> getOrder(String account);

    // 搜索订单
    List<Order> searchOrder(String filter);

    // 筛选指定日期内指定用户的订单
    List<DateOrder> dateFilter(String beginDate, String endDate, String account);

    // 筛选指定日期内指定用户的详细订单
    List<DateOrder> dateDetailFilter(String beginDate, String endDate, String account);
}
