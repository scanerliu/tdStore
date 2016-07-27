package com.tiandu.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiandu.order.entity.TdOrderShipment;
import com.tiandu.order.entity.mapper.TdOrderShipmentItemMapper;
import com.tiandu.order.entity.mapper.TdOrderShipmentMapper;
import com.tiandu.order.search.TdOrderShipmentSearchCriteria;
import com.tiandu.order.service.TdOrderShipmentService;

/**
 * 
 * @author liuxinbing
 *
 */
@Service("tdOrderShipmentService")
public class TdOrderShipmentServiceImpl implements TdOrderShipmentService{

	@Autowired
	private TdOrderShipmentMapper tdOrderShipmentMapper;
	
	@Autowired
	private TdOrderShipmentItemMapper tdOrderShipmentItemMapper;
	
	@Override
	public TdOrderShipment findOne(Integer id) {
		return tdOrderShipmentMapper.selectByPrimaryKey(id);
	}
	@Override
	public List<TdOrderShipment> findBySearchCriteria(TdOrderShipmentSearchCriteria sc) {
		return tdOrderShipmentMapper.findBySearchCriteria(sc);
	}

	
}
