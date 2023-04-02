package com.example.mybatis_plus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis_plus.common.Result;
import com.example.mybatis_plus.entity.CompanyInfo;
import com.example.mybatis_plus.entity.TreeNode;
import com.example.mybatis_plus.service.CompanyInfoService;
import com.example.mybatis_plus.utils.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-04-01
 */
@Api(value = "", tags = "")
@RestController
@RequestMapping("/companyInfo")
//@Slf4j
public class CompanyInfoController {

    @Autowired
    private CompanyInfoService companyInfoService;


    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/list")
    public Result list(@RequestBody(required = false) Map<String, Object> object) {
        Page<CompanyInfo> page = ParamUtils.toPage(CompanyInfo.class, object);
        CompanyInfo entity = ParamUtils.toEntity(CompanyInfo.class, object);
		QueryWrapper<CompanyInfo> qw = new QueryWrapper<>();
		if (null != entity) {
			qw.setEntity(entity);
		}
		// qw.orderByAsc("");
		page = companyInfoService.page(page, qw);
		return Result.success(page);
    }

    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/get")
    public Result get(String id) {
        if (null == id || id.equals("")) {
            return Result.fail("ID不能为空！");
        }
        CompanyInfo entity = companyInfoService.getById(id);
        return Result.success(entity);
    }

    /**
     * 输入 parentId
     *
     * @param map
     * @return
     */
    @ApiOperation(value = "获得指定父节点的子节点列表")
    @PostMapping(value = "/getChildNodes")
    public Result getChildNodes(@RequestBody Map<String, Object> map) {
        if (map.isEmpty()) {
            return Result.fail("输入格式错误");
        }

        String parentId = ParamUtils.toString(map, "parentId");
//        List<CompanyInfo> ngPowerTransformerInfoList = CompanyInfoService.getChildNodes(parentId);
        List<CompanyInfo> ngPowerTransformerInfoList = companyInfoService.getChildNodes(parentId);

        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        for (CompanyInfo CompanyInfo : ngPowerTransformerInfoList) {
            String CompanyId = CompanyInfo.getId();
            String CompanyParentId = CompanyInfo.getParentId();
            String companyName = CompanyInfo.getCompanyName();

            boolean isLeaf = true;
            List<CompanyInfo> childNodes = companyInfoService.getChildNodes(CompanyId);
            if (childNodes.size() > 0) {
                isLeaf = false;
            }
            TreeNode treeNode = new TreeNode(CompanyId, companyName, CompanyParentId, isLeaf);

            treeNodes.add(treeNode);
        }
//        HashMap<String, Object> hashMap = new HashMap<>();
//
        return Result.success(treeNodes);
    }
    @ApiOperation(value = "新增/修改数据")
    @PostMapping(value = "/save")
    public Result save(HttpServletRequest request, @RequestBody CompanyInfo entity) {
        if (null == entity) {
            return Result.fail("对象不能为空！");
        }
        companyInfoService.saveOrUpdate(entity);
        return Result.success(entity);
    }

    @ApiOperation(value = "批量删除数据")
    @PostMapping(value = "/delete")
    public Result delete(HttpServletRequest request, @RequestBody List<String> idList) {
        if (null == idList || idList.isEmpty()) {
            return Result.fail("对象不能为空！");
        }
        companyInfoService.removeByIds(idList);

        return Result.success();
    }

}

