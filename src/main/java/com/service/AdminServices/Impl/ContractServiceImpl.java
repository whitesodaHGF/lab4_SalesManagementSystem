package com.service.AdminServices.Impl;

import com.Exception.CommonException;
import com.enums.ResultEnum;
import com.mapper.ContractMapper;
import com.model.Contract;
import com.service.AdminServices.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 合同管理
 *
 * @author:HGF
 */
@Service
@Slf4j
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractMapper contractMapper;

    @Override
    public List<Contract> getContracts() {
        return contractMapper.selectAll();
    }

    @Override
    public Contract getContract(int id) {
        return contractMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int addContract(Contract contract) {
        contract.setIsFulfill("未履行");
        Date date=new Date();
        if(!contract.getEffectiveDate().before(contract.getExpirationDate())){
            throw new CommonException(ResultEnum.CONTRACT_DATE_ERROR1);
        }
        if(date.before(contract.getSigningDate())){
            throw new CommonException((ResultEnum.CONTRACT_DATE_ERROR2));
        }
        contractMapper.insert(contract);
        log.info(String.valueOf(contract.getId()));
        return contract.getId();
    }

    @Override
    @Transactional
    public void updateContract(Contract contract) {
        // 如果合同状态是执行中，就不可以修改合同
        Date date=new Date();
        if(!contract.getEffectiveDate().before(contract.getExpirationDate())){
            throw new CommonException(ResultEnum.CONTRACT_DATE_ERROR1);
        }
        if(date.before(contract.getSigningDate())){
            throw new CommonException((ResultEnum.CONTRACT_DATE_ERROR2));
        }
        Contract contract1=contractMapper.selectByPrimaryKey(contract.getId());
        if(contract1==null) throw new CommonException(ResultEnum.CONTRACT_ID_MISS);
        if(!contract1.getIsFulfill().equals("未履行"))
            throw new CommonException((ResultEnum.CONTRACT_UPDATE_ERROR));
        contract.setIsFulfill("未履行");
        contractMapper.updateByPrimaryKey(contract);
    }


}
