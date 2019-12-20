package com.service.WareServices;

import com.model.LogisticInfo;
import com.pojo.dto.AddPurchaseOrderDTO;
import com.pojo.dto.PurchaseOrderDTO;

import java.util.List;

/**
 * 进货管理
 *
 * @author:HGF
 */
public interface PurchasingService {

    List<PurchaseOrderDTO> getPurchaseOrders();

    PurchaseOrderDTO getPurchaseOrder(int id);

    List<PurchaseOrderDTO> getPurchaseOrdersExtra();

    void confirmPurchase(int id,int userId);

    void addPurchaseOrder(AddPurchaseOrderDTO addPurchaseOrderDTO);

    void addLogisticInfo(int orderId,String content);

    List<LogisticInfo> getLogisticInfos(int orderId);
}
