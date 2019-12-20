package com.service.AdminServices.Impl;

import com.mapper.PurchasingListMapper;
import com.pojo.dto.PurchasingListDTO;
import com.pojo.dto.SalesStatisticDTO;
import com.service.AdminServices.SalesStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 销售数据统计
 *
 * @author:HGF
 */
@Service
public class SalesStatisticServiceImpl implements SalesStatisticService {

    @Autowired
    PurchasingListMapper purchasingListMapper;

    /**
     * 返回所有已处理的采购单
     *
     * @param salesStatisticDTO
     */
    @Override
    public List<PurchasingListDTO> getSalesStatistic(SalesStatisticDTO salesStatisticDTO) {
        return purchasingListMapper.getSalesStatistic(
                salesStatisticDTO.getStartDate(),
                salesStatisticDTO.getEndDate(),
                salesStatisticDTO.getCustomerName(),
                salesStatisticDTO.getProductName());
    }



}
