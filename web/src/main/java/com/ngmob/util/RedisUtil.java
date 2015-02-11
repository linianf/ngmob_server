package com.ngmob.util;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Redis工具类
 * 
 * 
 */
public class RedisUtil {

	private static Log log = LogFactory.getLog(RedisUtil.class);

	private JedisPool pool;
	
	private JedisPool datacenterJedisPool;

	/**
	 * 数据中心防刷redis
	 */
	private JedisPool dataPool;


	public JedisPool getDataPool() {
		return dataPool;
	}

	public void setDataPool(JedisPool dataPool) {
		this.dataPool = dataPool;
	}
	/**
	 * redis的List集合 ，向key这个list添加元素
	 * 
	 * @param key
	 *            List别名
	 * @param string
	 *            元素
	 * @return
	 */
	/*
	 * public long rpush(String key, String string) { try { shardedJedis =
	 * pool.getResource(); long ret = shardedJedis.rpush(key, string);
	 * pool.returnResource(shardedJedis); return ret; } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * }
	 */

	/**
	 * 获取key这个List，从第几个元素到第几个元素 LRANGE key start
	 * stop返回列表key中指定区间内的元素，区间以偏移量start和stop指定。
	 * 下标(index)参数start和stop都以0为底，也就是说，以0表示列表的第一个元素，以1表示列表的第二个元素，以此类推。
	 * 也可以使用负数下标，以-1表示列表的最后一个元素，-2表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param key
	 *            List别名
	 * @param start
	 *            开始下标
	 * @param end
	 *            结束下标
	 * @return
	 */
	/*
	 * public List<String> lrange(String key, long start, long end) { try {
	 * shardedJedis = pool.getResource(); List<String> ret =
	 * shardedJedis.lrange(key, start, end); pool.returnResource(shardedJedis);
	 * return ret; } catch (Exception e) { e.printStackTrace(); if (shardedJedis
	 * != null) { pool.returnBrokenResource(shardedJedis); } throw new
	 * JedisException(e); } }
	 */

	public void hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);

		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public void del(String... key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	
	public void lpush(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.lpush(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}
	
	public String lpop(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.lpop(key);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}


	public String dataLpop(String key) {
		Jedis jedis = null;
		try {
			jedis = dataPool.getResource();
			return jedis.lpop(key);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			if (jedis != null) {
				dataPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (dataPool != null) {
				dataPool.returnResource(jedis);
			}
		}
	}

	public void incr(String key, int count) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			for (int i=0;i<count;i++) {
				jedis.incr(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public void hdel(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.hdel(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	public JedisPool getDatacenterJedisPool() {
		return datacenterJedisPool;
	}

	public void setDatacenterJedisPool(JedisPool datacenterJedisPool) {
		this.datacenterJedisPool = datacenterJedisPool;
	}

	/**
	 * 向key赋值
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(key, value);

		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取key的值
	 * 
	 * @param key
	 * @return
	 */

	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String value = jedis.get(key);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取key的值
	 * 
	 * @param key
	 * @return
	 */
	/*
	 * public byte[] getBytes(byte[] key) { try { shardedJedis =
	 * pool.getResource(); byte[] value = shardedJedis.get(key);
	 * pool.returnResource(shardedJedis); return value; } catch (Exception e) {
	 * e.printStackTrace(); if (shardedJedis != null) {
	 * pool.returnBrokenResource(shardedJedis); } throw new JedisException(e); }
	 * }
	 */

	/**
	 * 将多个field - value(域-值)对设置到哈希表key中。
	 * 
	 * @param key
	 * @param map
	 */
	public void hmset(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.hmset(key, map);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 将多个field - value(域-值)对设置到哈希表key中。
	 * 
	 * @param key
	 * @param map
	 */
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.hmget(key, fields);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 给key赋值，并生命周期设置为seconds
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 * @param value
	 */

	public void setex(String key, int seconds, String value) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 为给定key设置生命周期
	 * 
	 * @param key
	 * @param seconds
	 *            生命周期 秒为单位
	 */
	public void expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	/**
	 * 从哈希表key中获取field的value
	 * 
	 * @param key
	 * @param field
	 */

	public String hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			String value = jedis.hget(key, field);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}
	
	/**
	 * 从哈希表key中获取field的value MAP
	 * 
	 * @param key
	 */

	public Map<String,String> hget(String key) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			Map<String,String> value = jedis.hgetAll(key);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				pool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (pool != null) {
				pool.returnResource(jedis);
			}
		}
	}

	
	/**
	 * 
	 * 
	 * @param key
	 */

	public Map<String,String> hgetAllFromDatacenter(String key) {
		Jedis jedis = null;
		try {
			jedis = datacenterJedisPool.getResource();
			Map<String,String> value = jedis.hgetAll(key);
			return value;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				datacenterJedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (datacenterJedisPool != null) {
				datacenterJedisPool.returnResource(jedis);
			}
		}
	}
	
	
	/**
	 * 
	 * 
	 * @param key
	 */

	public Set<Tuple> zrevrangeWithScoresFromDatacenter(String key, int start,
			int end) {
		Jedis jedis = null;
		try {
			jedis = datacenterJedisPool.getResource();
			Set<Tuple> sets = jedis.zrevrangeWithScores(key, start, end);
			return sets;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				datacenterJedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (datacenterJedisPool != null) {
				datacenterJedisPool.returnResource(jedis);
			}
		}
	}
	
	public Long zcardFromDatacenter(String key) {
		Jedis jedis = null;
		try {
			jedis = datacenterJedisPool.getResource();
			Long count = jedis.zcard(key);
			return count;
		} catch (Exception e) {
			log.error(e);
			if (jedis != null) {
				datacenterJedisPool.returnBrokenResource(jedis);
			}
			throw new JedisException(e);
		} finally {
			if (datacenterJedisPool != null) {
				datacenterJedisPool.returnResource(jedis);
			}
		}
	}
	
	
}