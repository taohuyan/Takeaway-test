package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * @author:呼延涛
 * @version:1.0
 */
@RequestMapping("/user/shop")
@RestController("userShopController")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {
    @Autowired
    RedisTemplate redisTemplate;

    private static final String KEY="SHOP_STATUS";
    /**
     * 获取店铺状态
     * @return
     */
    @ApiOperation("获取店铺状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer o = (Integer)valueOperations.get(KEY);
        log.info("获取到店铺的营业状态:{}",o==1?"营业中":"打烊中");
        return Result.success(o);
    }
}
