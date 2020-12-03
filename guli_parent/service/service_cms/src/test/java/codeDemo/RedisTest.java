package codeDemo;

import redis.clients.jedis.Jedis;

public class RedisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.102", 6379);
        //存储数据到列表中
        System.out.println(jedis.ping());
    }
}
