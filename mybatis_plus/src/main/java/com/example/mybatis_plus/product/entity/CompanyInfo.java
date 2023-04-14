package com.example.mybatis_plus.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author 
 * @since 2023-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_company_info")
@ApiModel(value="CompanyInfo对象", description="")
public class CompanyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
      @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "公司名")
    private String companyName;

    @ApiModelProperty(value = "父节点id")
    private String parentId;


}
