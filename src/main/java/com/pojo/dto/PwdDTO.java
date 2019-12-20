package com.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 修改密码
 *
 * @author:HGF
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PwdDTO {
    @NotBlank
    private String originPwd;
    @NotBlank
    private String newPwd;
}
