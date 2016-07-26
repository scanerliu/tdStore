package com.tiandu.order.entity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tiandu.order.entity.TdOrderProduct;

public interface TdOrderProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdOrderProduct record);

    int insertSelective(TdOrderProduct record);

    TdOrderProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdOrderProduct record);

    int updateByPrimaryKeyWithBLOBs(TdOrderProduct record);

    int updateByPrimaryKey(TdOrderProduct record);
    
    public List<TdOrderProduct> findByOrderId(Integer orderId);

    /**
     * 根据订单Id和类型查找记录
     * @param orderId
     * @param typeId
     * @return
     */
	TdOrderProduct findByOrderIdAndTypeId(@Param("orderId") Integer orderId, @Param("typeId") Byte typeId);
}