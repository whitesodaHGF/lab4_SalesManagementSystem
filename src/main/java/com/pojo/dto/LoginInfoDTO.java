package com.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录权限信息
 *
 * @author:HGF
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoDTO {
    private String username;
    private String role;
    private String originPwd;
    private String newPwd;
}
