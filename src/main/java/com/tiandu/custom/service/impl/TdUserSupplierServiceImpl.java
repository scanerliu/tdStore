package com.tiandu.custom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.article.entity.TdArticleTitle;
import com.tiandu.common.db.DBContextHolder;
import com.tiandu.custom.entity.TdUser;
import com.tiandu.custom.entity.TdUserSupplier;
import com.tiandu.custom.entity.mapper.TdUserSupplierMapper;
import com.tiandu.custom.search.TdUserSupplierSearchCriteria;
import com.tiandu.custom.service.TdUserService;
import com.tiandu.custom.service.TdUserSupplierService;

/**
 * 
 * @author L. Gao
 *
 */
@Service("tdUserSupplierService")
public class TdUserSupplierServiceImpl implements TdUserSupplierService {
	
	@Autowired
	TdUserSupplierMapper userSupplierMapper;
	
	@Autowired
	TdUserService tdUserService;
	
	@Override
	public int insert(TdUserSupplier u) {
		DBContextHolder.setDbType(DBContextHolder.DB_RW);
		return userSupplierMapper.insert(u);
	}

	@Override
	public TdUserSupplier findOne(Integer id) {
		return userSupplierMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<TdUserSupplier> findBySearchCriteria(TdUserSupplierSearchCriteria sc) {
		Integer count = userSupplierMapper.countByCriteria(sc);
		if(sc.getStatus() != null && sc.getStatus() == -1){
			sc.setStatus(null);
		}
		sc.setTotalCount(count);
		sc.setAssociationUser(true);
		sc.setAssociationUpdatePerson(true);
		if(sc.getSearchName() != null && sc.getSearchName().equals("")){
			sc.setSearchName(null);
		}
		List<TdUserSupplier> userSupplierList = userSupplierMapper.findBySearchCriteria(sc);
//		for(TdUserSupplier ts: userSupplierList){
//			if(ts.getUid() != null){
//				TdUser user = tdUserService.findOne(ts.getUid());
//				ts.setUser(user);
//			}
//			if(ts.getUpdateBy() != null){
//				ts.setUpdatePerson(tdUserService.findOne(ts.getUpdateBy()));
//			}
//		}
		return userSupplierList;
	}
	
	
	@Override
	public Integer save(TdUserSupplier userSupplier) {
		if(null!=userSupplier){
			if(null!=userSupplier.getId()){//更新
				return userSupplierMapper.updateByPrimaryKey(userSupplier);
			}else{
				return userSupplierMapper.insert(userSupplier);
			}
		}
		return null;
	}

	@Override
	public Integer delete(Integer id) {
		return userSupplierMapper.deleteByPrimaryKey(id);
	}

}
