package com.tiandu.product.entity.mapper;

import java.util.List;

import com.tiandu.product.entity.TdProductSku;

public interface TdProductSkuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdProductSku record);

    int insertSelective(TdProductSku record);

    TdProductSku selectByPrimaryKey(Integer id);
    
    TdProductSku selectByPrimaryKeyWithProduct(Integer id);

    int updateByPrimaryKeySelective(TdProductSku record);

    int updateByPrimaryKey(TdProductSku record);
    
    List<TdProductSku> findByProductId(Integer proId);
    Integer deleteByProductId(Integer proId);
    List<TdProductSku> selectByProductId(Integer proId);
    
}