package com.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "contract")
@Data
public class Contract {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    /**
     * 客户编号
     */
    @Column(name = "customer_id")
    @NotNull(message = "客户编号不能为空")
    private Integer customerId;

    /**
     * 销售人员工号
     */
    @Column(name = "salespeople_id")
    @NotNull(message = "销售人员工号不能为空")
    private Integer salespeopleId;

    /**
     * 合同履行状态: 0未履行 1履行中 2 已履行
     */
    @Column(name = "is_fulfill")
    private String isFulfill;

    /**
     * 签订日期
     */
    @Column(name = "signing_date")
    @NotNull(message = "签订日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signingDate;

    /**
     * 生效日期
     */
    @Column(name = "effective_date")
    @NotNull(message = "生效日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date effectiveDate;

    /**
     * 失效日期
     */
    @Column(name = "expiration_date")
    @NotNull(message = "失效日期不能为空")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    /**
     * 合同描述
     */
    private String description;

    /**
    * 发货地址
    */
    @NotNull(message = "发货地址不能为空")
    private String address;

}