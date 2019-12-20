package com.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "purchase_order")
@Data
public class PurchaseOrder {
    @Id
    private Integer id;

    /**
     * 货物编码
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 进货日期
     */
    @Column(name = "purchase_date")
    private Date purchaseDate;

    /**
     * 仓库管理员编号
     */
    @Column(name = "warehouse_manager_id")
    private Integer warehouseManagerId;

    /**
     * 采购清单编号
     */
    @Column(name = "purchasing_list_id")
    private Integer purchasingListId;

    private String status;

   }