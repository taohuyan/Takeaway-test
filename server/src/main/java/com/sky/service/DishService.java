package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author:呼延涛
 * @version:1.0
 */

public interface DishService {
    /**
     * 新增菜品
     * @param dishDTO
     */
    void insert(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult QueryByPage(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品批量删除
     * @param ids
     */
    void DeleteBatch(List<Long> ids);

    /**
     * 根据Id查询菜品
     *
     * @param id
     * @return
     */
    DishVO QueryById(Long id);

    /**
     * 修改菜品信息
     * @param dishDTO
     */
    void UpdateWithFlavor(DishDTO dishDTO);
    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 菜品的起售和停售
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
