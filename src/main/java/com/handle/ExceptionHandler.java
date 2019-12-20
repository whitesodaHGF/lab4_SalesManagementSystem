package com.handle;

import com.Exception.CommonException;
import com.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 *
 * @author:HGF
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public CommonResponse handle(Exception e) {
        if (e instanceof CommonException) {
            CommonException ex = (CommonException) e;
            return CommonResponse.error(ex.getCode(),ex.getMessage());
        }else{
            log.error("系统异常{}",e);
            return CommonResponse.error(-1,"未知错误");
        }
    }

}
