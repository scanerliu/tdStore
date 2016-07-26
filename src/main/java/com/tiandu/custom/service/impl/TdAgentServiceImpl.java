package com.tiandu.custom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.common.db.DBContextHolder;
import com.tiandu.custom.entity.TdAgent;
import com.tiandu.custom.entity.mapper.TdAgentMapper;
import com.tiandu.custom.search.TdAgentSearchCriteria;
import com.tiandu.custom.service.TdAgentService;
import com.tiandu.custom.service.TdUserService;

/**
 * 
 * @author L. Gao
 *
 */
@Service("tdAgentService")
public class TdAgentServiceImpl implements TdAgentService {
	
	@Autowired
	TdAgentMapper agentMapper;
	
	@Autowired
	TdUserService tdUserService;
	
	@Override
	public int insert(TdAgent u) {
		DBContextHolder.setDbType(DBContextHolder.DB_RW);
		return agentMapper.insert(u);
	}

	@Override
	public TdAgent findOne(Integer id) {
		return agentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TdAgent> findBySearchCriteria(TdAgentSearchCriteria sc) {
		if(sc.getUsername() != null && sc.getUsername().equals("")){
			sc.setUsername(null);
		}
		if(sc.getLevel() != null && sc.getLevel() == -1){
			sc.setLevel(null);
		}
		if(sc.getRegionId() != null && sc.getRegionId() == -1){
			sc.setRegionId(null);
		}
		if(sc.getProductTypeId() != null && sc.getProductTypeId() == -1){
			sc.setProductTypeId(null);
		}
		sc.setAssociationUser(true);
		sc.setAssociationRegion(true);
		sc.setAssociationProductType(true);
		Integer count = agentMapper.countByCriteria(sc);
		sc.setTotalCount(count);
		List<TdAgent> agentList = agentMapper.findBySearchCriteria(sc);
		return agentList;
	}
	
	
	@Override
	public int countByCriteria(TdAgentSearchCriteria sc) {
		return agentMapper.countByCriteria(sc);
	}

	@Override
	public Integer save(TdAgent TdAgent) {
		if(null!=TdAgent){
			if(null!=TdAgent.getId()){//更新
				return agentMapper.updateByPrimaryKey(TdAgent);
			}else{
				return agentMapper.insert(TdAgent);
			}
		}
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		return agentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public TdAgent findByUid(Integer uid) {
		return agentMapper.findByUid(uid);
	}

	
}
