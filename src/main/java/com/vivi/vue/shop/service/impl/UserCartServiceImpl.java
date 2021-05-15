package com.vivi.vue.shop.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vivi.vue.shop.utils.PageUtils;
import com.vivi.vue.shop.utils.Query;

import com.vivi.vue.shop.dao.UserCartDao;
import com.vivi.vue.shop.entity.UserCartEntity;
import com.vivi.vue.shop.service.UserCartService;


@Service("userCartService")
public class UserCartServiceImpl extends ServiceImpl<UserCartDao, UserCartEntity> implements UserCartService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCartEntity> page = this.page(
                new Query<UserCartEntity>().getPage(params),
                new QueryWrapper<UserCartEntity>()
        );

        return new PageUtils(page);
    }

}