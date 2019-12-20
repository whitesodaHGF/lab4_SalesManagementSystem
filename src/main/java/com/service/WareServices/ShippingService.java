package com.service.WareServices;

import com.pojo.dto.ShippingOrderDTO;

import java.util.List;

/**
 * 发货管理
 *
 * @author:HGF
 */
public interface ShippingService {

    List<ShippingOrderDTO> getShippingOrders();

    ShippingOrderDTO getShippingOrder(int id);

    void confirmShipment(int id,int userId);
}
