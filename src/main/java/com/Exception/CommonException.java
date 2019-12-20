package com.Exception;

import com.enums.ResultEnum;
import lombok.Getter;

/**
 * 异常处理
 *
 * @author:HGF
 */
@Getter
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -4340763954328969870L;

    private Integer code;

    public CommonException(Integer code,String message){
        super(message);
        this.code=code;
    }

    public CommonException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
    }

}
