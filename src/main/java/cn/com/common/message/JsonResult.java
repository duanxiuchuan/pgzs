package cn.com.common.message;

import java.io.Serializable;

/**
 * 返回JSON数据对象
 *
 * @author: LiDaDa
 */
public class JsonResult implements Serializable {

	private static final long serialVersionUID = -4010650516060154042L;

	/** 类型 */
	private int status;

	/** 内容 */
	private String result;

	/** 数据 */
	private Object date;

	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 构造方法
	 */
	public JsonResult() {
	}

	/**
	 * 构造方法
	 *
	 * @param status
	 *            类型
	 * @param result
	 *            内容
	 */
	public JsonResult(int status, String result) {
		this.status = status;
		this.result = result;
		this.date = null;
	}

	/**
	 * 构造方法
	 *
	 * @param status
	 *            类型
	 * @param result
	 *            内容
	 * @param date
	 * 			       数据
	 */
	public JsonResult(int status, String result, Object date) {
		this.status = status;
		this.result = result;
		this.date = date;
	}
	
	/**
	 * 构造方法
	 *
	 * @param status
	 *            类型
	 * @param result
	 *            内容
	 * @param date
	 * 			       数据
	 * @param sign
	 * 			       签名
	 */
	public JsonResult(int status, String result, Object date,String sign) {
		this.status = status;
		this.result = result;
		this.date = date;
		this.sign=sign;
	}

	/**
	 * 错误
	 *
	 * @param result
	 * 			提示语句
	 * @param date
	 * 			返回数据（可空）
	 * @return
	 */
	public static JsonResult error(String result, Object date) {
		JsonResult message = new JsonResult(JsonResultType.ERROR, result, date);
		return message;
	}

	/**
	 * 正确
	 *
	 * @param result
	 * @param date
	 * @return
	 */
	public static JsonResult success(String result, Object date) {
		JsonResult message = new JsonResult(JsonResultType.SUCCESS, result, date);
		return message;
	}

	/**
	 * 警告
	 *
	 * @param result
	 * @param date
	 * @return
	 */
	public static JsonResult warn(String result, Object date) {
		JsonResult message = new JsonResult(JsonResultType.WARN, result, date);
		return message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Object getDate() {
		return date;
	}

	public void setDate(Object date) {
		this.date = date;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
