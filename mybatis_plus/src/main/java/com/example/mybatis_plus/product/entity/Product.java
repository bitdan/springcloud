package com.example.mybatis_plus.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xsz
 * @since 2023-03-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product")
@ApiModel(value = "Product对象" , description = "存放子弹")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("用户类别")
    private Integer category;

    @ApiModelProperty("总数")
    private Integer count;

    @ApiModelProperty("描述")
    private String description;


}
