package com.vivi.vue.shop.web;

import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.service.OrderService;
import com.vivi.vue.shop.utils.R;
import com.vivi.vue.shop.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 订单表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @GetMapping("/orders")
    public R list(@Validated PageQueryVO pageQueryVO){
        OrdersListPageVO page = orderService.queryPage(pageQueryVO);

        return R.ok().setMsg("获取成功").setData(page);
    }

    /**
     * 保存
     */
    @PostMapping("/orders")
    public R save(){
        return R.create(BizCodeEnum.BAD_REQUEST.getErrCode(), "文档不完整，订单添加功能暂未实现");
    }


    /**
     * 信息
     */
    @GetMapping("/orders/{orderId}")
    public R info(@PathVariable("orderId") Integer orderId){
        OrderVO vo = orderService.getOne(orderId);

        return R.ok().setMsg("获取成功").setData(vo);
    }

    /**
     * 修改
     */
    @PutMapping("/orders/{orderId}")
    public R update(@PathVariable("orderId") Integer orderId,
                    @Validated OrderUpdateVO updateVO){
        OrderVO vo = orderService.updateById(orderId, updateVO);

        return R.ok().setMsg("更新成功").setData(vo);
    }

    /**
     * 删除
     */
    @DeleteMapping("/orders/{orderId}")
    public R delete(@PathVariable("orderId") Integer orderId){
        orderService.removeCascade(orderId);

        return R.ok().setMsg("删除成功");
    }

}
