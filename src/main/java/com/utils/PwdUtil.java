package com.utils;

import com.Exception.CommonException;
import com.enums.ResultEnum;
import org.springframework.util.DigestUtils;

/**
 * 字符串加密工具类
 *
 * @author:HGF
 */
public class PwdUtil {
    /**
     * 加长需要加密的字符串，然后进行md5可以降低被解密的概率
     * 注意：改变盐值，会导致加密后的值改变
     */
    private final static String salt = "password_md5_salt_josuke_!@#)(*$&%^";

    /**
     * 私有化构造函数，不能创建对象
     */
    private PwdUtil() {}

    // 框架自带md5加密类
    public static String md5(String text) {
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

    public static boolean isEmpty(String text) {
        return text == null || text.isEmpty();
    }

    /**
     * 密码加密算法，不可逆
     * @param text
     * @return
     */
    public static String passwordEncryption(String text) {
        if (isEmpty(text)) {
            throw new CommonException(ResultEnum.USER_SET_PASSWORD_MISS);
        }
        return md5(text + salt);
    }
}
