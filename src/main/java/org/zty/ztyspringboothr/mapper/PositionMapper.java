package org.zty.ztyspringboothr.mapper;

import org.apache.ibatis.annotations.Param;
import org.zty.ztyspringboothr.bean.Position;
import org.zty.ztyspringboothr.bean.RespBean;

import java.util.List;

public interface PositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<Position> getAllPositions();

    Integer deletePositionByIds(@Param("ids") Integer[] ids);
}