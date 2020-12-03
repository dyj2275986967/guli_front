package com.atguigu.vod.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件中的内容  当项目已启动，spring加载之后，执行接口一个方法
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    //当spring把值加载后 afterPropertiesSet方法就会执行
     //读取配置文件中的内容


      @Value("${aliyun.vod.file.keyid}")
      private String keyid;

      @Value("${aliyun.vod.file.keysecret}")
      private String keysecret;

      //定义静态常量
      public static String KEYID;
      public static String KEYSECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
            KEYID=keyid;
            KEYSECRET=keysecret;
    }
}
