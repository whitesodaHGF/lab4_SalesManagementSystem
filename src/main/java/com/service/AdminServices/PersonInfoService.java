package com.service.AdminServices;

import com.pojo.dto.LoginInfoDTO;
import com.pojo.dto.PersonInfoDTO;

/**
 * 个人信息管理
 *
 * @author:HGF
 */
public interface PersonInfoService {

    PersonInfoDTO getPersonInfo(int id);

    void updatePersonInfo(int id, PersonInfoDTO personInfo);

    LoginInfoDTO getLoginInfo(int id);

    void updateUserPassword(int id, String originPwd,String newPwd);

}
