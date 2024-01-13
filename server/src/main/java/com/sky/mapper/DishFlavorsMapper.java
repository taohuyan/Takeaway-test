package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author:呼延涛
 * @version:1.0
 */
@Mapper
public interface DishFlavorsMapper {
    /**
     * 向口味表添加n条数据数据
     * @param flavors
     */
    void insert(List<DishFlavor> flavors);

    /**
     * 根据菜品id来删除口味
     * @param e
     */
    @Delete("delete from dish_flavor where dish_id = #{e}")
    void deleteById(Long e);
}
