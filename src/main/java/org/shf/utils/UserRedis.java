package org.shf.utils;



import redis.clients.jedis.Jedis;

import java.util.List;

public class UserRedis {

    public static void set(String key,String value){
        Jedis jedis = RedisUtil.getJedis();
        jedis.set(key,value);
        RedisUtil.returnJedis(jedis);
    }

    public static void set(String key,String value,int seconds){
        Jedis jedis = RedisUtil.getJedis();
        jedis.setex(key,seconds,value);
        RedisUtil.returnJedis(jedis);
    }

    public static String get(String key){
        Jedis jedis = RedisUtil.getJedis();
        String value=jedis.get(key);
        RedisUtil.returnJedis(jedis);
        return value;
    }

    //string 中查询有没有指定的key
    public static Boolean exists(String key){
        Jedis jedis = RedisUtil.getJedis();
        Boolean exists = jedis.exists(key);
        RedisUtil.returnJedis(jedis);
        return exists;
    }

    //redis的hash
    public static void hset(String key,String filed,String value){
        Jedis jedis = RedisUtil.getJedis();
        Long hset = jedis.hset(key, filed, value);
        RedisUtil.returnJedis(jedis);
    }

    //获取hash指定key所有filed的数量
    public static long hlen(String key){
        Jedis jedis = RedisUtil.getJedis();
        Long hlen = jedis.hlen(key);
        RedisUtil.returnJedis(jedis);
        return hlen;
    }

    public static String hget(String key,String filed){
        Jedis jedis = RedisUtil.getJedis();
        String hget = jedis.hget(key,filed);
        RedisUtil.returnJedis(jedis);
        return hget;
    }

    //获取hash key下面的所有数据
    public static List<String> hvals(String key){
        Jedis jedis = RedisUtil.getJedis();
        List<String> hvals = jedis.hvals(key);
        RedisUtil.returnJedis(jedis);
        return hvals;
    }

    //删除hash指定key下的filed
    public static void hdel(String key,String filed){
        Jedis jedis = RedisUtil.getJedis();
        jedis.hdel(key,filed);
        RedisUtil.returnJedis(jedis);
    }



}
