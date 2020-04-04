package cn.com.utils;
import org.apache.shiro.crypto.hash.Md5Hash;

import cn.com.entity.admin.Admin;
/**
 * @author LiDaDa
 */
public class PasswordHelper {

	public void encryptPassword(Admin admin) {
		String newPassword = new Md5Hash(admin.getPassword(), admin.getUserName()).toHex();
		admin.setPassword(newPassword);
	}
//	public static void main(String[] args) {
//		PasswordHelper passwordHelper = new PasswordHelper();
//		Admin admin = new Admin();
//		admin.setUserName("333333");
//		admin.setPassword("333333");
//		passwordHelper.encryptPassword(admin);
//		System.out.println(admin.getPassword());
//	}
}
