package com.service.WareServices.Impl;

import com.Exception.CommonException;
import com.enums.ResultEnum;
import com.mapper.ContractMapper;
import com.mapper.LogisticInfoMapper;
import com.mapper.PurchasingListMapper;
import com.mapper.ShippingOrderMapper;
import com.model.LogisticInfo;
import com.model.PurchasingList;
import com.model.ShippingOrder;
import com.pojo.dto.ShippingOrderDTO;
import com.service.WareServices.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 发货管理
 *
 * @author:HGF
 */
@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    ShippingOrderMapper shippingOrderMapper;

    @Autowired
    PurchasingListMapper purchasingListMapper;

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    LogisticInfoMapper logisticInfoMapper;

    @Override
    public List<ShippingOrderDTO> getShippingOrders() {
        return shippingOrderMapper.getShippingOrders();
    }

    @Override
    public ShippingOrderDTO getShippingOrder(int id) {
        return shippingOrderMapper.getShippingOrder(id);
    }

    /** 
    * 确认发货
    */ 
    @Override
    @Transactional
    public void confirmShipment(int id,int userId) {
        ShippingOrder shippingOrder=shippingOrderMapper.selectByPrimaryKey(id);
        if(shippingOrder==null)
            throw new CommonException(ResultEnum.SHIPPINGORDER_ID_MISS);
        // 改变发货单的状态
        shippingOrder.setStatus("已确认");
        shippingOrder.setWarehouseManagerId(userId);
        shippingOrder.setShippingDate(new Date());
        shippingOrderMapper.updateByPrimaryKey(shippingOrder);
        // 检查合同履行情况，改变合同履行状态
        PurchasingList purchasingList=purchasingListMapper.selectByPrimaryKey(shippingOrder.getPurchasingListId());
        int contractId=purchasingList.getContractId();
        if(contractMapper.countAll(contractId)==contractMapper.countDone(contractId)){
            contractMapper.setDone(contractId);
        }
        // 添加物流信息为已发货
        LogisticInfo logisticInfo=new LogisticInfo();
        logisticInfo.setOrderId(shippingOrder.getId());
        logisticInfo.setDate(new Date());
        logisticInfo.setContent("已发货");
        logisticInfoMapper.insert(logisticInfo);
    }

}
