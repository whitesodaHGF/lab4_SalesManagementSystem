package com.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Table(name = "customer")
@Data
@ToString
public class Customer {
    @Id
    private Integer id;

    /**
     * 姓名
     */
    @NotBlank(message = "客户姓名不能为空")
    private String name;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 客户描述
     */
    private String description;
}