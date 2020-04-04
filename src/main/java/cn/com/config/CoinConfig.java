package cn.com.config;

/**
 * <p>TITLE： 数据货币相关配置信息</p>
 *
 * @author: RM
 * @version 1.0
 * Created on 2018年10月11日
 * Copyright © 2018LiDaDa. All rights reserved.
 */
public class CoinConfig {

	public static final String WEB_TITLE = "webTitle";
	
	public static final String WEB_BASE_URL = "http://www.diaocoin.io";
	
	public static final String INVITE_URL = WEB_BASE_URL + "/common/user/invite/${code}/${user}";
	
//	public static final String RESOURCE_BASIC_PATH = "/data/lotacoin/images/";
	public static final String RESOURCE_BASIC_PATH = "d://temp";
	
	/**
	 * 字典类型
	 */
	public static final String DICT_COIN = "coinType";
	public static final String DICT_MONEY = "moneyType";
	public static final String DICT_MESSAGE = "messageType";
	public static final String DICT_INVEST = "investState";
	
	/**
	 * 跳转地址
	 */
	public static final String USER_LOGIN_VIEW = "redirect:/user/login";
	public static final String USER_INDEX_VIEW = "redirect:/user/pc/money/info";
	public static final String USER_WAP_LOGIN_VIEW = "redirect:/user/wap/login/index";
	public static final String USER_WAP_INDEX_VIEW = "redirect:/user/wap/index";
	
	/**
	 * 数字货币接口地址
	 */
	public static final String COIN_BTC_URL = "http://127.0.0.1:8888";
	public static final String COIN_ETH_URL = "http://127.0.0.1:8545";
	public static final String COIN_USDT_URL = "http://127.0.0.1:8888";
	public static final String COIN_DIAO_URL = "http://127.0.0.1:8545";
	
	/**
	 * USDT 正式网络usdt=31，测试网络可以用2
	 */
	public static final int ACCOUNT_USDT_PROPERTYID = 31;
	/**
	 * 托管账户（BTC/USDT）注册数据
	 */
	public static final String ACCOUNT_BTC_OR_USDT_NAME = "EscrowAccount";
	/**
	 * eth及diao托管账户密码
	 */
	public static final String ACCOUNT_ETH_OR_DIAO_PASSWORD = "Escrow@Password";

//	/**
//	 * ETH转账剩余额度
//	 */
//	public static final String ACCOUNT_ETH_LIMIT = "0.1"; 
	/**
	 * ETH总账户及密码
	 */
	public static final String ACCOUNT_COLLECT_ETH_USER = "";
	public static final String ACCOUNT_COLLECT_ETH_PASS = "";
	/**
	 * ETH总账户转入限制
	 */
	public static final String ACCOUNT_COLLECT_ETH_LIMIT = "0.5";
	
	/**
	 * BTC 区块信息
	 */
	public static final String BLOCK_BTC_NUM_REDIS = "BLOCK_BTC_NUM";
	public static final String BLOCK_BTC_NUM = "548381";
	/**
	 * USDT 区块信息
	 */
	public static final String BLOCK_USDT_NUM_REDIS = "BLOCK_USDT_NUM";
	public static final String BLOCK_USDT_NUM = "550449";
	/**
	 * ETH 区块信息
	 */
	public static final String BLOCK_ETH_NUM_REDIS = "BLOCK_ETH_NUM";
	public static final String BLOCK_ETH_NUM = "6721504";
	/**
	 * 代币（DIAO） 区块信息
	 */
	public static final String BLOCK_TOKEN_NUM_REDIS = "BLOCK_TOKEN_NUM";
	public static final String BLOCK_TOKEN_NUM = "6562440";
	
	/**
	 * 代币参数
	 */
	public static final String PARAM_TOKEN_CONTRACT_ADDRESS = "0x0a47b6ac96d639e68d05a13ed5d82cce26b55107";
	public static final String PARAM_TOKEN_WALLET_PATH = "/data/geth/gethdata/keystore";
	
