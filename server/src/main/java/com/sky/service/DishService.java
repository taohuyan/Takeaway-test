package com.sky.service;

import com.sky.dto.DishDTO;
import org.springframework.stereotype.Service;

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
}
