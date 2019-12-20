package com.mapper;

import com.model.LogisticInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface LogisticInfoMapper extends Mapper<LogisticInfo> {
    @Select("select id,order_id as orderId,content,date from logistic_info where order_id=#{orderId}")
    List<LogisticInfo> getLogisticInfo(int orderId);
}