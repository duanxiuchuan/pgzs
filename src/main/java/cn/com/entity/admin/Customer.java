package cn.com.entity.admin;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

import cn.com.annotation.Dict;
import cn.com.common.constant.DictConstantType;
import cn.com.entity.base.BaseEntity;
import lombok.Data;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/* 
* 
* gen by beetlsql 2020-04-02
*/
@Table(name="pgzs.customer")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Data
public class Customer extends BaseEntity<Customer> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String custId ;
	/*
	地区
	*/
	@Dict(type = DictConstantType.ADMIN_AREAS_TYPE)
	private String address ;
	/*
	面积
	*/
	private String area ;
	private String email ;
	/*
	楼盘名称
	*/
	private String houseName ;
	/*
	户型
	*/
	@Dict(type = DictConstantType.ADMIN_LAYOUT_TYPE)
	private String layout ;
	private String name ;
	private String phone ;
	private String status ;
	@Dict(type = DictConstantType.ADMIN_TYPE_TYPE)
	private String type ;
	private Date createTime ;
	
	public Customer() {
	}

	@AssignID("uuid")
	@Column(length = 32)
	public String getCustId(){
		return  custId;
	}
	public void setCustId(String custId ){
		this.custId = custId;
	}
	
	/**
	* 地区
	*@return 
	*/
	public String getAddress(){
		return  address;
	}
	/**
	* 地区
	*@param  address
	*/
	public void setAddress(String address ){
		this.address = address;
	}
	
	/**
	* 面积
	*@return 
	*/
	public String getArea(){
		return  area;
	}
	/**
	* 面积
	*@param  area
	*/
	public void setArea(String area ){
		this.area = area;
	}
	
	public String getEmail(){
		return  email;
	}
	public void setEmail(String email ){
		this.email = email;
	}
	
	/**
	* 楼盘名称
	*@return 
	*/
	public String getHouseName(){
		return  houseName;
	}
	/**
	* 楼盘名称
	*@param  houseName
	*/
	public void setHouseName(String houseName ){
		this.houseName = houseName;
	}
	
	/**
	* 户型
	*@return 
	*/
	public String getLayout(){
		return  layout;
	}
	/**
	* 户型
	*@param  layout
	*/
	public void setLayout(String layout ){
		this.layout = layout;
	}
	
	public String getName(){
		return  name;
	}
	public void setName(String name ){
		this.name = name;
	}
	
	public String getPhone(){
		return  phone;
	}
	public void setPhone(String phone ){
		this.phone = phone;
	}
	
	public String getStatus(){
		return  status;
	}
	public void setStatus(String status ){
		this.status = status;
	}
	
	public String getType(){
		return  type;
	}
	public void setType(String type ){
		this.type = type;
	}
	
	public Date getCreateTime(){
		return  createTime;
	}
	public void setCreateTime(Date createTime ){
		this.createTime = createTime;
	}
	

}
