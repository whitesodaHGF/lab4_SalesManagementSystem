package com.pojo.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 查询销售额信息
 *
 * @author:HGF
 */
@Data
@ToString
public class SalesStatisticDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date StartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date EndDate;
    private String customerName;
    private String productName;
}
