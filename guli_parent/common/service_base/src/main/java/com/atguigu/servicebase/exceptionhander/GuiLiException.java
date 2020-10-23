package com.atguigu.servicebase.exceptionhander;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//生成getset方法
@Data
//有参构造
@AllArgsConstructor
//无参构造
@NoArgsConstructor
public class GuiLiException extends RuntimeException{

     private Integer code;//状态码

     private String msg;//异常信息

}
