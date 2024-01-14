package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author:呼延涛
 * @version:1.0
 */
@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    /**
     * 新增菜品对应的口味
     * @param dishDTO
     */
    @Transactional
    @Override
    public void insert(DishDTO dishDTO) {
        //向菜品表插入一条数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.Insert(dish);
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(e->e.setDishId(dishId));
        if(flavors==null || flavors.size()>0){
            //向口味表插入n条数据
            dishFlavorsMapper.insert(flavors);
        }
    }

    /**
     * 分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult QueryByPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.QueryByPage(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page);
    }

    /**
     * 菜品的删除
     * @param ids
     */
    @Override
    public void DeleteBatch(List<Long> ids) {
        ids.forEach(e->{
            Dish dish = dishMapper.getById(e);
            if(dish.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });
        List<Long> setMeals = setMealDishMapper.getSetMealDishIds(ids);
        if(setMeals!=null&&setMeals.size()>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        ids.forEach(e->{
            dishMapper.deleteById(e);
            dishFlavorsMapper.deleteById(e);
        });
    }

    /**
     * 根据id查询菜品数据以及口味数据
     * @param id
     * @return
     */
    @Override
    public DishVO QueryById(Long id) {
        //根据id查询菜品数据
        Dish dish = dishMapper.getById(id);
        //根据id查询口味数据
        List<DishFlavor> dishFlavors= dishFlavorsMapper.getByDishId(id);
        //组合
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public void UpdateWithFlavor(DishDTO dishDTO) {
        //修改菜品的基本信息
        Dish dish =  new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.Update(dish);
        //先删除口味表，再新增
        dishFlavorsMapper.deleteById(dishDTO.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        Long dishId = dishDTO.getId();
        if(flavors==null || flavors.size()>0){
            flavors.forEach(e->e.setDishId(dishId));
            //向口味表插入n条数据
            dishFlavorsMapper.insert(flavors);
        }
    }
    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }
}
