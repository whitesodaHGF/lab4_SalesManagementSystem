package com.controller;

import com.config.JwtConfig;
import com.constant.URLConstant.WareURLConstant;
import com.pojo.dto.AddPurchaseOrderDTO;
import com.service.WareServices.PurchasingService;
import com.service.WareServices.ShippingService;
import com.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * 仓库管理员用例
 *
 * @author:HGF
 */
@RestController
@Slf4j
public class WareController {

    /** 
    * 进货管理 
    */ 
    @Autowired
    PurchasingService purchasingService;

    /** 
    * 发货管理
    */ 
    @Autowired
    ShippingService shippingService;

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
    * @description: 获取发货单列表
    */
    @GetMapping(WareURLConstant.WARE_SHIPPING+"/All")
    public CommonResponse getShippingOrders(){
        return CommonResponse.setOk(shippingService.getShippingOrders());
    }

    /**
     * @description: 获取发货单
     */
    @GetMapping(WareURLConstant.WARE_SHIPPING)
    public CommonResponse getShippingOrder(int id){
        return CommonResponse.setOk(shippingService.getShippingOrder(id));
    }


    /**
    * @description: 确认发货
    */
    @PutMapping(WareURLConstant.WARE_SHIPPING)
    public CommonResponse confirmShipment(@NotBlank int id, HttpServletRequest request){
        int userId=getIdFromToken(request);
        shippingService.confirmShipment(id,userId);
        return CommonResponse.setOk("确认发货成功");
    }

    /**
     * @description: 获取进货单列表
     */
    @GetMapping(WareURLConstant.WARE_PURCHASING+"/All")
    public CommonResponse getPurchaseOrders(){
        return CommonResponse.setOk(purchasingService.getPurchaseOrders());
    }

    /**
     * @description: 获取进货单
     */
    @GetMapping(WareURLConstant.WARE_PURCHASING)
    public CommonResponse getPurchaseOrder(int id){
        return CommonResponse.setOk(purchasingService.getPurchaseOrder(id));
    }

    /**
    * @description: 获取进货单列表(自动生成)
    */
    @GetMapping(WareURLConstant.WARE_PURCHASING+"/extra")
    public CommonResponse getPurchaseOrdersExtra(){
        return CommonResponse.setOk(purchasingService.getPurchaseOrdersExtra());
    }
    /** 
    * @description: 确认进货
    */
    @PutMapping(WareURLConstant.WARE_PURCHASING)
    public CommonResponse confirmPurchase(@NotBlank int id, HttpServletRequest request){
        int userId=getIdFromToken(request);
        purchasingService.confirmPurchase(id,userId);
        return CommonResponse.setOk("确认进货成功");
    }

    /**
    * @description:  添加进货单
    */
    @PostMapping(WareURLConstant.WARE_PURCHASING)
    public CommonResponse addPurchaseOrder(@Valid AddPurchaseOrderDTO purchaseOrderDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        purchasingService.addPurchaseOrder(purchaseOrderDTO);
        return CommonResponse.setOk("添加成功");
    }

    /** 
    * @description: 获取物流信息列表
    */
    @GetMapping(WareURLConstant.WARE_PURCHASING+"/logisticInfo/All")
    public CommonResponse getLogisticInfos(@NotBlank int orderId){
        return CommonResponse.setOk(purchasingService.getLogisticInfos(orderId));
    }

    /** 
    * @description: 添加物流信息
    */
    @PostMapping(WareURLConstant.WARE_PURCHASING+"/logisticInfo")
    public CommonResponse addLogisticInfo(@NotBlank int orderId,@NotBlank String content){
        purchasingService.addLogisticInfo(orderId,content);
        return CommonResponse.setOk("添加物流信息成功");
    }
    
}
