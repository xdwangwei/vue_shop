package com.vivi.vue.shop;

import com.aliyun.oss.OSS;
import com.vivi.vue.shop.entity.AttributeEntity;
import com.vivi.vue.shop.service.AttributeService;
import com.vivi.vue.shop.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VueShopApplicationTests {

    @Autowired
    AttributeService attributeService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OSS oss;

    @Test
    void contextLoads() {
        AttributeEntity e = new AttributeEntity();
        // e.setAttrSel("only");
        e.setAttrName("hahahah");
        e.setCatId(1);
        e.setAttrVals("");
        attributeService.save(e);
        System.out.println("hahahah");
    }


    @Test
    void testOSS() {
        System.out.println(oss);
    }

}
