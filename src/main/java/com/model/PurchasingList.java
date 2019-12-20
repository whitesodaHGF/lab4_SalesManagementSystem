package com.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "purchasing_list")
@ToString
@Data
public class PurchasingList {
    @Id
    private Integer id;

    /**
     * 合同编号
     */
    @Column(name = "contract_id")
    private Integer contractId;

    /**
     * 商品编号
     */
    @NotNull(message = "商品编号不能为空" )
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 总采购数量
     */
    @NotNull(message = "购买数量不能为空")
    private Integer quantity;

    /**
     * 已发货数量
     */
    @Column(name = "quantity_shipped")
    private Integer quantityShipped;
    
    /** 
    * 发货状态
    */
    private String status;
}