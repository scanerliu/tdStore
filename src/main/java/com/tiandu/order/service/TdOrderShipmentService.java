package com.tiandu.order.service;

import java.util.List;

import com.tiandu.order.entity.TdOrderShipment;
import com.tiandu.order.search.TdOrderShipmentSearchCriteria;

public interface TdOrderShipmentService {

	public TdOrderShipment findOne(Integer id);
	
	public List<TdOrderShipment> findBySearchCriteria(TdOrderShipmentSearchCriteria sc);
}
