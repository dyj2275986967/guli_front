package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ReadDate {

    //index代表第几列
    @ExcelProperty(index=0)
    private Integer sno;

    @ExcelProperty(index=1)
    private String sname;


}
