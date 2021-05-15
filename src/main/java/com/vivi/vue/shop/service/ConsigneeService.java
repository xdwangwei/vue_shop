package com.vivi.vue.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.entity.ConsigneeEntity;

import java.util.Map;

/**
 * 收货人表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
public interface ConsigneeService extends IService<ConsigneeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

