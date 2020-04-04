package cn.com.common.session;

import java.io.Serializable;

/**
 * @author
 */
public class LoginUser implements Serializable {

	private static final long serialVersionUID = -4143087469894486702L;

	/**
	 * 登录用户id
	 */
	private Long id;
	
	/**
	 * 用户姓名
	 */
	private String userName;
	
	/**
	 * 邀请码
	 */
	private String code;
	
	/**
	 * 是否激活
	 */
	private boolean isActivation;
	
	/**
	 * 是否需要添加交易密码
	 */
	private boolean addPass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public boolean isActivation() {
		return isActivation;
	}

	public void setActivation(boolean isActivation) {
		this.isActivation = isActivation;
	}

	public boolean isAddPass() {
		return addPass;
	}

	public void setAddPass(boolean addPass) {
		this.addPass = addPass;
	}

	@Override
	public String toString() {
		return "LoginUser [id=" + id + ", userName=" + userName + ", code=" + code + ", isActivation=" + isActivation
				+ ", addPass=" + addPass + "]";
	}

}
