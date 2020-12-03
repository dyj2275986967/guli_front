package com.atguigu.eduservice.entity.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名,模糊查询")
    private String title;

    @ApiModelProperty(value = "发布状态 Draft未发布 Normal已发布")
    private String status;

}
