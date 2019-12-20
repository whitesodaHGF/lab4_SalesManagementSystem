package com.controller;

import com.config.JwtConfig;
import com.constant.URLConstant.AdminURLConstant;
import com.model.*;
import com.pojo.dto.SalesStatisticDTO;
import com.service.AdminServices.*;
import com.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * 销售管理员用例
 *
 * @author:HGF
 */

@RestController
@Slf4j
public class AdminController {
    /**
     * 合同管理
     */
    @Autowired
    ContractService contractService;
    /**
     * 商品管理
     */
    @Autowired
    ProductService productService;
    /**
     * 采购清单管理
     */
    @Autowired
    PurchasingListService purchasingListService;
    /**
     * 客户管理
     */
    @Autowired
    CustomerService customerService;
    /**
     * 销售人员管理
     */
    @Autowired
    SalesService salesService;
    /**
     * 销售数据统计
     */
    @Autowired
    SalesStatisticService statisticService;
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
     * @description: 获取销售人员列表
     */
    @GetMapping(AdminURLConstant.ADMIN_SALES + "/All")
        public CommonResponse getSalesPeoples() {
        return CommonResponse.setOk(salesService.getSalesPeoples());
    }

    /**
     * @description: 获取销售人员
     * @param: id
     */
    @GetMapping(AdminURLConstant.ADMIN_SALES)
    public CommonResponse getSalesPeople(@NotBlank int id) {
        return CommonResponse.setOk(salesService.getSalesPeople(id));
    }

    /**
     * @description: 添加销售人员
     * @param: user
     */
    @PostMapping(AdminURLConstant.ADMIN_SALES)
    public CommonResponse addSalesPeople(@Valid TUser user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        salesService.addSalesPeople(user);
        return CommonResponse.setOk("添加成功！");
    }

    /**
     * @description: 修改销售人员信息
     * @param: user
     */
    @PutMapping(AdminURLConstant.ADMIN_SALES)
    public CommonResponse updateSalesPeople(@Valid TUser user, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        salesService.updateSalesPeople(user);
        return CommonResponse.setOk("修改成功！");
    }

    /**
     * @description: 获取客户列表
     */
    @GetMapping(AdminURLConstant.ADMIN_CUSTOMER + "/All")
    public CommonResponse getCustomers() {
        return CommonResponse.setOk(customerService.getCustomers());
    }

    /**
     * @description: 获取客户
     * @param: id
     */
    @GetMapping(AdminURLConstant.ADMIN_CUSTOMER)
    public CommonResponse getCustomer(@NotBlank int id) {
        return CommonResponse.setOk(customerService.getCustomer(id));
    }

