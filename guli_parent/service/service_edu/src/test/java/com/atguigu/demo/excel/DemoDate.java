package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

//lombok
@Data
public class DemoDate {

   //设置Excel 的表头
    @ExcelProperty("学生编号")
     private Integer sno;

    @ExcelProperty("学生姓名")
     private String  sname;


}
