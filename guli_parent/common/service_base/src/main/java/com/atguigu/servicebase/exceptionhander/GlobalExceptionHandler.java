package com.atguigu.servicebase.exceptionhander;


import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j //把异常信息写到文件中，1，加一个slf4j 2,    在异常方法里面加上  log.error(e.getMessage());
public class GlobalExceptionHandler {
    //全局异常处理
    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回数据
    public R error(Exception e){
     return R.error().message(e.getMessage());
    }
    //找异常的话从从特殊到全局
    //特殊异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//返回数据
    public R errorArithmeticException(ArithmeticException e){
        return R.error().message("特殊异常处理");
    }

    //找异常的话从从特殊到全局
    //自定义处理
    @ExceptionHandler(GuiLiException.class)
    @ResponseBody//返回数据
    public R errorGuiLiException(GuiLiException e){
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
