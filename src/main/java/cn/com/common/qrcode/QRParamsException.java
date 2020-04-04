package cn.com.common.qrcode;

/**
 * <p>description: to do what </p>
 *
 * @author: RM
 *
 * Created on 2018年6月4日
 *
 * Copyright © 2018力雅软件. All rights reserved.
 */
public class QRParamsException extends Exception {
	private static final long serialVersionUID = 8837582301762730656L;

	/**
	 * 用来创建无参数对象
	 */
	public QRParamsException() {
	}

	/**
	 * 用来创建指定参数对象
	 * @param message
	 */
	public QRParamsException(String message) {
		// 调用超类构造器
		super(message);
	}
}
