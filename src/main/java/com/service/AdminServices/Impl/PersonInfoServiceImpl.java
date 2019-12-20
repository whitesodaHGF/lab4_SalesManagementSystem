package com.service.AdminServices.Impl;

import com.Exception.CommonException;
import com.enums.ResultEnum;
import com.mapper.TUserMapper;
import com.model.TUser;
import com.pojo.dto.LoginInfoDTO;
import com.pojo.dto.PersonInfoDTO;
import com.service.AdminServices.PersonInfoService;
import com.utils.PwdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 个人信息管理
 *
 * @author:HGF
 */
@Service
@Slf4j
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    TUserMapper userMapper;

    /**
    * 实体类与DTO的数据转换
    */
    private TUser personInfo2User(TUser user,PersonInfoDTO personInfo) {
        user.setActualName(personInfo.getActualName());
        user.setSex(personInfo.getSex());
        user.setPhone(personInfo.getPhone());
//        Date date=DateUtil.getDate(personInfo.getBirth());
//        log.info(String.valueOf(date.getTime()));
        user.setBirth(personInfo.getBirth());
        user.setBirthplace(personInfo.getBirthplace());
        user.setAddress(personInfo.getAddress());
        return user;
    }

    /**
     * 实体类与DTO的数据转换
     */
    private PersonInfoDTO user2PersonInfo(TUser user){
        PersonInfoDTO personInfo=new PersonInfoDTO();
        personInfo.setActualName(user.getActualName());
        personInfo.setSex(user.getSex());
        personInfo.setPhone(user.getPhone());
        personInfo.setBirth(user.getBirth());
        personInfo.setCard(user.getCard());
        personInfo.setBirthplace(user.getBirthplace());
        personInfo.setAddress(user.getAddress());
        return personInfo;
    }

    /**
     * 实体类与DTO的数据转换
     */
    private LoginInfoDTO user2LoginInfo(TUser user){
        LoginInfoDTO loginInfoDTO=new LoginInfoDTO();
        loginInfoDTO.setUsername(user.getUsername());
        loginInfoDTO.setRole(user.getRole());
        return loginInfoDTO;
    }

    /** 
    * @description: 获取用户信息 
    * @param:  id
    * @return:  PersonInfoDTO
    */ 
    @Override
    public PersonInfoDTO getPersonInfo(int id) {
        TUser user=userMapper.selectByPrimaryKey(id);
        if(user==null) throw new CommonException(ResultEnum.USER_ID_MISS);
        return user2PersonInfo(user);
    }

    /** 
    * @description: updatePersonInfo
    * @param: id,personInfo
    */ 
    @Transactional
    @Override
    public void updatePersonInfo(int id, PersonInfoDTO personInfo){
        TUser user=userMapper.selectByPrimaryKey(id);
        TUser updatedUser=personInfo2User(user,personInfo);
        userMapper.updateByPrimaryKey(updatedUser);
    }

    /** 
    * @description: 获取登录信息 
    * @param:  id
    * @return: LoginInfoDTO 
    */ 
    @Override
    public LoginInfoDTO getLoginInfo(int id) {
        TUser user=userMapper.selectByPrimaryKey(id);
        if(user==null) throw new CommonException(ResultEnum.USER_ID_MISS);
        return user2LoginInfo(user);
    }

    /** 
    * @description: 修改登录密码
    * @param: id
    */
    @Transactional
    @Override
    public void updateUserPassword(int id, String originPwd,String newPwd) {
        TUser user= userMapper.selectByPrimaryKey(id);
        if(user==null) throw new CommonException(ResultEnum.USER_ID_MISS);
        if(!PwdUtil.passwordEncryption(originPwd).equals(user.getPassword()))
            throw new CommonException(ResultEnum.USER_SET_PASSWORD_ERROR);
        userMapper.updateUserPasswordById(id, PwdUtil.passwordEncryption(newPwd));
    }
}
