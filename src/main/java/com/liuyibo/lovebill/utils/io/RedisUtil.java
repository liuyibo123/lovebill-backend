package com.liuyibo.lovebill.utils.io;

import com.liuyibo.lovebill.utils.redis.sharded.ShardedRedisUtil;

import java.util.Set;

/**
 * Redis 工具类
 * Created by lambo on 2016/11/26.
 */
public class RedisUtil {

	private static ShardedRedisUtil shardedRedisUtil = ShardedRedisUtil.getInstance();


	/**
	 * 设置 String
	 * @param key
	 * @param value
	 */
	public  static void set(String key, String value) {
		shardedRedisUtil.set(key,value);
	}


	/**
	 * 设置 String 过期时间
	 * @param key
	 * @param value
	 * @param seconds 以秒为单位
	 */
	public  static void set(String key, String value, int seconds) {
		shardedRedisUtil.setex(key,seconds,value);
	}


	/**
	 * 获取String值
	 * @param key
	 * @return value
	 */
	public  static String get(String key) {
		return shardedRedisUtil.get(key);
	}


	/**
	 * 删除值
	 * @param key
	 */
	public  static void del(String key) {
		shardedRedisUtil.del(key);
	}


	/**
	 * lpush
	 * @param key
	 * @param key
	 */
	public static void lpush(String key, String... strings) {
		shardedRedisUtil.lpush(key,strings);
	}

	/**
	 * lrem
	 * @param key
	 * @param count
	 * @param value
	 */
	public  static void lrem(String key, long count, String value) {
		shardedRedisUtil.lrem(key,count,value);
	}

	/**
	 * sadd
	 * @param key
	 * @param value
	 */
	public  static void sadd(String key, String value) {
		shardedRedisUtil.sadd(key,value);
	}

	/**
	 * incr
	 * @param key
	 * @return value
	 */
	public synchronized static Long incr(String key) {
		return shardedRedisUtil.incr(key);
	}

	/**
	 * decr
	 * @param key
	 * @return value
	 */
	public  static Long decr(String key) {
		return shardedRedisUtil.decr(key);
	}

	/**
	 * scard
	 * @param key
	 * @return
	 */
	public  static Long scard (String key) {
		return shardedRedisUtil.scard(key);
	}

	/**
	 * expire
	 * @param key
	 * @param seconds
	 */
	public  static Long expire (String key, int seconds) {
		return shardedRedisUtil.expire(key,seconds);
	}

	/**
	 * smembers
	 * @param key
	 * @return
	 */
	public static Set<String> smembers(String key){
		return shardedRedisUtil.smembers(key);
	}

}