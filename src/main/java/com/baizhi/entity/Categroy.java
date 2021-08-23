package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categroy implements Serializable {
    private String id;  //id
    private String cate_name; //分类名
    private Integer levels;  //级别
    private String parent_id;  //上级id
}