    /**
     * @description: 添加客户
     * @param: customer
     */
    @PostMapping(AdminURLConstant.ADMIN_CUSTOMER)
    public CommonResponse addCustomer(@Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError().getDefaultMessage()));
        }
        log.info(customer.toString());
        customerService.addCustomer(customer);
        return CommonResponse.setOk("添加成功！");
    }

    /**
     * @description: 修改客户信息
     * @param: customer
     */
    @PutMapping(AdminURLConstant.ADMIN_CUSTOMER)
    public CommonResponse updateCustomer(@Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError().getDefaultMessage()));
        }
        log.info(customer.toString());
        customerService.updateCustomer(customer);
        return CommonResponse.setOk("修改成功！");
    }

    /**
     * @description: 获取商品列表
     */
    @GetMapping(AdminURLConstant.ADMIN_PRODUCT + "/All")
    public CommonResponse getProducts() {
        return CommonResponse.setOk(productService.getProducts());
    }

    /**
     * @description: 获取商品
     * @param: id
     */
    @GetMapping(AdminURLConstant.ADMIN_PRODUCT)
    public CommonResponse getProduct(@NotBlank int id) {
        return CommonResponse.setOk(productService.getProduct(id));
    }

    /**
     * @description: 添加商品
     * @param: product
     */
    @PostMapping(AdminURLConstant.ADMIN_PRODUCT)
    public CommonResponse addProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info(product.toString());
        productService.addProduct(product);
        return CommonResponse.setOk("添加成功！");
    }

    /**
     * @description: 修改商品信息
     * @param: product
     */
    @PutMapping(AdminURLConstant.ADMIN_PRODUCT)
    public CommonResponse updateProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info(product.toString());
        productService.updateProduct(product);
        return CommonResponse.setOk("修改成功！");
    }

    /**
     * @description: 获取合同列表
     */
    @GetMapping(AdminURLConstant.ADMIN_CONTRACT + "/All")
    public CommonResponse getContracts() {
        return CommonResponse.setOk(contractService.getContracts());
    }

    /**
     * @description: 获取合同
     * @param: id
     */
    @GetMapping(AdminURLConstant.ADMIN_CONTRACT)
    public CommonResponse getContract(@NotBlank int id) {
        return CommonResponse.setOk(contractService.getContract(id));
    }

    /**
     * @description: 添加合同
     * @param: product
     */
    @PostMapping(AdminURLConstant.ADMIN_CONTRACT)
    public CommonResponse addContract(@Valid Contract contract,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info(contract.toString());
        int n=contractService.addContract(contract);
        return CommonResponse.setOk(n);
    }

    /**
     * @description: 修改合同信息
     * @param: product
     */
    @PutMapping(AdminURLConstant.ADMIN_CONTRACT)
    public CommonResponse updateContract(@Valid Contract contract, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info(contract.toString());
        contractService.updateContract(contract);
        return CommonResponse.setOk("修改成功！");
    }

    /**
     * @description: 获取采购清单列表
     */
    @GetMapping(AdminURLConstant.ADMIN_PURCHASINGLIST + "/All")
    public CommonResponse getPurchasingLists() {
        return CommonResponse.setOk(purchasingListService.getPurchasingLists());
    }

    /**
     * @description: 获取详细采购清单列表
     */
    @GetMapping(AdminURLConstant.ADMIN_PURCHASINGLIST + "/Full")
    public CommonResponse getPurchasingListDTOs() {
        return CommonResponse.setOk(purchasingListService.getPurchasingListDTOs());
    }

    /**
     * @description: 获取采购清单
     * @param: id
     */
    @GetMapping(AdminURLConstant.ADMIN_PURCHASINGLIST)
    public CommonResponse getPurchasingList(@NotBlank int id) {
        log.info(String.valueOf(id));
        return CommonResponse.setOk(purchasingListService.getPurchasingList(id));
    }

    /**
     * @description: 按合同名称获取采购清单
     * @param: id
     */
    @GetMapping(AdminURLConstant.ADMIN_PURCHASINGLIST+"/byContractId")
    public CommonResponse getPurchasingListByContractId(@NotBlank int id) {
        log.info(String.valueOf(id));
        return CommonResponse.setOk(purchasingListService.getPurchasingListByContractId(id));
    }

    /**
     * @description: 添加采购清单
     * @param: product
     */
    @PostMapping(AdminURLConstant.ADMIN_PURCHASINGLIST)
    public CommonResponse addPurchasingList(@Valid PurchasingList purchasingList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info(purchasingList.toString());
        // 设置开始时已发货数量为0
        purchasingList.setQuantityShipped(0);
        purchasingListService.addPurchasingList(purchasingList);
        return CommonResponse.setOk("添加成功！");
    }

    /**
     * @description: 修改采购清单信息
     * @param: product
     */
    @PutMapping(AdminURLConstant.ADMIN_PURCHASINGLIST)
    public CommonResponse updatePurchasingList(@Valid PurchasingList purchasingList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info(purchasingList.toString());
        purchasingList.setStatus("待处理");
        purchasingListService.updatePurchasingList(purchasingList);
        return CommonResponse.setOk("修改成功！");
    }
    
    /** 
    * @description: 删除采购清单信息
    * @param:  id
    */
    @DeleteMapping(AdminURLConstant.ADMIN_PURCHASINGLIST)
    public CommonResponse deletePurchasingList(@NotBlank int id){
        log.info("deletePurchasingList");
        log.info(String.valueOf(id));
        purchasingListService.deletePurchasingList(id);
        return CommonResponse.setOk("删除成功！");
    }

    /**
    * @description: 发货管理 - 添加发货单
    * @param:  purchasingListId
    */
    @PostMapping(AdminURLConstant.ADMIN_PURCHASINGLIST+"/addDelivery")
    public CommonResponse addDelivery(@NotBlank int id,@NotBlank int quantity) {
        log.info("addDelivery");
        return CommonResponse.setOk(purchasingListService.addDelivery(id,quantity));
    }

    /**
    * @description: 销售额统计
    * @param:
    * @return:
    */
    @GetMapping(AdminURLConstant.ADMIN_STATISTIC)
    public CommonResponse getSalesStatistic(SalesStatisticDTO salesStatisticDTO){
        log.info(salesStatisticDTO.toString());
        return CommonResponse.setOk(statisticService.getSalesStatistic(salesStatisticDTO));
    }
}

