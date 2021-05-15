package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.TypeEntity;

import java.util.Map;

/**
 * 类型表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface TypeService extends IService<TypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