	////////////////////////////////////////////数字货币 相关////////////////////////////////////////////////
	/**
	 * 充值确认数判定值
	 */
	public static final int INCOME_SURE_TIMES = 3;
	/**
	 * 确认时间间隔（分钟 * 60 * 1000）
	 */
	public static final int INCOME_SURE_DATE = 3 * 60 * 1000;
	/**
	 * 激活账户至少充值的币的数量
	 */
	public static final String ACTIVATION_RECHARGE_NUM = "1";
	/**
	 * 提现默认费率
	 */
	public static final String OUTCOME_DEFAULT_REATE = "0.05";
	/**
	 * 提现默认限制
	 */
	public static final String OUTCOME_DEFAULT_LIMIT = "0.01";
	/**
	 * 充值默认限制
	 */
	public static final String INCOME_DEFAULT_LIMIT = "0.01";
	
	
	/**
	 * TODO 数据设置
	 */
	public static final String PARAM_TOKEN_GAS_PRICE = null;
	public static final String PARAM_TOKEN_DIAO_ADRESS = null;
	
	
	////////////////////////////////////////////REDIS 相关////////////////////////////////////////////////
	/**
	 * 托管账户缓存
	 * 存储使用list结构，rpush、lpop命令操作，存储tmp_coin_account_redis表对应生成的Java对象
	 */
	public static final String REIDS_COIN_ACCOUNT = "tmp_coin_account_redis";
	/**
	 * 充值确认数据缓存
	 * 存储使用hash存储
	 */
	public static final String REDIS_COIN_RECHARGE = "tmp_coin_recharge_redis_";
	/**
	 * 提币数据缓存
	 */
	public static final String REDIS_COIN_WITHDRAW = "tmp_coin_feeout_redis_";
	/**
	 * 充值归集缓存
	 * 所有用户托管账户的货币归集到总账户中
	 */
	public static final String REDIS_COIN_COLLECT = "tmp_coin_collect_redis_";
	/**
	 * 激活充值导入投资数据
	 */
	public static final String REDIS_INVEST_ACTIVATION = "tmp_invest_activation_list";
	/**
	 * 社区、推荐奖励
	 */
	public static final String REDIS_INVEST_REWARD = "tmp_invest_reward_list";
	/**
	 * 用户数据
	 */
	public static final String REDIS_USER_COUNT = "tmp_user_count";
	
	////////////////////////////////////////////任务 相关////////////////////////////////////////////////
	/**
	 * 定时任务线程池大小
	 */
	public static final int TASK_POOL_SIZE = 24;
	/**
	 * 定时任务线程池名称前缀
	 */
	public static final String TASK_POOL_NAME = "springboot-task-coin";
	
	////////////////////////////////////////////验证码 相关////////////////////////////////////////////////
	/**
	 * 验证码相关
	 */
	private static final String KEY_PREFIX_UPPHONE = "UPPASS";
	private static final String KEY_PREFIX_UPCASH = "UPCASH";
	private static final String KEY_PREFIX_ADDUSER = "ADDPASS";
	/**
	 * 验证码有效期（5分钟）
	 */
	public static final int CODE_EXPIRE_SECONDS = 60 * 5;
	
	////////////////////////////////////////////邀请码 相关////////////////////////////////////////////////
	/**
	 * 邀请码位数
	 */
	public static final int EXTEND_CODE_SIZE = 8;
	/**
	 * 邀请码发展人数上线
	 */
	public static final int EXTEND_CODE_CHILD = 9;
	
	
	/**
	 * <p>TITLE：获取更改手机验证缓存KEY</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月15日
	 * 
	 * @param sessionId
	 * @param tel
	 * @return
	 */
	public static String keyForUpPhone(String sessionId, String tel) {
		return KEY_PREFIX_UPPHONE + ":" + tel + ":" + sessionId;
	}
	
	/**
	 * <p>TITLE：获取注册用户验证缓存KEY</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月15日
	 * 
	 * @param sessionId
	 * @param tel
	 * @return
	 */
	public static String keyForAddUser(String sessionId, String tel) {
		return KEY_PREFIX_ADDUSER + ":" + tel + ":" + sessionId;
	}
	
	/**
	 * <p>TITLE：获取修改资金密码验证缓存KEY</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年10月16日
	 * 
	 * @param sessionId
	 * @param tel
	 * @return
	 */
	public static String keyForUpCash(String sessionId, String tel) {
		return KEY_PREFIX_UPCASH + ":" + tel + ":" + sessionId;
	}
	

	////////////////////////////////////////////推广 相关////////////////////////////////////////////////

	/**
	 * 推广默认海报
	 */
	public static final String POSTER_BELOW = "static/poster.jpg";
	public static final int POSTER_WATERMARK_FONT_SIZE = 32;
	public static final int POSTER_WATERMARK_FONT_COLOR = Integer.parseInt("38c2ad", 16);
	public static final int POSTER_WATERMARK_X = 548;
	public static final int POSTER_WATERMARK_Y = 1238;

}