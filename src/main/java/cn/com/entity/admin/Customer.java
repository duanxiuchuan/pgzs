package cn.com.entity.admin;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

import cn.com.annotation.Dict;
import cn.com.common.constant.DictConstantType;
import cn.com.entity.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/* 
* 
* gen by beetlsql 2020-04-02
*/
@Entity
@Table(name="pgzs.customer")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Data
@Accessors(chain = true)
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
	private String designerId;
	private String designerName;
	public Customer() {
	}

	@AssignID("uuid")
	@Column(length = 32)
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
}
