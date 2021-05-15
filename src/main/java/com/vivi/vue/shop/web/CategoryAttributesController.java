package com.vivi.vue.shop.web;

import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.service.AttributeService;
import com.vivi.vue.shop.utils.R;
import com.vivi.vue.shop.vo.AttributeAddVO;
import com.vivi.vue.shop.vo.CategoryAttributeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
 * 属性表
 *
 * @author wangwei
 * @email xidian.wangwei@gmail.com
 * @date 2021-02-08 19:39:50
 */
@RestController
public class CategoryAttributesController {

    @Autowired
    private AttributeService attributeService;

    /**
     * 列表
     */
    @GetMapping("/categories/{catId}/attributes")
    public R list(@PathVariable("catId") Integer catId,
                  @RequestParam("sel") String sel){

        List<CategoryAttributeVO> attributeVOS = attributeService.listByCatIdAndSelType(catId, sel);

        return R.ok().setMsg("获取成功").setData(attributeVOS);
    }


    /**
     * 新增
     * @param addVO
     * @return
     */
    @PostMapping("/categories/{catId}/attributes")
    public R save(@PathVariable("catId") Integer catId,
                  @Validated AttributeAddVO addVO) {
        CategoryAttributeVO catAttributeVO = attributeService.add(catId, addVO);
        return R.create(BizCodeEnum.CREATED.getErrCode(), BizCodeEnum.CREATED.getErrMsg()).setData(catAttributeVO);
    }

    /**
     * 删除指定分类下指定属性，
     * 但因为attrId是主键，所以并不需要参数catId也能锁定记录
     * @param catId
     * @param attrId
     * @return
     */
    @DeleteMapping("/categories/{catId}/attributes/{attrId}")
    public R delete(@PathVariable("catId") Integer catId,
                  @PathVariable("attrId")Integer attrId) {

        attributeService.removeById(attrId);
        return R.ok().setMsg("删除成功");
    }


    /**
     * 查询指定分类下指定属性，
     * 但因为attrId是主键，所以并不需要参数catId也能锁定记录
     * @param catId
     * @param attrId
     * @return
     */
    @GetMapping("/categories/{catId}/attributes/{attrId}")
    public R info(@PathVariable("catId") Integer catId,
                  @PathVariable("attrId")Integer attrId){
		CategoryAttributeVO attribute = attributeService.getOne(attrId);

        return R.ok().setMsg("获取成功").setData(attribute);
    }


    /**
     * 修改指定分类下指定属性，
     * 但因为attrId是主键，所以并不需要参数catId也能锁定记录
     */
    @PutMapping("/categories/{catId}/attributes/{attrId}")
    public R update(@PathVariable("catId") Integer catId,
                  @PathVariable("attrId")Integer attrId,
                    @Validated AttributeAddVO attrVO){
        CategoryAttributeVO attribute = attributeService.update(attrId, attrVO);

        return R.ok().setMsg("更新成功").setData(attribute);
    }
}
