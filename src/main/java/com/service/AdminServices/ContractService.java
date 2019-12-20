package com.service.AdminServices;

import com.model.Contract;

import java.util.List;

/**
 * 合同管理
 *
 * @author:HGF
 */
public interface ContractService {

    List<Contract> getContracts();

    Contract getContract(int id);

    int addContract(Contract contract);

    void updateContract(Contract contract);
}
