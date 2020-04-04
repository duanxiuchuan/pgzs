package cn.com.utils;

import java.math.BigDecimal;

/**
 * <p>TITLE：数字格式化 </p>
 * <p>DESCRIPTION： </p>
 *
 * @author: RM
 * @version 1.0
 * Created on 2018年11月24日
 * Copyright © 2018LiDaDa. All rights reserved.
 */
public class MathUtils {

	/**
	 * 
	 * <p>TITLE：金额格式化处理（默认保留6位小数）</p>
	 *
	 * @author: RM
	 * @version 1.0
	 * Created on 2018年11月24日
	 * 
	 * @param math
	 * @return
	 */
	public static BigDecimal scale(BigDecimal math) {
		// 直接舍弃多余的小数位
		math = math.setScale(6, BigDecimal.ROUND_DOWN);
		return math;
	}
}
