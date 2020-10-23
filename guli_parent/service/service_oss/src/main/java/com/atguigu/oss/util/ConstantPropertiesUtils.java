package com.atguigu.oss.util;

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
       @Value("${aliyun.oss.file.endpoint}")
       private String endpoint;

      @Value("${aliyun.oss.file.keyid}")
      private String keyid;

      @Value("${aliyun.oss.file.keysecret}")
      private String keysecret;

      @Value("${aliyun.oss.file.bucketname}")
      private String bucketname;

      //定义静态常量
      public static String EDNPOINT;
      public static String KEYID;
      public static String KEYSECRET;
      public static String BUCKETNAME;


    @Override
    public void afterPropertiesSet() throws Exception {
            EDNPOINT=endpoint;
            KEYID=keyid;
            KEYSECRET=keysecret;
            BUCKETNAME=bucketname;
    }
}
