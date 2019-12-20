package com.service.AdminServices;

import com.model.TUser;

import java.util.List;

/**
 * 销售员工管理
 *
 * @author:HGF
 */
public interface SalesService {

    List<TUser> getSalesPeoples();

    TUser getSalesPeople(int id);

    void addSalesPeople(TUser user);

    void updateSalesPeople(TUser user);
}
