package com.controller;

import com.config.JwtConfig;
import com.constant.URLConstant.SalesURLConstant;
import com.pojo.dto.SalesStatisticDTO;
import com.service.SalesServices.QueryContractExecutionService;
import com.service.SalesServices.QuerySalesPerformanceService;
import com.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 销售人员用例
 *
 * @author:HGF
 */
@RestController
@Slf4j
public class SalesController {

    /** 
    * 查询合同执行情况 
    */ 
    @Autowired
    QueryContractExecutionService contractExecutionService;

    /** 
    * 查询销售业绩
    */ 
    @Autowired
    QuerySalesPerformanceService salesPerformance;

    /**
     * Jwt认证工具类
     */
    @Autowired
    JwtConfig jwtConfig;

    /**
     * @description: 获取请求头中的token及返回用户Id
     * @param: request
     * @return: id
     */
    private int getIdFromToken(HttpServletRequest request) {
        String token = request.getHeader(jwtConfig.getHeader());
        log.info(token);
        String id = jwtConfig.getTokenClaim(token).getSubject();
        return Integer.parseInt(id);
    }

    /** 
    * @description: 根据选择条件查询对应已完成的采购单
    */
    @GetMapping(SalesURLConstant.SALES_SALES_PERFORMANCE)
    public CommonResponse getSalesStatisticBySalesId(SalesStatisticDTO salesStatisticDTO, HttpServletRequest request){
        int id=getIdFromToken(request);
        return CommonResponse.setOk(salesPerformance.getSalesStatisticBySalesId(salesStatisticDTO,id));
    }

    /**
    * @description: 销售人员根据选择条件返回返回所有已处理的合同
    */
    @GetMapping(SalesURLConstant.SALES_CONTRACT_EXECUTE)
    public CommonResponse getFulfillContract(SalesStatisticDTO salesStatisticDTO, HttpServletRequest request){
        int id=getIdFromToken(request);
        return CommonResponse.setOk(contractExecutionService.getFulfillContract(salesStatisticDTO,id));
    }
}
