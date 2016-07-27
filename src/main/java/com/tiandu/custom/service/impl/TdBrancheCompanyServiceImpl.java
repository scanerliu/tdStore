package com.tiandu.custom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.common.db.DBContextHolder;
import com.tiandu.custom.entity.TdBrancheCompany;
import com.tiandu.custom.entity.mapper.TdBrancheCompanyMapper;
import com.tiandu.custom.search.TdBrancheCompanySearchCriteria;
import com.tiandu.custom.service.TdBrancheCompanyService;
import com.tiandu.custom.service.TdUserService;

/**
 * 
 * @author L. Gao
 *
 */
@Service("tdBrancheCompanyService")
public class TdBrancheCompanyServiceImpl implements TdBrancheCompanyService {
	
	@Autowired
	TdBrancheCompanyMapper brancheCompanyMapper;
	
	@Autowired
	TdUserService tdUserService;
	
	@Override
	public int insert(TdBrancheCompany u) {
		DBContextHolder.setDbType(DBContextHolder.DB_RW);
		return brancheCompanyMapper.insert(u);
	}

	@Override
	public TdBrancheCompany findOne(Integer id) {
		return brancheCompanyMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TdBrancheCompany> findBySearchCriteria(TdBrancheCompanySearchCriteria sc) {
		if(sc.getStatus() != null && sc.getStatus() == -1){
			sc.setStatus(null);
		}
		if(sc.getLevel() != null && sc.getLevel() == -1){
			sc.setLevel(null);
		}
		if(sc.getDistrictName() != null && sc.getDistrictName().equals("")){
			sc.setDistrictName(null);
		}
		if(sc.getSearchName() != null && sc.getSearchName().equals("")){
			sc.setSearchName(null);
		}
		sc.setAssociationUser(true);
		sc.setAssociationDistrict(true);
		sc.setAssociationUpdatePerson(true);
		Integer count = brancheCompanyMapper.countByCriteria(sc);
		sc.setTotalCount(count);
		List<TdBrancheCompany> brancheCompanyList = brancheCompanyMapper.findBySearchCriteria(sc);
		return brancheCompanyList;
	}
	
	
	@Override
	public int countByCriteria(TdBrancheCompanySearchCriteria sc) {
		return brancheCompanyMapper.countByCriteria(sc);
	}

	@Override
	public Integer save(TdBrancheCompany tdBrancheCompany) {
		if(null!=tdBrancheCompany){
			if(null!=tdBrancheCompany.getId()){//更新
				return brancheCompanyMapper.updateByPrimaryKey(tdBrancheCompany);
			}else{
				return brancheCompanyMapper.insert(tdBrancheCompany);
			}
		}
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		return brancheCompanyMapper.deleteByPrimaryKey(id);
	}

	@Override
	public TdBrancheCompany findByUid(Integer userId) {
		return brancheCompanyMapper.findByUid(userId);
	}

	
}
