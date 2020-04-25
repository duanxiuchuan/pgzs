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
@Entity
@Table(name="pgzs.exquisite_case")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Data
@Accessors(chain = true)
public class Case extends BaseEntity<Case> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String caseId ;
	/*
	面积
	*/
    @Dict(type = DictConstantType.ADMIN_AREA_TYPE)
	private String area ;
	/*
	小区id
	*/
	private String areasName ;
	private String areasId ;
	private String designerName ;
	private String designerId ;
	/*
	详情
	*/
	private String detail ;
	/*
	户型
	*/
	@Dict(type = DictConstantType.ADMIN_LAYOUT_TYPE)
	private String layout ;
	private String status ;
	/*
	风格
	*/
	@Dict(type = DictConstantType.ADMIN_STYLE_TYPE)
	private String style ;
	/*
	标题
	*/
	private String title ;

	private String cover;
	
	public Case() {
	}

	@AssignID("uuid")
	@Column(length = 32)
	public String getCaseId(){
		return  caseId;
	}
	public void setCaseId(String caseId ){
		this.caseId = caseId;
	}


}
