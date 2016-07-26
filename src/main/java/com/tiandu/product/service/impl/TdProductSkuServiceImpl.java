package com.tiandu.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.product.entity.TdProductSku;
import com.tiandu.product.entity.mapper.TdProductSkuMapper;
import com.tiandu.product.service.TdProductSkuService;

@Service("tdProductSkuService")
public class TdProductSkuServiceImpl implements TdProductSkuService{

	@Autowired
	private TdProductSkuMapper tdProductSkuMapper;

	@Override
	public Integer deleteByPrimaryKey(Integer id) {
		return tdProductSkuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Integer save(TdProductSku sku) {
		if(null != sku)
		{
			if(null != sku.getId()){
				return tdProductSkuMapper.updateByPrimaryKey(sku);
			}else{
				return tdProductSkuMapper.insert(sku);
			}
		}
		return null;
	}

	@Override
	public TdProductSku findOne(Integer id) {
		return tdProductSkuMapper.selectByPrimaryKey(id);
	}

	@Override
	public TdProductSku findOneWithProduct(Integer id) {
		return tdProductSkuMapper.selectByPrimaryKeyWithProduct(id);
	}

	@Override
	public List<TdProductSku> findByProductId(Integer proId) {
		return tdProductSkuMapper.selectByProductId(proId);
	}

	@Override
	public Integer deleteByProductId(Integer proId) {
		return tdProductSkuMapper.deleteByProductId(proId);
	}
	
}
