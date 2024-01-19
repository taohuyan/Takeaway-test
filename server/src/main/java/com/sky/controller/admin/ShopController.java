package com.sky.controller.admin;

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
@RequestMapping("/admin/shop")
@RestController("adminShopController")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {
    @Autowired
    RedisTemplate redisTemplate;

    private static final String KEY="SHOP_STATUS";
    /**
     * 设置店铺状态
     * @param status
     * @return
     */
    @ApiOperation("设置店铺状态")
    @PutMapping("/{status}")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺的营业状态:{}",status==1?"营业中":"打烊中");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(KEY,status);
        return Result.success();
    }

    /**
     * 获取店铺状态
     * @return
     */
    @ApiOperation("获取店铺状态")
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Integer o = (Integer)valueOperations.get(KEY);
        log.info("获取到店铺的营业状态:{}",0==1?"营业中":"打烊中");
        return Result.success(o);
    }
}
