package com.sky.mapper;

import com.sky.entity.DishFlavor;
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
}
