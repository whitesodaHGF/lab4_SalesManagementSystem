package com.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 参与者个人信息
 *
 * @author:HGF
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoDTO {
    @NotBlank(message = "姓名不能为空")
    private String actualName;

    private String sex;

    @Size(min = 11,max = 11,message = "手机号长度不合法")
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private String card;
    private String birthplace;
    private String address;
}
