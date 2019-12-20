package com.service.SalesServices;

import com.pojo.dto.PurchasingListDTO;
import com.pojo.dto.SalesStatisticDTO;

import java.util.List;

/**
 * 查询销售业绩
 *
 * @author:HGF
 */
public interface QuerySalesPerformanceService {

    /**
     * 销售人员根据选择条件返回返回所有已处理的采购单
     */
    List<PurchasingListDTO> getSalesStatisticBySalesId(SalesStatisticDTO salesStatisticDTO,int salesId);

}
