package com.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * 发货单管理
 *
 * @author:HGF
 */
@Data
public class ShippingOrderDTO {
    private int id;
    private int purchasingListId;
    private int productId;
    private String productName;
    private int quantity;
    private Date shippingDate;
    private String receiptAddress;
    private int wareId;
    private String shippingStatus;
    private String status;
    private double total;
}


