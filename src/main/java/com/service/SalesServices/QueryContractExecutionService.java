package com.service.SalesServices;

import com.pojo.dto.PurchasingListDTO;
import com.pojo.dto.SalesStatisticDTO;

import java.util.List;

/**
 * 查询合同执行情况
 *
 * @author:HGF
 */
public interface QueryContractExecutionService {
    /**
     * 销售人员根据选择条件返回返回所有已处理的合同
     */
    List<PurchasingListDTO> getFulfillContract(SalesStatisticDTO salesStatisticDTO, int salesId);

}
