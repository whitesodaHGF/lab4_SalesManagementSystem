package com.service.SalesServices.Impl;

import com.mapper.ContractMapper;
import com.mapper.PurchasingListMapper;
import com.pojo.dto.PurchasingListDTO;
import com.pojo.dto.SalesStatisticDTO;
import com.service.SalesServices.QuerySalesPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询销售业绩
 *
 * @author:HGF
 */
@Service
public class QuerySalesPerformanceServiceImpl implements QuerySalesPerformanceService {

    @Autowired
    PurchasingListMapper purchasingListMapper;

    @Autowired
    ContractMapper contractMapper;

    /**
     * 销售人员根据选择条件返回返回所有已处理的采购单
     *
     * @param salesStatisticDTO
     */
    @Override
    public List<PurchasingListDTO> getSalesStatisticBySalesId(SalesStatisticDTO salesStatisticDTO, int salesId) {
        return purchasingListMapper.getSalesStatisticBySalesId(
                salesStatisticDTO.getStartDate(),
                salesStatisticDTO.getEndDate(),
                salesStatisticDTO.getCustomerName(),
                salesStatisticDTO.getProductName(),
                salesId);
    }

}
