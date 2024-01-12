package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用于创建AliossUtil对象
 * @author:呼延涛
 * @version:1.0
 */
@Configuration
@Slf4j
public class OSSConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始创建阿里云文件上传工具类{}",aliOssProperties);
        AliOssUtil aliOssUtil = new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getBucketName());
        return aliOssUtil;
    }
}
