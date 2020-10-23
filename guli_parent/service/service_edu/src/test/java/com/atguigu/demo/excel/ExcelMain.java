package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class ExcelMain {

    public static void main(String[] args) {
        String filename="E:\\学生信息文档.xlsx";

        EasyExcel.read(filename,ReadDate.class,new ExcelsListener()).sheet().doRead();

    }

    public  static List<DemoDate> getDate(){
        List<DemoDate> list=new ArrayList<>();
         for (int i=0;i<10;i++){

              DemoDate data=new DemoDate();
             data.setSname("mangoubiubiu"+i);
             data.setSno(i);
             list.add(data);

         }
          return  list;
    }

}
