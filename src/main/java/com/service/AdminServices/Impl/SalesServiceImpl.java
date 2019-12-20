package com.service.AdminServices.Impl;

import com.Exception.CommonException;
import com.constant.RoleConstant;
import com.enums.ResultEnum;
import com.mapper.TUserMapper;
import com.model.TUser;
import com.service.AdminServices.SalesService;
import com.utils.PwdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 销售人员管理
 *
 * @author:HGF
 */
@Service
@Slf4j
public class SalesServiceImpl implements SalesService {

    @Autowired
    TUserMapper userMapper;

    /** 
    * @description: 获取销售人员列表
    * @return:  List<TUser>
    */ 
    @Override
    public List<TUser> getSalesPeoples() {
        return userMapper.selectAllByRole(RoleConstant.SALES);
    }

    @Override
    public TUser getSalesPeople(int id) {
        TUser user=userMapper.selectUserById("Sales",id);
        if(user==null) throw new CommonException(ResultEnum.USER_ID_MISS);
        return user;
    }

    /** 
    * @description: 添加销售人员 
    * @param:  TUser
    */ 
    @Override
    @Transactional
    public void addSalesPeople(TUser user) {
        user.setRole(RoleConstant.SALES);
        user.setPassword(PwdUtil.passwordEncryption("12345678"));
        userMapper.insert(user);
    }

    /** 
    * @description: 修改销售人员信息
    * @param:  id,TUser
    */
    @Override
    @Transactional
    public void updateSalesPeople(TUser user) {
        TUser olduser=userMapper.selectByPrimaryKey(user.getId());
        user.setPassword(olduser.getPassword());
        user.setRole(olduser.getRole());
        userMapper.updateByPrimaryKey(user);
    }
}
