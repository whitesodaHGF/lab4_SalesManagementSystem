package com.service.UserServices;

import com.pojo.dto.UserAuthDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录注册
 *
 * @author:HGF
 */
public interface UserService {

    int login(UserAuthDTO userAuthDTO, HttpServletResponse response);

    void setPassword(UserAuthDTO userAuthDTO);

    String getRole(UserAuthDTO userAuthDTO);

    void isTokenValid(UserAuthDTO userAuthDTO,String token);
}
