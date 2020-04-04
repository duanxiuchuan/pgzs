package cn.com.entity.admin;
import java.io.Serializable;
import java.math.*;
import java.util.Date;
import java.sql.Timestamp;

import cn.com.entity.base.BaseEntity;
import org.beetl.sql.core.annotatoin.AssignID;
import org.beetl.sql.core.annotatoin.Table;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/* 
* 
* gen by beetlsql 2020-04-03
*/
@Table(name="pgzs.exquisite_case")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Case extends BaseEntity<Case> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String caseId ;
	/*
	面积
	*/
	private String area ;
	/*
	小区id
	*/
	private String areasName ;
	private String designer ;
	/*
	详情
	*/
	private String detail ;
	/*
	户型
	*/
	private String layout ;
	private String status ;
	/*
	风格
	*/
	private String style ;
	/*
	标题
	*/
	private String title ;
	
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
	
	/**
	* 小区id
	*@return 
	*/
	public String getAreasName(){
		return  areasName;
	}
	/**
	* 小区id
	*@param  areasName
	*/
	public void setAreasName(String areasName ){
		this.areasName = areasName;
	}
	
	public String getDesigner(){
		return  designer;
	}
	public void setDesigner(String designer ){
		this.designer = designer;
	}
	
	/**
	* 详情
	*@return 
	*/
	public String getDetail(){
		return  detail;
	}
	/**
	* 详情
	*@param  detail
	*/
	public void setDetail(String detail ){
		this.detail = detail;
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
	
	public String getStatus(){
		return  status;
	}
	public void setStatus(String status ){
		this.status = status;
	}
	
	/**
	* 风格
	*@return 
	*/
	public String getStyle(){
		return  style;
	}
	/**
	* 风格
	*@param  style
	*/
	public void setStyle(String style ){
		this.style = style;
	}
	
	/**
	* 标题
	*@return 
	*/
	public String getTitle(){
		return  title;
	}
	/**
	* 标题
	*@param  title
	*/
	public void setTitle(String title ){
		this.title = title;
	}
	

}
