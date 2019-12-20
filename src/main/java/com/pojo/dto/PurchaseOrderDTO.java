package com.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author:HGF
 */
@Data
public class PurchaseOrderDTO {
    private int id;
    private int purchasingListId;
    private int productId;
    private String productName;
    private int quantity;
    private Date purchaseDate;
    private int wareId;
    private String status;
}
