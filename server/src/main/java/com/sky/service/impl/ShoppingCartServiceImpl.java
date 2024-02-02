package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author:呼延涛
 * @version:1.0
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断加入购物车的商品是否已经存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //如果已经存在，数量+1
        if(list != null && list.size()>0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber()+1);//update
            shoppingCartMapper.Update(cart);
        }else{
            //如果不存在，则新增数据
            //判断是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if(dishId!=null){
                //本次添加的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            }else{
                //本次添加的是套餐
                Setmeal setmeal = setmealMapper.getById(dishId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 查看购物车
     * @return
     */
    @Override
    public List<ShoppingCart> showShoppingCart() {
        //获取到当前用户的id
        Long userid = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userid)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    /**
     * 清空购物车
     */
    @Override
    public void cleanShoppingCart() {
        //获取到当前用户的id
        Long userid = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userid)
                .build();
        shoppingCartMapper.Clean(shoppingCart);
    }

    /**
     * 删除购物车中一个商品
     * @param shoppingCartDTO
     */
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //获取到当前用户的id
        Long userid = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();

        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(userid);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        ShoppingCart shoppingCart1 = list.get(0);
        if(shoppingCart1.getNumber()>1){
            Integer number = shoppingCart1.getNumber();
            shoppingCart1.setNumber(number-1);
            shoppingCartMapper.update(shoppingCart1);
        }else {
            shoppingCartMapper.Clean(shoppingCart);
        }
    }
}
