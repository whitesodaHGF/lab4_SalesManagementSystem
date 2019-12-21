package com.service.WareServices.Impl;

import com.Exception.CommonException;
import com.enums.ResultEnum;
import com.mapper.*;
import com.model.*;
import com.pojo.dto.AddPurchaseOrderDTO;
import com.pojo.dto.PurchaseOrderDTO;
import com.service.WareServices.PurchasingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 进货管理
 *
 * @author:HGF
 */
@Service
@Slf4j
public class PurchasingServiceImpl implements PurchasingService {

    @Autowired
    PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    ShippingOrderMapper shippingOrderMapper;

    @Autowired
    PurchasingListMapper purchasingListMapper;

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    LogisticInfoMapper logisticInfoMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<PurchaseOrderDTO> getPurchaseOrders() {
        return purchaseOrderMapper.getPurchaseOrders();
    }

    @Override
    public PurchaseOrderDTO getPurchaseOrder(int id) {
        if(purchaseOrderMapper.getPurchaseOrder(id)!=null){
            return purchaseOrderMapper.getPurchaseOrder(id);
        }else if(purchaseOrderMapper.getPurchaseOrdersExtraById(id)!=null){
            return purchaseOrderMapper.getPurchaseOrdersExtraById(id);
        }else{
            throw new CommonException(ResultEnum.PURCHASEORDER_ID_MISS);
        }

    }

    @Override
    public List<PurchaseOrderDTO> getPurchaseOrdersExtra() {
        return purchaseOrderMapper.getPurchaseOrdersExtra();
    }

    /**
     * 自动补货数量
     */
    private static int supplynum=100;

    /**
    * @description: 确认进货
    */
    @Override
    @Transactional
    public void confirmPurchase(int id, int userId) {
        PurchaseOrder purchaseOrder=purchaseOrderMapper.selectByPrimaryKey(id);
        if(purchaseOrder==null) throw new CommonException(ResultEnum.PURCHASEORDER_ID_MISS);
         // 有采购单消息 生成发货单，等待仓库管理员发货
        if(purchaseOrder.getPurchasingListId()!=null){
            PurchasingList purchasingList=purchasingListMapper.selectByPrimaryKey(purchaseOrder.getPurchasingListId());
            Contract contract=contractMapper.selectByPrimaryKey(purchasingList.getContractId());
            log.info("有采购单消息 生成发货单，等待仓库管理员发货");
            ShippingOrder shippingOrder=new ShippingOrder();
            shippingOrder.setPurchasingListId(purchaseOrder.getPurchasingListId());
            shippingOrder.setStatus("待确认");
            shippingOrder.setShippingStatus("待发货");
            shippingOrder.setReceiptAddress(contract.getAddress());
            shippingOrder.setQuantity(purchaseOrder.getQuantity());
            shippingOrderMapper.insert(shippingOrder);

            // 修改采购单已发货数量
            purchasingList.setQuantityShipped(purchasingList.getQuantityShipped()+purchaseOrder.getQuantity());
            purchasingListMapper.updateByPrimaryKey(purchasingList);

            // 修改采购单状态
            if(purchasingList.getQuantityShipped().equals(purchasingList.getQuantity())){
                purchasingListMapper.setStatusOk(purchasingList.getId());
            }else{
                purchasingListMapper.setStatusToReady(purchasingList.getId());
            }
        }
        else{
            // 无采购单消息 增加对应商品库存
            log.info("无采购单消息 增加对应商品库存");
            Product product=productMapper.selectByPrimaryKey(purchaseOrder.getProductId());
            product.setQuantity(product.getQuantity()+supplynum);
            productMapper.updateByPrimaryKey(product);
        }
        // 处理进货单
        purchaseOrder.setStatus("已确认");
        purchaseOrder.setPurchaseDate(new Date());
        purchaseOrder.setWarehouseManagerId(userId);
        purchaseOrderMapper.updateByPrimaryKey(purchaseOrder);
    }

    @Override
    @Transactional
    public void addPurchaseOrder(AddPurchaseOrderDTO addPurchaseOrderDTO) {
        PurchaseOrder purchaseOrder=new PurchaseOrder();
        purchaseOrder.setProductId(addPurchaseOrderDTO.getProductId());
        purchaseOrder.setQuantity(addPurchaseOrderDTO.getQuantity());
        purchaseOrder.setStatus("待确认");
        purchaseOrderMapper.insert(purchaseOrder);
    }

    @Override
    @Transactional
    public void addLogisticInfo(int orderId, String content) {
        LogisticInfo logisticInfo=new LogisticInfo();
        logisticInfo.setOrderId(orderId);
        logisticInfo.setContent(content);
        logisticInfo.setDate(new Date());
        logisticInfoMapper.insert(logisticInfo);
    }

    @Override
    public List<LogisticInfo> getLogisticInfos(int orderId) {
        return logisticInfoMapper.getLogisticInfo(orderId);
    }


}
