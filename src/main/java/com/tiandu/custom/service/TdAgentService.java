package com.tiandu.custom.service;

import java.util.List;

import com.tiandu.custom.entity.TdAgent;
import com.tiandu.custom.search.TdAgentSearchCriteria;

/**
 * 
 * @author L. Gao
 *
 */
public interface TdAgentService {

	public int insert(TdAgent u);
	public TdAgent findOne(Integer id);
	public List<TdAgent> findBySearchCriteria(TdAgentSearchCriteria sc);
	public Integer save(TdAgent TdAgent);
	public Integer delete(Integer id);
	public int countByCriteria(TdAgentSearchCriteria sc);
	/**
	 * 根据用户id查找代理
	 * @param uid
	 * @return
	 */
	public TdAgent findByUid(Integer uid);
}
