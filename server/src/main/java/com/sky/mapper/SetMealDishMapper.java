package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:呼延涛
 * @version:1.0
 */
@Mapper
public interface SetMealDishMapper {
    /**
     * 根据菜品id查询套餐
     * @param dishIds
     * @return
     */
    List<Long> getSetMealDishIds(List<Long> dishIds);
}
