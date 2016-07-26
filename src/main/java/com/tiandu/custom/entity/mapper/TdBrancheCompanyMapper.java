package com.tiandu.custom.entity.mapper;

import java.util.List;

import com.tiandu.custom.entity.TdBrancheCompany;
import com.tiandu.custom.entity.TdExperienceStore;
import com.tiandu.custom.search.TdBrancheCompanySearchCriteria;
import com.tiandu.custom.search.TdExperienceStoreSearchCriteria;

public interface TdBrancheCompanyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TdBrancheCompany record);

    int insertSelective(TdBrancheCompany record);

    TdBrancheCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TdBrancheCompany record);

    int updateByPrimaryKey(TdBrancheCompany record);
    
    Integer countByCriteria(TdBrancheCompanySearchCriteria sc);
    public List<TdBrancheCompany> findBySearchCriteria(TdBrancheCompanySearchCriteria sc);
}