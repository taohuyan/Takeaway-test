package com.sky.controller.admin;

import com.sky.annotation.RedisDelete;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 * @author:呼延涛
 * @version:1.0
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishServiceImpl dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @ApiOperation("新增菜品")
    @PostMapping()
    @RedisDelete()
    public Result insert(@RequestBody DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        dishService.insert(dishDTO);

        return Result.success();
    }

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询菜品")
    public Result<PageResult> QueryByPage(DishPageQueryDTO dishPageQueryDTO){
        log.info("分页查询菜品:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.QueryByPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除菜品")
    @RedisDelete()
    public Result Delete(@RequestParam List<Long> ids){
        log.info("删除菜品:{}",ids);
        dishService.DeleteBatch(ids);
        return Result.success();
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据Id查询菜品")
    public Result<DishVO> QueryById(@PathVariable Long id){
        log.info("根据id查询菜品：{}",id);
        DishVO dishVO = dishService.QueryById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品数据
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜品数据")
    @RedisDelete()
    public Result Update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品信息");
        dishService.UpdateWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        log.info("根据分类id查询菜品");
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 菜品起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @RedisDelete()
    public Result StartOrStop(@PathVariable Integer status,Long id){
        log.info("菜品起售停售");
        dishService.startOrStop(status,id);
        return Result.success();
    }
}
