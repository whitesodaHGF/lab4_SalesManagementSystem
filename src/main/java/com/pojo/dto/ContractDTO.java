package com.pojo.dto;

import com.model.Contract;
import com.model.PurchasingList;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 合同添加
 *
 * @author:HGF
 */
@Data
@ToString
public class ContractDTO {
    private Contract contract;
    private List<PurchasingList> purchasingLists;
}
