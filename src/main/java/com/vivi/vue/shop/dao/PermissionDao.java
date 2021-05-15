package com.vivi.vue.shop.dao;

import com.vivi.vue.shop.entity.PermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 权限表
 * 
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@Mapper
public interface PermissionDao extends BaseMapper<PermissionEntity> {

    /**
     * 范围查询
     * @param psIds
     * @return
     */
    List<PermissionEntity> limitList(@Param("psIds") Set<Integer> psIds);
}
