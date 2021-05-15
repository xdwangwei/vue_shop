package com.vivi.vue.shop.web;

import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangwei
 * 2021/2/11 19:23
 */
@RestController
public class LogisticsController {

    /**
     * 查询快递物流信息
     *
     * @param id 运单号
     * @return
     */
    @GetMapping("/kuaidi/{id}")
    public R logistics(@PathVariable("id") String id) {
        return R.create(BizCodeEnum.BAD_REQUEST.getErrCode(), "物流查询暂未实现，单号：" + id);
    }
}
