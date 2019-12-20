package com.mapper;

import com.model.ShippingOrder;
import com.pojo.dto.ShippingOrderDTO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ShippingOrderMapper extends Mapper<ShippingOrder> {

    @Select("select shipping_order.id as id,\n" +
            "       purchasing_list.id as purchasingListId,\n" +
            "       product.id as productId,\n" +
            "       product.name as productName,\n" +
            "       shipping_order.quantity as quantity,\n" +
            "       shipping_order.shipping_date as shippingDate,\n" +
            "       shipping_order.receipt_address as receiptAddress,\n" +
            "       warehouse_manager_id as WareId,\n" +
            "       shipping_status as shippingStatus,\n" +
            "       shipping_order.status\n" +
            "from shipping_order,purchasing_list,product\n" +
            "where shipping_order.purchasing_list_id=purchasing_list.id\n" +
            "and product.id=purchasing_list.product_id")
    List<ShippingOrderDTO> getShippingOrders();

    @Select("<script>" +
            "select shipping_order.id as id,\n" +
            "       purchasing_list.id as purchasingListId,\n" +
            "       product.id as productId,\n" +
            "       product.name as productName,\n" +
            "       purchasing_list.quantity as quantity,\n" +
            "       shipping_order.shipping_date as shippingDate,\n" +
            "       shipping_order.receipt_address as receiptAddress,\n" +
            "       warehouse_manager_id as WareId,\n" +
            "       shipping_status as shippingStatus,\n" +
            "       shipping_order.status\n" +
            "from shipping_order,purchasing_list,product\n" +
            "where shipping_order.purchasing_list_id=purchasing_list.id\n" +
            "and product.id=purchasing_list.product_id" +
            "<if test='id!=null'>" +
            "and shipping_order.id=#{id}" +
            "</if>" +
            "</script>")
    ShippingOrderDTO getShippingOrder(int id);

    @Update("update shipping_order set status='已确认' where id=#{id}")
    void setStatus(int id);
}