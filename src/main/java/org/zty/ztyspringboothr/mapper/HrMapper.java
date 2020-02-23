package org.zty.ztyspringboothr.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.zty.ztyspringboothr.bean.Hr;
import org.zty.ztyspringboothr.bean.Role;

import java.util.List;

public interface HrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Hr record);

    int insertSelective(Hr record);

    Hr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Hr record);

    int updateByPrimaryKey(Hr record);

    Hr loadUserByUsername(String username);

    List<Role> getHrRolesById(Integer id);

    List<Hr> getAllHrs(@Param("hrid") Integer hrid, @Param("keywords") String keywords);
}