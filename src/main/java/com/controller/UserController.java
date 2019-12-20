package com.controller;

import com.Exception.CommonException;
import com.annotation.PassToken;
import com.config.JwtConfig;
import com.constant.URLConstant.UserURLConstant;
import com.enums.ResultEnum;
import com.pojo.dto.PersonInfoDTO;
import com.pojo.dto.PwdDTO;
import com.pojo.dto.UserAuthDTO;
import com.service.AdminServices.PersonInfoService;
import com.service.UserServices.UserService;
import com.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.Objects;

/**
 * 用户登录管理
 *
 * @author:HGF
 */
@RestController
@Slf4j
//@Api(value="demo")
public class UserController {
    /**
    * 用户登录管理 
    */ 
    @Autowired
    UserService userService;

    /**
     * 个人信息管理
     */
    @Autowired
    PersonInfoService personInfoService;

    /**
    * Jwt认证工具类
    */
    @Autowired
    JwtConfig jwtConfig;

    /**
    * @description: 用户验证登录并生成token
    * @param:  userAuthDTO,bindingResult,response
    * @return:  CommonResponse
    */
    @PassToken
    @PostMapping(UserURLConstant.USER_LOGIN)
    public CommonResponse login(@Valid UserAuthDTO userAuthDTO, BindingResult bindingResult, HttpServletResponse response){
        log.info("login");
        // 检查信息填入是否符合要求
        if(bindingResult.hasErrors()){
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int id=userService.login(userAuthDTO,response);
        // 生成token返回
        log.info(String.valueOf(id));
        String token= jwtConfig.getToken(String.valueOf(id));
        if(StringUtil.isEmpty(token))
            throw new CommonException(ResultEnum.TOKEN_ERROR);
        response.setHeader(jwtConfig.getHeader(),token);
        return CommonResponse.setOk(token);
    }

    /**
    * @description: 获取请求头中的token及返回用户Id
    * @param:  request
    * @return:  id
    */
    private int getIdFromToken(HttpServletRequest request){
        String token=request.getHeader(jwtConfig.getHeader());
        log.info(token);
        String id=jwtConfig.getTokenClaim(token).getSubject();
        return Integer.parseInt(id);
    }

    /**
    * @description: 获取参与者个人信息
    * @param:  request
    * @return:  personInfo
    */
    @GetMapping(UserURLConstant.USER_PERSONINFO)
    public CommonResponse getPersonInfo(HttpServletRequest request){
        log.info("getPersonInfo");
        int id=getIdFromToken(request);
        return CommonResponse.setOk(personInfoService.getPersonInfo(id));
    }

    /**
    * @description: 修改参与者个人信息
    * @param:  personInfo
    */
    @PutMapping(UserURLConstant.USER_PERSONINFO)
    public CommonResponse putPersonInfo(@Valid PersonInfoDTO personInfo, BindingResult bindingResult, HttpServletRequest request) throws ParseException {
        if(bindingResult.hasErrors()){
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int id=getIdFromToken(request);
        personInfoService.updatePersonInfo(id,personInfo);
        return CommonResponse.setOk("修改成功！");
    }

    /**
    * @description:  获取用户登录信息
    * @param:  request
    * @return:  loginInfo
    */
    @GetMapping(UserURLConstant.USER_LOGININFO)
    public CommonResponse getLoginInfo(HttpServletRequest request){
        int id=getIdFromToken(request);
        return CommonResponse.setOk(personInfoService.getLoginInfo(id));
    }

    /**
    * @description: 修改用户登录密码
    * @param:  originPwd,newPwd,bindingResult,request
    */
    @PutMapping(UserURLConstant.USER_LOGININFO)
    public CommonResponse updateUserPassword(@Valid PwdDTO pwdDTO, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return CommonResponse.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        int id=getIdFromToken(request);
        personInfoService.updateUserPassword(id, pwdDTO.getOriginPwd(),pwdDTO.getNewPwd());
        return CommonResponse.setOk("修改密码成功！");
    }

}
