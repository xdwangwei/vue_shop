package com.vivi.vue.shop.web;

import com.aliyun.oss.OSS;
import com.vivi.vue.shop.exception.BizCodeEnum;
import com.vivi.vue.shop.exception.BizException;
import com.vivi.vue.shop.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangwei
 * 2021/2/11 10:44
 */
@Slf4j
@RestController
public class UploadController {


    @Autowired
    private OSS ossClient; // 创建OSSClient实例

    @Value("${alibaba.cloud.oss.urlPrefix}")
    private String urlPrefix;

    @Value("${alibaba.cloud.oss.bucket}")
    private String bucket; // 请填写您的 bucketname


    /**
     * 单个文件上传到oss
     * @param multipartFile
     * @return
     */
    @RequestMapping("/upload")
    public R upload(@RequestPart(value = "file") MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new BizException(BizCodeEnum.BAD_REQUEST, "文件上传项的请求参数名必须为file");
        }
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            // 上传到阿里云
            String path = "goods_pictures/" + originalFilename;
            ossClient.putObject(bucket, path, multipartFile.getInputStream());
            Map<String, String> res = new HashMap<>();
            res.put("tmp_path", path);
            res.put("url", urlPrefix + path);
            return R.ok().setMsg("上传成功").setData(res);
        } catch (Exception e) {
            log.error("文件上传异常：", e);
            throw new BizException(BizCodeEnum.INTERNAL_SERVER_ERROR, "上传出错啦，请重试");
        }
    }

}
