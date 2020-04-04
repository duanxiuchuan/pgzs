package cn.com.common.redis;

import cn.com.config.CoinConfig;
import cn.com.config.RedisConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author LiDaDa
 */
@Service("redisService")
public class RedisService {

    @Autowired
    private RedisConfig redisConfig;

    private static Jedis getJedisResource(RedisConfig redisConfig) {
    	JedisPool pool = redisConfig.redisPoolFactory();
    	return pool.getResource();
    }
	
    public static void close(Jedis used) {
		if (used != null) {
			used.close();
		}
	}
	
	public Set<String> keys(String prefix) {
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
		} else {
			try {
				return jedis.keys(prefix + "*");
			} finally {
				close(jedis);
			}
		}
		return null;
	}
	
	public String get(String key) {
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return null;
		}
		try {
			return jedis.get(key);
		} finally {
			close(jedis);
		}
	}
	
	public void set(String key, String value) {
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
		} else {
			try {
				jedis.set(key, value);
			} finally {
				close(jedis);
			}
		}
	}
	
	/**
	 * <p>TITLE：设置缓存，seconds有效期（秒）</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月15日
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public boolean set(String key, String value, int seconds) {
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return false;
		} else {
			try {
				jedis.setex(key, seconds, value);
			} finally {
				close(jedis);
			}
			return true;
		}
	}

	/**
	 * <p>TITLE：清楚缓存数据</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月16日
	 * 
	 * @param key
	 */
	public boolean remove(String key) {
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return false;
		} else {
			try {
				jedis.del(key);
			} finally {
				close(jedis);
			}
			return true;
		}
	}

	/**
	 * <p>TITLE：获取托管账户List长度</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月15日
	 * 
	 * @return
	 */
	public Long coinAccountlength() {
		String key = CoinConfig.REIDS_COIN_ACCOUNT;
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return 0L;
		} else {
			Long len = 0L;
			try {
				len = jedis.llen(key);
			} finally {
				close(jedis);
			}
			return len;
		}
	}

	/**
	 * <p>TITLE：将托管账户放入redis</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月15日
	 * 
	 * @param account CoinAccount JSON字符串
	 * @return
	 */
	public Long coinAccountAdd(String account) {
		String key = CoinConfig.REIDS_COIN_ACCOUNT;
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return 0L;
		} else {
			Long len = 0L;
			try {
				len = jedis.rpush(key, account);
			} finally {
				close(jedis);
			}
			return len;
		}
	}
	
	/**
	 * <p>TITLE：获取托管账户</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月16日
	 * 
	 * @return
	 */
	public String coinAccountGet() {
		String key = CoinConfig.REIDS_COIN_ACCOUNT;
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return null;
		} else {
			try {
				return jedis.lpop(key);
			} finally {
				close(jedis);
			}
		}
	}

	/**
	 * 
	 * <p>TITLE：激活充值处理（ETH充值激活）</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年11月16日
	 *
	 * @param invest UserInvestOrder JSON字符串
	 * @return
	 */
	public long activRechargeAdd(String invest) {
		String key = CoinConfig.REDIS_INVEST_ACTIVATION;
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return 0L;
		} else {
			Long len = 0L;
			try {
				len = jedis.rpush(key, invest);
			} finally {
				close(jedis);
			}
			return len;
		}
	}
	
	/**
	 * 
	 * <p>TITLE：获取激活充值数据（ETH充值激活）</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年11月16日
	 * 
	 * @return
	 */
	public String activRechargeGet() {
		String key = CoinConfig.REDIS_INVEST_ACTIVATION;
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return null;
		} else {
			try {
				return jedis.lpop(key);
			} finally {
				close(jedis);
			}
		}
	}
	
	/**
	 * 
	 * <p>TITLE：设置社区、推荐奖励数据</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年11月16日
	 *
	 * @param  reward
	 * @return
	 */
	public long rewardInvestAdd(String reward) {
		String key = CoinConfig.REDIS_INVEST_REWARD;
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return 0L;
		} else {
			Long len = 0L;
			try {
				len = jedis.rpush(key, reward);
			} finally {
				close(jedis);
			}
			return len;
		}
	}
	
	/**
	 * 
	 * <p>TITLE：获取推荐、社区奖励数据</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年11月16日
	 * 
	 * @return
	 */
	public String rewardInvestGet() {
		String key = CoinConfig.REDIS_INVEST_REWARD;
		Jedis jedis = getJedisResource(redisConfig);
		if (jedis == null) {
			return null;
		} else {
			try {
				return jedis.lpop(key);
			} finally {
				close(jedis);
			}
		}
	}

//	/**
//	 * <p>TITLE：ETH区块处理 </p>
//	 *
//	 * @author: RM
//	 * @version 1.0
//	 * Created on 2018年10月31日
//	 * 
//	 * @return
//	 */
//	public String dealBlock(String code) {
//		String blockNum = get(CoinConfig.BLOCK_ETH_NUM_REDIS);
//		if ("liya001".equals(code)) {
//			// 跳过当前区块处理
//			int dealNewNum = Integer.parseInt(blockNum) + 1;
//			set(CoinConfig.BLOCK_ETH_NUM_REDIS, dealNewNum + "");
//			return new Date() + " add success : " + dealNewNum;
//		} else if ("liya010".equals(code)) {
//			// 后退一个区块处理
//			int dealNewNum = Integer.parseInt(blockNum) - 1;
//			set(CoinConfig.BLOCK_ETH_NUM_REDIS, dealNewNum + "");
//			return new Date() + " plus success : " + dealNewNum;
//		} else {
//			return new Date() + " fail : " + blockNum;
//		}
//	}
	
	
}
