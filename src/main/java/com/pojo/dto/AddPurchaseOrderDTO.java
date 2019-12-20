package com.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author:HGF
 */
@Data
public class AddPurchaseOrderDTO {
    @NotNull
    private Integer productId;
    @NotNull
    private Integer quantity;
}
