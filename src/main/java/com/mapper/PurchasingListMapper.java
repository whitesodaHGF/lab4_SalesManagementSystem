package com.mapper;

import com.model.PurchasingList;
import com.pojo.dto.PurchasingListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchasingListMapper extends Mapper<PurchasingList> {

    @Select("select id,contract_id as contractId,product_id as productId,quantity " +
            "from purchasing_list " +
            "where contract_id=#{id}")
    List<PurchasingList> getPurchasingListByContractId(int id);

    @Select("select purchasing_list.id,\n" +
            "       contract_id as contractId,\n" +
            "       customer.name as customerName,\n" +
            "       t_user.actual_name as salesName,\n" +
            "       product_id as productId,\n" +
            "       product.name as productName,\n" +
            "       purchasing_list.quantity as quantity,\n" +
            "       purchasing_list.quantity_shipped as quantityShipped,\n" +
            "       product.price,\n" +
            "       purchasing_list.status\n" +
            "from purchasing_list,product,contract,t_user,customer\n" +
            "where purchasing_list.product_id=product.id\n" +
            "and purchasing_list.contract_id=contract.id\n" +
            "and contract.salespeople_id=t_user.id\n" +
            "and contract.customer_id=customer.id\n")
    List<PurchasingListDTO> getPurchasingListDTOs();

    @Update("update purchasing_list set status='进货中' where id=#{id}")
    void setStatusToPurchasing(int id);

    @Update("update purchasing_list set status='待处理' where id=#{id}")
    void setStatusToReady(int id);

    @Update("update purchasing_list set status='已处理' where id=#{id}")
    void setStatusOk(int id);

    @Select("<script>" +
            "select purchasing_list.id,\n" +
            "       contract_id as contractId,\n" +
            "       customer.name as customerName,\n" +
            "       t_user.actual_name as salesName,\n" +
            "       product_id as productId,\n" +
            "       product.name as productName,\n" +
            "       purchasing_list.quantity as quantity,\n" +
            "       product.price,\n" +
            "       status\n" +
            "from purchasing_list,product,contract,t_user,customer\n" +
            "where purchasing_list.product_id=product.id\n" +
            "and purchasing_list.contract_id=contract.id\n" +
            "and contract.salespeople_id=t_user.id\n" +
            "and contract.customer_id=customer.id\n" +
            "<if test='customerName!=null'>" +
            "and customer.name like  CONCAT('%',#{customerName},'%')" +
            "</if>" +
            "<if test='productName!=null'>" +
            "and product.name like CONCAT('%',#{productName},'%')" +
            "</if>" +
            "<if test='startDate!=null and endDate!=null'>" +
            "and contract.signing_date between #{startDate} and #{endDate} " +
            "</if>" +
            "and purchasing_list.status='已处理'" +
            "</script>")
    List<PurchasingListDTO> getSalesStatistic(@Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate,
                                              @Param("customerName") String customerName,
                                              @Param("productName") String productName);

    @Select("<script>" +
            "select purchasing_list.id,\n" +
            "       contract_id as contractId,\n" +
            "       customer.name as customerName,\n" +
            "       t_user.actual_name as salesName,\n" +
            "       product_id as productId,\n" +
            "       product.name as productName,\n" +
            "       purchasing_list.quantity as quantity,\n" +
            "       product.price,\n" +
            "       status\n" +
            "from purchasing_list,product,contract,t_user,customer\n" +
            "where purchasing_list.product_id=product.id\n" +
            "and purchasing_list.contract_id=contract.id\n" +
            "and contract.salespeople_id=t_user.id\n" +
            "and contract.customer_id=customer.id\n" +
            "<if test='customerName!=null'>" +
            "and customer.name like  CONCAT('%',#{customerName},'%')" +
            "</if>" +
            "<if test='productName!=null'>" +
            "and product.name like CONCAT('%',#{productName},'%')" +
            "</if>" +
            "<if test='startDate!=null and endDate!=null'>" +
            "and contract.signing_date between #{startDate} and #{endDate} " +
            "</if>" +
            "and purchasing_list.status='已处理'" +
            "and contract.salespeople_id=#{SalesId}" +
            "</script>")
    List<PurchasingListDTO> getSalesStatisticBySalesId(@Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate,
                                              @Param("customerName") String customerName,
                                              @Param("productName") String productName,
                                              @Param("SalesId") int salesId);

}

