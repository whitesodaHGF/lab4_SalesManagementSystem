package com.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "product")
@Data
public class Product {
    @Id
    private Integer id;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 售价
     */
    private Double price;
}