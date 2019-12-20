package com.enums;

/**
 * 返回值枚举类
 *
 * @author:HGF
 */
public enum ResultEnum {
    TOKEN_MISS(100, "登录信息不存在，请重新登录"),
    TOKEN_EXPIRED(101, "登录信息失效，请重新登录"),
    TOKEN_ERROR(102, "生成登录信息错误，请重新登录"),
    TOKEN_USERNAME_MISS(103, "用户名不存在"),
    TOKEN_PASSWORD_ERROR(104, "密码错误"),
    USER_ID_MISS(105, "用户不存在"),
    USER_SET_PASSWORD_MISS(106, "新密码不能为空"),
    USER_SET_PASSWORD_ERROR(107, "旧密码与实际密码不符"),
    CUSTOMER_ID_MISS(108, "客户不存在"),
    PRODUCT_ID_MISS(109,"商品不存在"),
    CONTRACT_ID_MISS(110,"合同不存在"),
    CONTRACT_UPDATE_ERROR(110,"合同已经处于不可更改状态"),
    CONTRACT_DATE_ERROR1(110,"合同生效时间不可以晚于失效时间"),
    CONTRACT_DATE_ERROR2(110,"合同签订日期不可大于当前日期"),
    PURCHASINGLIST_ID_ERROR(110,"采购清单不存在"),
    SHIPPINGORDER_ID_MISS(111,"发货单不存在"),
    PURCHASEORDER_ID_MISS(112,"进货单不存在"),
    ADDDILIVERY_ERROR1(113,"发货数量不可为零"),
    ADDDILIVERY_ERROR2(114,"发货数量不可大于总采购数量"),
    ADDDILIVERY_ERROR3(115,"发货数量不可大于待发货数量"),


    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
