package com.tiandu.custom.service;

import java.util.List;

import com.tiandu.custom.entity.TdBrancheCompany;
import com.tiandu.custom.search.TdBrancheCompanySearchCriteria;

/**
 * 
 * @author L. Gao
 *
 */
public interface TdBrancheCompanyService {

	public int insert(TdBrancheCompany u);
	public TdBrancheCompany findOne(Integer id);
	public List<TdBrancheCompany> findBySearchCriteria(TdBrancheCompanySearchCriteria sc);
	public int countByCriteria(TdBrancheCompanySearchCriteria sc);
	public Integer save(TdBrancheCompany tdBrancheCompany);
	public Integer delete(Integer id);
	/**
	 * 按用户id查找
	 * @param userId
	 * @return
	 */
	public TdBrancheCompany findByUid(Integer userId);
}
