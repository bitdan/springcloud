package com.example.mybatis_plus.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatis_plus.product.entity.Product;
import com.example.mybatis_plus.product.mapper.ProductMapper;
import com.example.mybatis_plus.product.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xsz
 * @since 2023-03-10
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public List<Product> selectByUserId(String productId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        List<Product> list= list(queryWrapper.lambda().eq(Product::getUserId,productId));
        return list;
    }
}
