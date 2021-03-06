package com.tiandu.custom.service;

import java.util.List;

import com.tiandu.custom.entity.TdUserIntegral;
import com.tiandu.custom.entity.TdUserIntegralLog;
import com.tiandu.custom.entity.TdUserSign;
import com.tiandu.custom.search.TdUserIntegralLogSearchCriteria;
import com.tiandu.custom.search.TdUserSearchCriteria;

public interface TdUserIntegralLogService {

	public int insert(TdUserIntegralLog u);
	public int updateByPrimaryKeySelective(TdUserIntegralLog record);
	public TdUserIntegralLog findOne(Integer id);
	public List<TdUserIntegralLog>  findBySearchCriteria(TdUserIntegralLogSearchCriteria sc);
	public Integer save(TdUserIntegralLog userIntegralLog);
	
}
