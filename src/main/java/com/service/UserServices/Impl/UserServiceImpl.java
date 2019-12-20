package com.service.UserServices.Impl;

import com.Exception.CommonException;
import com.config.JwtConfig;
import com.enums.ResultEnum;
import com.mapper.TUserMapper;
import com.model.TUser;
import com.pojo.dto.UserAuthDTO;
import com.service.UserServices.UserService;
import com.utils.PwdUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录注册
 *
 * @author:HGF
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    TUserMapper tUserMapper;

    /**
     * Jwt认证工具类
     */
    @Autowired
    JwtConfig jwtConfig;

    /** 
    * @description: 登录认证 
    * @param:  userAuthDTO,response
    * @return:  boolean
    */ 
    @Override
    public int login(UserAuthDTO userAuthDTO, HttpServletResponse response) {
        // 新建一个实例，添加查询条件为字段名为username的值与查询值相等
        Example example=new Example(TUser.class);
        example.and().andEqualTo("username",userAuthDTO.getUsername());
        TUser user=tUserMapper.selectOneByExample(example);
        // 用户不存在
        if(user == null)
            throw new CommonException(ResultEnum.TOKEN_USERNAME_MISS);
        // 密码对比，密码存入时已加密
        String password= PwdUtil.passwordEncryption(userAuthDTO.getPassword());
        if(!user.getPassword().equals(password)){
            throw new CommonException(ResultEnum.TOKEN_PASSWORD_ERROR);
        }
        response.setHeader("ROLE",user.getRole());
        return user.getId();
    }

    /** 
    * @description: 更换密码
    * @param:  userAuthDTO
    */
    @Override
    @Transactional
    public void setPassword(UserAuthDTO userAuthDTO) {
        TUser user=tUserMapper.selectUserByName(userAuthDTO.getUsername());
        String password= PwdUtil.passwordEncryption(userAuthDTO.getPassword());
        log.info(userAuthDTO.getPassword());
        log.info(password);
        tUserMapper.updateUserPasswordById(user.getId(),password);
    }

    /**
    * @description: 获取用户权限
    * @param:  userAuthDTO
    * @return:  role
    */
    @Override
    public String getRole(UserAuthDTO userAuthDTO) {
        String password= PwdUtil.passwordEncryption(userAuthDTO.getPassword());
        Example example=new Example(TUser.class);
        example.and().andEqualTo("name",userAuthDTO.getUsername())
                .andEqualTo("password",password);
        TUser user=tUserMapper.selectOneByExample(example);
        // 用户不存在
        if(user == null)
            throw new CommonException(ResultEnum.TOKEN_USERNAME_MISS);
        return user.getRole();
    }

    /** 
    * @description: 检验token中用户信息是否与表单中信息一致
    * @param:  
    * @return:  
    */ 
    @Override
    public void isTokenValid(UserAuthDTO userAuthDTO, String token) {
        TUser user=tUserMapper.selectUserByName(userAuthDTO.getUsername());
        if(user==null){
            throw new CommonException(ResultEnum.TOKEN_USERNAME_MISS);
        }
        JwtConfig jwtConfig=new JwtConfig();
        Claims claims=jwtConfig.getTokenClaim(token);
        String username=claims.getSubject();
        if(!username.equals(userAuthDTO.getUsername())){
            throw new CommonException(ResultEnum.TOKEN_MISS);
        }
    }
}
