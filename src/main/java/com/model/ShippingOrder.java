package com.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "shipping_order")
@Data
public class ShippingOrder {
    @Id
    private Integer id;

    /**
     * 采购清单编号
     */
    @Column(name = "purchasing_list_id")
    private Integer purchasingListId;

    /**
     * 发货日期
     */
    @Column(name = "shipping_date")
    private Date shippingDate;

    /**
     * 收货地址
     */
    @Column(name = "receipt_address")
    private String receiptAddress;

    /**
     * 发货状态: 0未发货 1已发货 
     */
    @Column(name = "shipping_status")
    private String shippingStatus;

    /**
     * 仓库管理员编号
     */
    @Column(name = "warehouse_manager_id")
    private Integer warehouseManagerId;

    private String status;
    
    /** 
    * 数量
    */ 
    private Integer quantity;

   }