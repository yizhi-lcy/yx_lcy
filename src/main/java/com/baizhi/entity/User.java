package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelTarget(value = "user")
public class User implements Serializable {
    @Excel(name="ID",width=30,height=30)
    private String id;
    @Excel(name="电话")
    private String phone;
    @Excel(name="姓名")
    private String username;
    @Excel(name="头像",type=2)
    private String head;
    @Excel(name="简介")
    private String brief;
    @Excel(name="微信")
    private String wechat;
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss")
    @Excel(name="注册时间",format="yyyy-MM-dd",width=20)
    private Date mins;
    @Excel(name="状态")
    private Integer status;
}
