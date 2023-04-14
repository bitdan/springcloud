package com.example.mybatis_plus.product.entity;

import lombok.Data;

/**
 * @author
 * @description TODO
 * @date 2023-04-01
 */
@Data
public class TreeNode {

    private String id;
    //父节点id
    private String companyName;
    private String parentId;
    private Boolean isLeaf;

    public TreeNode(String id, String companyName, String parentId, Boolean isLeaf) {
        this.id = id;
        this.companyName = companyName;
        this.parentId = parentId;
        this.isLeaf = isLeaf;
    }
}
