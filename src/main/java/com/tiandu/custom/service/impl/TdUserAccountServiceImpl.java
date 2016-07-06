package com.tiandu.custom.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.custom.entity.TdUserAccount;
import com.tiandu.custom.entity.TdUserAccountLog;
import com.tiandu.custom.entity.TdUserIntegral;
import com.tiandu.custom.entity.TdUserIntegralLog;
import com.tiandu.custom.entity.mapper.TdUserAccountLogMapper;
import com.tiandu.custom.entity.mapper.TdUserAccountMapper;
import com.tiandu.custom.entity.mapper.TdUserIntegralLogMapper;
import com.tiandu.custom.entity.mapper.TdUserIntegralMapper;
import com.tiandu.custom.service.TdUserAccountService;

@Service("tdUserAccountService")
public class TdUserAccountServiceImpl implements TdUserAccountService {

	@Autowired
	TdUserAccountMapper tdUserAccountMapper;
	
	@Autowired
	TdUserAccountLogMapper tdUserAccountLogMapper;

	@Override
	public int insert(TdUserAccount u) {
		return tdUserAccountMapper.insert(u);
	}

	@Override
	public int updateByPrimaryKeySelective(TdUserAccount record) {
		return tdUserAccountMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public TdUserAccount findOne(Integer id) {
		return tdUserAccountMapper.selectByPrimaryKey(id);
	}

	@Override
	public synchronized boolean addAmount(TdUserAccount record, TdUserAccountLog log) {
		TdUserAccount account = tdUserAccountMapper.selectByPrimaryKey(record.getUid());
		BigDecimal totalamount = account.getAmount().add(log.getUpamount());
		log.setPreamount(account.getAmount());
		log.setUid(account.getUid());
		record.setAmount(totalamount);		
		tdUserAccountMapper.updateByPrimaryKey(record);
		tdUserAccountLogMapper.insert(log);
		return true;
	}

	

	
	
	
}
