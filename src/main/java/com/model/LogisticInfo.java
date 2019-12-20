package com.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "logistic_info")
@Data
public class LogisticInfo {
    @Id
    private Integer id;

    /**
     * 关联发货单编号
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 物流内容
     */
    private String content;

    private Date date;

}