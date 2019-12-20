package com.intercepter;

import com.Exception.CommonException;
import com.annotation.PassToken;
import com.config.JwtConfig;
import com.enums.ResultEnum;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 拦截请求，检验有无token
 *
 * @author:HGF
 */
@Component
@Slf4j
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("TokenInterceptor");
        // 如果请求没有映射到方法上，就直接通过
        if (!(handler instanceof HandlerMethod)) return true;

        // 如果方法上有PassToken注解，也放行
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) return true;
        }

        // Token验证
        String token=request.getHeader(jwtConfig.getHeader());
        if(StringUtils.isEmpty(token)){
            token=request.getHeader(jwtConfig.getHeader());
        }
        if (StringUtils.isEmpty(token)) {
            throw new CommonException(ResultEnum.TOKEN_MISS);
        }
        // 检查登录信息是否失效
        Claims claims=jwtConfig.getTokenClaim(token);
        if(claims==null || jwtConfig.isTokenExpired(claims.getExpiration())){
            throw new CommonException(ResultEnum.TOKEN_EXPIRED);
        }
        //设置 identityId 用户身份ID
        request.setAttribute(jwtConfig.getHeader(),claims.getSubject());
        return true;
    }
}
