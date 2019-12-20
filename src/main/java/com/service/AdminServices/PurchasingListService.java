package com.service.AdminServices;

import com.model.PurchasingList;
import com.pojo.dto.PurchasingListDTO;

import java.util.List;

/**
 * 采购清单管理
 *
 * @author:HGF
 */
public interface PurchasingListService {

    List<PurchasingList> getPurchasingLists();

    List<PurchasingListDTO> getPurchasingListDTOs();

    PurchasingList getPurchasingList(int id);

    List<PurchasingList> getPurchasingListByContractId(int id);

    void addPurchasingList(PurchasingList purchasingList);

    void updatePurchasingList(PurchasingList purchasingList);

    void deletePurchasingList(int id);

    String addDelivery(int id,int quantity);
}
