package com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Table(name = "t_user")
@Data
@Setter
@Getter
public class TUser {
    @Id
    private Integer id;

    /**
     * 姓名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 身份证
     */
    private String card;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;

    /**
     * 出生地
     */
    private String birthplace;

    /**
     * 性别
     */
    private String sex;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    @JsonIgnore
    private String role;

    @Column(name = "actual_name")
    private String actualName;
}