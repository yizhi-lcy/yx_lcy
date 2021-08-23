package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video implements Serializable {
    private String id;
    private String title;
    private String brief;
    private String coverPath;
    private String videoPath;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date mins;
    private Categroy categroy;
    private User user;
    private String group_id;
}