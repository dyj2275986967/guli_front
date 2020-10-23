package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelsListener extends AnalysisEventListener<ReadDate> {


    //一行一行读取excel的内容
    @Override
    public void invoke(ReadDate readDate, AnalysisContext analysisContext) {

        System.out.println("++++++++++"+readDate);
    }

    //读取表头信息
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("++++++++++"+headMap);
    }


    //读取完成之后做的操作
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
