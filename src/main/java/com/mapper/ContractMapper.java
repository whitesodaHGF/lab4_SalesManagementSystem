package com.mapper;

import com.model.Contract;
import com.pojo.dto.PurchasingListDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Repository
public interface ContractMapper extends Mapper<Contract> {

    @Update("update contract set is_fulfill='履行中' where id=#{id}")
    void setExecuting(int id);

    @Update("update contract set is_fulfill='已完成' where id=#{id}")
    void setDone(int id);
    
    /** 
    * @description: 检查合同履行情况
    */
    @Select("select count(*)\n" +
            "from contract,purchasing_list\n" +
            "where contract.id=purchasing_list.contract_id\n" +
            "and status='已完成'\n" +
            "and contract_id=#{id}")
    int countDone(int id);

    @Select("select count(*)\n" +
            "from contract,purchasing_list\n" +
            "where contract.id=purchasing_list.contract_id\n" +
            "and contract_id=#{id}")
    int countAll(int id);

    @Select("<script>" +
            "select contract.id ,\n" +
            "                customer.name as customerName,\n" +
            "                t_user.actual_name salesName,\n" +
            "                contract.is_fulfill as status,\n" +
            "                sum(product.price*purchasing_list.quantity) as price\n" +
            "from contract,purchasing_list,t_user,customer,product\n" +
            "where contract.id=purchasing_list.contract_id\n" +
            "and t_user.id=contract.salespeople_id\n" +
            "and customer.id=contract.customer_id\n" +
            "and product.id=purchasing_list.product_id\n" +
            "<if test='customerName!=null'>" +
            "and customer.name like  CONCAT('%',#{customerName},'%')" +
            "</if>" +
            "<if test='startDate!=null and endDate!=null'>" +
            "and contract.signing_date between #{startDate} and #{endDate} " +
            "</if>" +
            "group by contract.id" +
            "</script>")
    List<PurchasingListDTO> getFulfillContract(@Param("startDate") Date startDate,
                                               @Param("endDate") Date endDate,
                                               @Param("customerName") String customerName,
                                               @Param("SalesId") int salesId);

}
