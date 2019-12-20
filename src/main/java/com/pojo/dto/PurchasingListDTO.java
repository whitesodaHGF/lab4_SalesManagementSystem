package com.pojo.dto;

import lombok.Data;

/**
 * 采购单管理
 *
 * @author:HGF
 */
@Data
public class PurchasingListDTO {
    private String  id;
    private String  contractId;
    private String  customerName;
    private String  salesName;
    private String  productId;
    private String  productName;
    private String  quantity;
    private String  quantityShipped;
    private String  price;
    private String  status;
}
