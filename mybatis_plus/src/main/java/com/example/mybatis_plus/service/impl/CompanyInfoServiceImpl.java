package com.example.mybatis_plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis_plus.entity.CompanyInfo;
import com.example.mybatis_plus.mapper.CompanyInfoMapper;
import com.example.mybatis_plus.service.CompanyInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-04-01
 */
@Service
public class CompanyInfoServiceImpl extends ServiceImpl<CompanyInfoMapper, CompanyInfo> implements CompanyInfoService {
    @Override
    public List<CompanyInfo> getChildNodes(String parentId) {
        List<CompanyInfo> list=list(new QueryWrapper<CompanyInfo>().eq("parent_id",parentId));
        return list;
    }
}
