package com.example.mybatis_plus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis_plus.entity.Product;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xsz
 * @since 2023-03-10
 */
public interface ProductService extends IService<Product> {
    List<Product> selectByUserId (String productId);
}
