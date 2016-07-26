package com.tiandu.custom.entity.mapper;

import java.util.List;

import com.tiandu.custom.entity.TdAgent;
import com.tiandu.custom.search.TdAgentSearchCriteria;

public interface TdAgentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdAgent record);

    int insertSelective(TdAgent record);

    TdAgent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdAgent record);

    int updateByPrimaryKey(TdAgent record);
    
    Integer countByCriteria(TdAgentSearchCriteria sc);
    public List<TdAgent> findBySearchCriteria(TdAgentSearchCriteria sc);
}