package com.service.AdminServices.Impl;

import com.Exception.CommonException;
import com.enums.ResultEnum;
import com.mapper.*;
import com.model.*;
import com.pojo.dto.PurchasingListDTO;
import com.service.AdminServices.PurchasingListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购清单管理
 *
 * @author:HGF
 */
@ConfigurationProperties(prefix = "config")
@Service
@Slf4j
public class PurchasingListServiceImpl implements PurchasingListService {

    @Autowired
    PurchasingListMapper purchasingListMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ShippingOrderMapper shippingOrderMapper;

    @Autowired
    PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    ContractMapper contractMapper;

    /**
     * 库存阈值
     */
    private static int threshold = 20;

    /**
     * 自动补货数量
     */
    private static int supplynum = 100;


    @Override
    public List<PurchasingList> getPurchasingLists() {
        return purchasingListMapper.selectAll();
    }

    @Override
    public List<PurchasingListDTO> getPurchasingListDTOs() {
        // 查询所有的采购清单列表，及有关数据
        return purchasingListMapper.getPurchasingListDTOs();
    }

    @Override
    public PurchasingList getPurchasingList(int id) {
        PurchasingList purchasingList = purchasingListMapper.selectByPrimaryKey(id);
        if (purchasingList == null) throw new CommonException(ResultEnum.PURCHASINGLIST_ID_ERROR);
        return purchasingList;
    }

    @Override
    public List<PurchasingList> getPurchasingListByContractId(int id) {
        return purchasingListMapper.getPurchasingListByContractId(id);
    }

    @Override
    @Transactional
    public void addPurchasingList(PurchasingList purchasingList) {
        purchasingList.setStatus("待处理");
        purchasingListMapper.insert(purchasingList);
    }

    @Override
    @Transactional
    public void updatePurchasingList(PurchasingList purchasingList) {
        log.info(purchasingList.toString());
        if (purchasingList.getId() != null) {
            purchasingListMapper.updateByPrimaryKey(purchasingList);
        } else {
            purchasingListMapper.insert(purchasingList);
        }
    }

    @Override
    @Transactional
    public void deletePurchasingList(int id) {
        purchasingListMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public String addDelivery(int id, int quantity) {
        String result=null;
        // 验证预发货数量及已发货数量及总采购数量之间的关系
        PurchasingList purchasingList = purchasingListMapper.selectByPrimaryKey(id);
        Product product = productMapper.selectByPrimaryKey(purchasingList.getProductId());
        int purchasingListQuantity = purchasingList.getQuantity();                              // 总采购数量
        int quantityShipped = purchasingList.getQuantityShipped();                              //已发货数量
        int quantityUnShipped = purchasingListQuantity - purchasingList.getQuantityShipped();   // 待发货数量
        int productQuantity = product.getQuantity();                                     // 库存
        log.info("总采购数量:"+purchasingListQuantity);
        log.info("已发货数量:"+quantityShipped);
        log.info("待发货数量:"+quantityUnShipped);
        log.info("库存:"+productQuantity);
        log.info("请求发货数量："+quantity);

        if (quantity == 0) {
            // 发货数量不可为零
            throw new CommonException(ResultEnum.ADDDILIVERY_ERROR1);
        } else if (quantity > purchasingListQuantity) {
            // 发货数量不可大于总采购数量
            throw new CommonException(ResultEnum.ADDDILIVERY_ERROR2);
        } else if (quantity > quantityUnShipped) {
            // 发货数量不可大于待发货数量
            throw new CommonException(ResultEnum.ADDDILIVERY_ERROR3);
        }

        Contract contract = contractMapper.selectByPrimaryKey(purchasingList.getContractId());

        // 库存大于等于待发货数量 || 库存小于待发货数量且库存小于待发货数量 ，可直接生成对应的发货单
        if(productQuantity >= quantityUnShipped || (productQuantity <= quantityUnShipped) &&(quantity <= productQuantity)){
            log.info(" // 库存大于等于待发货数量 || 库存小于待发货数量且库存小于待发货数量 ，可直接生成对应的发货单");
            // 生成发货单
            ShippingOrder shippingOrder = new ShippingOrder();
            shippingOrder.setPurchasingListId(id);
            shippingOrder.setReceiptAddress(contract.getAddress());
            shippingOrder.setShippingStatus("待发货");
            shippingOrder.setStatus("待确认");
            shippingOrder.setQuantity(quantity);
            shippingOrderMapper.insert(shippingOrder);
            // 修改商品库存信息
            product.setQuantity(productQuantity - quantity);
            log.info("库存："+(productQuantity - quantity));
            productMapper.updateByPrimaryKey(product);
            // 修改采购单中待发货数量
            purchasingList.setQuantityShipped(quantityShipped + quantity);
            // 检查采购单是否全部发货完成，是就改变状态为已处理
            if (purchasingList.getQuantity().equals(purchasingList.getQuantityShipped())) {
                purchasingList.setStatus("已处理");
            }
            purchasingListMapper.updateByPrimaryKey(purchasingList);
            result="处理成功，生成数量为"+shippingOrder.getQuantity()+"的发货单";
        }
            else {
            // 生成数量为库存的发货单，并返回消息提示
            log.info("生成数量为库存的发货单");
            if(productQuantity!=0){
                // 数量不为零时生成发货单
                ShippingOrder shippingOrder = new ShippingOrder();
                shippingOrder.setPurchasingListId(id);
                shippingOrder.setReceiptAddress(contract.getAddress());
                shippingOrder.setShippingStatus("待发货");
                shippingOrder.setStatus("待确认");
                shippingOrder.setQuantity(productQuantity);
                shippingOrderMapper.insert(shippingOrder);
                // 修改商品库存
                product.setQuantity(0);
                productMapper.updateByPrimaryKey(product);
                // 修改采购单中已发货数量
                purchasingList.setQuantityShipped(quantityShipped+productQuantity);
                purchasingListMapper.updateByPrimaryKey(purchasingList);
                result="处理成功，库存不足，生成数量为"+shippingOrder.getQuantity()+"的发货单,余量待仓库管理员进货";
            }else{
                purchasingList.setQuantityShipped(quantityShipped+(quantity-productQuantity));
                purchasingListMapper.updateByPrimaryKey(purchasingList);
                result="处理成功，库存不足，生成数量为"+(quantity-productQuantity)+"的进货单,待仓库管理员进货";
            }
            // 生成数量为发货单及采购数量之差的进货单
            log.info("生成数量为发货单及采购数量之差的进货单");
            log.info("数量为");
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setProductId(purchasingList.getProductId());
            purchaseOrder.setQuantity(quantity-productQuantity);
            log.info(String.valueOf(purchaseOrder.getQuantity()));
            purchaseOrder.setPurchasingListId(purchasingList.getId());
            purchaseOrder.setStatus("待确认");
            purchaseOrderMapper.insert(purchaseOrder);
        }

        // 商品库存小于阈值时，生成自动补货发货单
        if(product.getQuantity()<20){
            log.info("商品库存小于阈值时，生成自动补货发货单");
            PurchaseOrder purchaseOrder=new PurchaseOrder();
            purchaseOrder.setProductId(purchasingList.getProductId());
            purchaseOrder.setQuantity(100);
            purchaseOrder.setStatus("待确认");
            purchaseOrderMapper.insert(purchaseOrder);
        }

        // 改变合同履行情况
        contractMapper.setExecuting(purchasingList.getContractId());

        return result;
    }
}

// 采购清单: 待处理 | 已处理
// 发货单： 待确认 | 已确认 [物流信息]:未发货 | 已发货 |
// 进货单： 待确认 | 已确认

