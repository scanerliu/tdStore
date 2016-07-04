package com.tiandu.product.entity.mapper;

import java.util.List;

import com.tiandu.product.entity.TdProduct;
import com.tiandu.product.search.TdProductCriteria;

public interface TdProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdProduct record);

    int insertSelective(TdProduct record);

    TdProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdProduct record);

    int updateByPrimaryKey(TdProduct record);
    
    Integer countByCriteria(TdProductCriteria sc);
    List<TdProduct> findBySearchCriteria(TdProductCriteria sc);
}