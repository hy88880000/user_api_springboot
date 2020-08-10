package org.shf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.shf.model.po.Area;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaDao extends BaseMapper<Area> {
    String selectAreaName(Integer areaId);
}
