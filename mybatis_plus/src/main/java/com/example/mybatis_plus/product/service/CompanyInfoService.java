package com.example.mybatis_plus.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis_plus.product.entity.CompanyInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-04-01
 */
public interface CompanyInfoService extends IService<CompanyInfo> {
    /**
     * 获得指定父节点的子节点列表
     * @param parentId 父节点ID
     * @return 子节点列表
     */
    List<CompanyInfo> getChildNodes(String parentId);
}
