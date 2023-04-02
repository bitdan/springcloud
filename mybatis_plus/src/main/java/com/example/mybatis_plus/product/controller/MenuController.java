package com.example.mybatis_plus.product.controller;

import com.example.mybatis_plus.product.entity.Menu;
import com.example.mybatis_plus.product.service.MenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis_plus.common.Result;
import com.example.mybatis_plus.utils.ParamUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-04-02
 */
@Api(value = "", tags = "")
@RestController
@RequestMapping("/product/menu")
//@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

//    @ApiOperation(value = "查询分页数据")
//    @PostMapping(value = "/list")
//    public Result list(@RequestBody(required = false) Map<String, Object> object) {
//        Page<Menu> page = ParamUtils.toPage(Menu.class, object);
//        // 需要like查询字段
//        String[] addLikeKeys = {};
//        Map<String, Object> params = ParamUtils.toParams(object, addLikeKeys);
//        page = menuService.pageList(page, params);
//        return Result.success(page);
//    }

//    @ApiOperation(value = "查询分页数据")
//    @PostMapping(value = "/list")
//    public Result list(@RequestBody(required = false) Map<String, Object> object) {
//        Page<Menu> page = ParamUtils.toPage(Menu.class, object);
//        // like字段-@TableField(condition=SqlCondition.LIKE)
//        Menu entity = ParamUtils.toEntity(Menu.class, object);
//        page = menuService.pageList(page, entity);
//        return Result.success(page);
//    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/list")
    public Result list(@RequestBody(required = false) Map<String, Object> object) {
        Page<Menu> page = ParamUtils.toPage(Menu.class, object);
        // 第三种使用Entity进行查询，like字段-@TableField(condition=SqlCondition.LIKE) 等等其它
        Menu entity = ParamUtils.toEntity(Menu.class, object);
		QueryWrapper<Menu> qw = new QueryWrapper<>();
		if (null != entity) {
			qw.setEntity(entity);
		}
		// qw.orderByAsc("");
		page = menuService.page(page, qw);
		return Result.success(page);
    }

    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/get")
    public Result get(String id) {
        if (null == id || id.equals("")) {
            return Result.fail("ID不能为空！");
        }
        Menu entity = menuService.getById(id);
        return Result.success(entity);
    }

    @ApiOperation(value = "新增/修改数据")
    @PostMapping(value = "/save")
    public Result save(HttpServletRequest request, @RequestBody Menu entity) {
        if (null == entity) {
            return Result.fail("对象不能为空！");
        }
        menuService.saveOrUpdate(entity);


        return Result.success(entity);
    }

    @ApiOperation(value = "批量删除数据")
    @PostMapping(value = "/delete")
    public Result delete(HttpServletRequest request, @RequestBody List<String> idList) {
        if (null == idList || idList.isEmpty()) {
            return Result.fail("对象不能为空！");
        }
        menuService.removeByIds(idList);


        return Result.success();
    }

}

