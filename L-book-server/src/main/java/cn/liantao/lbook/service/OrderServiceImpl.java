package cn.liantao.lbook.service;

import cn.liantao.lbook.entity.DateOrder;
import cn.liantao.lbook.entity.Order;
import cn.liantao.lbook.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderMapper orderMapper;

    // 添加订单
    @Override
    public void addOrder(List<Order> orders) {
        int id;
        try {
            id = orderMapper.maxID() + 1;
        } catch(Exception e){
            id = 0;
        }

        for (int i=0;i<orders.size();i++) {
            int stock = orderMapper.selectStock(orders.get(i).getISBN());
            stock = stock - orders.get(i).getCount();
            if (stock >= 0) {
                orderMapper.setStock(orders.get(i).getISBN(), stock);
                orders.get(i).setId(id);
                orderMapper.addOrder(orders.get(i));
            }
        }
    }

    // 获取所有订单
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderMapper.getAllOrders();
        return orders;
    }

    // 获取指定用户订单
    @Override
    public List<Order> getOrder(String account) {
        List<Order> orders = orderMapper.getOrder(account);
        return orders;
    }

    // 搜索订单
    @Override
    public List<Order> searchOrder(String text) {
        String filter = "%"+text+"%";
        List<Order> orders= orderMapper.searchOrder(filter);
        return orders;
    }

    // 筛选指定日期内指定用户的订单
    @Override
    public List<DateOrder> dateFilter(String beginDate, String endDate, String account) {
        List<DateOrder> orders= orderMapper.dateFilter(beginDate,endDate,account);
        return orders;
    }

    // 筛选指定日期内指定用户的详细订单
    @Override
    public List<DateOrder> dateDetailFilter(String beginDate, String endDate, String account) {
        List<DateOrder> orders= orderMapper.dateDetailFilter(beginDate,endDate,account);
        return orders;
    }
}
