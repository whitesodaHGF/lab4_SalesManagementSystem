package com.mapper;

import com.model.TUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TUserMapper extends Mapper<TUser> {

    @Select("select * from t_user where name like #{username} limit 0,1")
    TUser selectUserByName(String name);

    @Update("update t_user set password=#{password} where id=#{id}")
    int updateUserPasswordById(@Param(value = "id")int id,@Param(value = "password") String password);

    @Select("<script>" +
            "select id,username,password,card,birth,birthplace,sex,address,phone,role,actual_name as actualName " +
            "from t_user where role like #{role} " +
            "</script>")
    List<TUser> selectAllByRole(String role);

    @Select("<script>" +
            "select id,username,password,card,birth,birthplace,sex,address,phone,role,actual_name as actualName " +
            "from t_user where role like #{role} " +
            "<if test='id!=null'>" +
            "and id=#{id}" +
            "</if>" +
            "</script>")
    TUser selectUserById(@Param(value = "role") String role,@Param(value = "id") int id);
}