package com.mapper;

import com.model.PurchaseOrder;
import com.pojo.dto.PurchaseOrderDTO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PurchaseOrderMapper extends Mapper<PurchaseOrder> {
    @Select("select purchase_order.id,\n" +
            "       purchasing_list_id as purchasingListId,\n" +
            "       purchasing_list.product_id as productId,\n" +
            "       product.name as productName,\n" +
            "       purchase_order.quantity as quantity,\n" +
            "       purchase_date as purchaseDate,\n" +
            "       purchase_order.warehouse_manager_id as wareId,\n" +
            "       purchase_order.status as status\n" +
            "from purchase_order,purchasing_list,product\n" +
            "where purchase_order.purchasing_list_id=purchasing_list.id\n" +
            "and purchase_order.product_id=product.id")
    List<PurchaseOrderDTO> getPurchaseOrders();

    @Select("<script>" +
            "select purchase_order.id,\n" +
            "       purchasing_list_id as purchasingListId,\n" +
            "       purchasing_list.product_id as productId,\n" +
            "       product.name as productName,\n" +
            "       purchase_order.quantity as quantity,\n" +
            "       purchase_date as purchaseDate,\n" +
            "       purchase_order.warehouse_manager_id as wareId,\n" +
            "       purchase_order.status as status\n" +
            "from purchase_order,purchasing_list,product\n" +
            "where purchase_order.purchasing_list_id=purchasing_list.id\n" +
            "and purchase_order.product_id=product.id" +
            "<if test='id!=null'>" +
            "and purchase_order.id=#{id}" +
            "</if>" +
            "</script>")
    PurchaseOrderDTO getPurchaseOrder(int id);


    @Select("select purchase_order.id,\n" +
            "       product.id as productId,\n" +
            "       product.name as productName,\n" +
            "       purchase_order.quantity as quantity,\n" +
            "       purchase_date as purchaseDate,\n" +
            "       warehouse_manager_id as wareId,\n" +
            "       status as status\n" +
            "from purchase_order,product\n" +
            "where product.id=purchase_order.product_id")
    List<PurchaseOrderDTO> getPurchaseOrdersExtra();

    @Select("<script>" +
            "select purchase_order.id,\n" +
            "       product.id as productId,\n" +
            "       product.name as productName,\n" +
            "       purchase_order.quantity as quantity,\n" +
            "       purchase_date as purchaseDate,\n" +
            "       warehouse_manager_id as wareId,\n" +
            "       status as status\n" +
            "from purchase_order,product\n" +
            "where product.id=purchase_order.product_id" +
            "<if test='id!=null'>" +
            "and purchase_order.id=#{id}" +
            "</if>" +
            "</script>")
    PurchaseOrderDTO getPurchaseOrdersExtraById(int id);
}