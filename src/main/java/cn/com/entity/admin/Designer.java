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
@Table(name="pgzs.designer")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
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
	private String style ;
	private String type ;
	/*
	代表作品
	*/
	private String works ;
	private String years ;
	
	public Designer() {
	}

	@AssignID("uuid")
	@Column(length = 32)
	public String getDesignerId(){
		return  designerId;
	}
	public void setDesignerId(String designerId ){
		this.designerId = designerId;
	}
	
	public String getEducation(){
		return  education;
	}
	public void setEducation(String education ){
		this.education = education;
	}
	
	/**
	* 设计理念
	*@return 
	*/
	public String getIdea(){
		return  idea;
	}
	/**
	* 设计理念
	*@param  idea
	*/
	public void setIdea(String idea ){
		this.idea = idea;
	}
	
	public String getName(){
		return  name;
	}
	public void setName(String name ){
		this.name = name;
	}
	
	public String getStatus(){
		return  status;
	}
	public void setStatus(String status ){
		this.status = status;
	}
	
	public String getStyle(){
		return  style;
	}
	public void setStyle(String style ){
		this.style = style;
	}
	
	public String getType(){
		return  type;
	}
	public void setType(String type ){
		this.type = type;
	}
	
	/**
	* 代表作品
	*@return 
	*/
	public String getWorks(){
		return  works;
	}
	/**
	* 代表作品
	*@param  works
	*/
	public void setWorks(String works ){
		this.works = works;
	}
	
	public String getYears(){
		return  years;
	}
	public void setYears(String years ){
		this.years = years;
	}
	

}
