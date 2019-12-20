package com.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "order")
@Data
public class Order {
    @Id
    private Integer id;

    /**
     * 采购清单编号
     */
    @Column(name = "purchasing_list_id")
    private Integer purchasingListId;

    /**
     * 付款状态: 0未付款 1已付款
     */
    @Column(name = "is_pay")
    private Byte isPay;
}