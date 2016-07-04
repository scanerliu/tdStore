package com.tiandu.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.product.entity.TdProductDescription;
import com.tiandu.product.entity.mapper.TdProductDescriptionMapper;
import com.tiandu.product.search.TdProductDescriptionCriteria;
import com.tiandu.product.service.TdProductDescriptionService;

@Service("tdProductDescriptionService")
public class TdProductDescriptionServiceImpl implements TdProductDescriptionService{

	@Autowired
	private TdProductDescriptionMapper tdProductDescriptionMapper;
	
	@Override
	public Integer save(TdProductDescription e) {
		if(null != e )
		{
			if(null != e.getId()){
				tdProductDescriptionMapper.updateByPrimaryKey(e);
			}else{
				tdProductDescriptionMapper.insert(e);
			}
		}
		return null;
	}

	@Override
	public TdProductDescription findOne(Integer id) {
		return tdProductDescriptionMapper.selectByPrimaryKey(id);
	}

	@Override
	public TdProductDescription findByProductId(TdProductDescriptionCriteria sc) {
		return tdProductDescriptionMapper.findByProductId(sc);
	}

	@Override
	public Integer deleteByProductId(Integer proId) {
		return tdProductDescriptionMapper.deleteByProductId(proId);
	}

}
