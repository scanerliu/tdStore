package com.tiandu.order.entity.mapper;

import java.util.List;

import com.tiandu.order.entity.TdOrderShipment;
import com.tiandu.order.search.TdOrderShipmentSearchCriteria;

public interface TdOrderShipmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdOrderShipment record);

    int insertSelective(TdOrderShipment record);

    TdOrderShipment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdOrderShipment record);

    int updateByPrimaryKey(TdOrderShipment record);
    
    public List<TdOrderShipment> findBySearchCriteria(TdOrderShipmentSearchCriteria sc);

	Integer countByCriteria(TdOrderShipmentSearchCriteria sc);
}