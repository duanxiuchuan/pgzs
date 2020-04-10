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
* gen by beetlsql 2020-04-03
*/
@Table(name="pgzs.designer")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Data
@Accessors(chain = true)
@Entity
public class Designer  extends BaseEntity<Designer> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String designerId ;
	private String education ;
	/*
	设计理念
	*/
	private String idea ;
	private String name ;
	private String status ;
	@Dict(type = DictConstantType.ADMIN_STYLE_TYPE)
	private String style ;
	@Dict(type = DictConstantType.ADMIN_DESIGENR_TYPE)
	private String type ;
	/*
	代表作品
	*/
	private String works ;
	private String caseName;
	private String years ;
	private String title;
	private String appointment;
	private String clicks;
	private String cases;
	public Designer() {
	}

	@AssignID("uuid")
	@Column(length = 32)
	public String getDesignerId() {
		return designerId;
	}

	public void setDesignerId(String designerId) {
		this.designerId = designerId;
	}
}
