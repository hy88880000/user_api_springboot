package org.shf.utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//redis连接池
public class RedisUtil {
    //从静态代码中初始化，项目启动到停就初始化一次
    private static JedisPool jedisPool;

    //私有化这个类目的不让别人new实例化，让它变成一个单例
    private RedisUtil(){

    }

    //静态代码块
    static {
        //创建Redis连接池的配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置最大连接数
        jedisPoolConfig.setMaxTotal(10);
        //设置最大空闲
        jedisPoolConfig.setMaxIdle(2);
        //设置最小连接
        jedisPoolConfig.setMinIdle(1);
        //设置最大的连接等待时间
        jedisPoolConfig.setMaxWaitMillis(30000);
        //初始Redis连接池，linux IP 和Redis 端口号
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.44.132",6379);
    }
    //从池中拿连接
    public static Jedis getJedis(){
        return jedisPool.getResource();
    };
    //连接用完还给池中
    public static void returnJedis(Jedis jedis){
        jedisPool.returnResource(jedis);
    }





}
