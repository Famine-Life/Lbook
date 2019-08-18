package cn.liantao.lbook.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderList {

    private List<List<Order>> orders;
}
