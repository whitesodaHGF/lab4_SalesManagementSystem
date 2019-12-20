package com.service.SalesServices.Impl;

import com.mapper.ContractMapper;
import com.pojo.dto.PurchasingListDTO;
import com.pojo.dto.SalesStatisticDTO;
import com.service.SalesServices.QueryContractExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询合同管理
 *
 * @author:HGF
 */
@Service
public class QueryContractExecutionServiceImpl implements QueryContractExecutionService {

    @Autowired
    ContractMapper contractMapper;


    /**
     * 销售人员根据选择条件返回返回所有已处理的合同
     *
     * @param salesStatisticDTO
     * @param salesId
     */
    @Override
    public List<PurchasingListDTO> getFulfillContract(SalesStatisticDTO salesStatisticDTO, int salesId) {
        return contractMapper.getFulfillContract(salesStatisticDTO.getStartDate(),
                                                    salesStatisticDTO.getEndDate(),
                                                    salesStatisticDTO.getCustomerName(),
                                                    salesId);
    }
}
