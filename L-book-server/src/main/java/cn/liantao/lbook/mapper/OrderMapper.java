package cn.liantao.lbook.mapper;

import cn.liantao.lbook.entity.DateOrder;
import cn.liantao.lbook.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    List<Order> getAllOrders();
    List<Order> searchOrder(String filter);
    List<Order> getOrder(String account);
    int setStock(String ISBN, int stock);
    int maxID();
    int selectStock(String ISBN);
    void addOrder(Order order);
    List<DateOrder> dateFilter(String beginDate, String endDate, String account);
    List<DateOrder> dateDetailFilter(String beginDate, String endDate, String account);
}
