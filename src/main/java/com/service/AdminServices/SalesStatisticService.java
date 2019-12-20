package com.service.AdminServices;

import com.pojo.dto.PurchasingListDTO;
import com.pojo.dto.SalesStatisticDTO;

import java.util.List;

/**
 * 销售数据统计
 *
 * @author:HGF
 */
public interface SalesStatisticService {
    
    /** 
    * 根据选择条件返回返回所有已处理的采购单
    */
    List<PurchasingListDTO> getSalesStatistic(SalesStatisticDTO salesStatisticDTO);



}
