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
@Table(name="pgzs.wiki")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Wiki extends BaseEntity<Wiki> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String wikiId ;
	/*
	点击数
	*/
	private String clicks ;
	private String source ;
	private String status ;
	private String titel ;
	private String type ;
	
	public Wiki() {
	}

	@AssignID("uuid")
	@Column(length = 32)
	public String getWikiId(){
		return  wikiId;
	}
	public void setWikiId(String wikiId ){
		this.wikiId = wikiId;
	}
	
	/**
	* 点击数
	*@return 
	*/
	public String getClicks(){
		return  clicks;
	}
	/**
	* 点击数
	*@param  clicks
	*/
	public void setClicks(String clicks ){
		this.clicks = clicks;
	}
	
	public String getSource(){
		return  source;
	}
	public void setSource(String source ){
		this.source = source;
	}
	
	public String getStatus(){
		return  status;
	}
	public void setStatus(String status ){
		this.status = status;
	}
	
	public String getTitel(){
		return  titel;
	}
	public void setTitel(String titel ){
		this.titel = titel;
	}
	
	public String getType(){
		return  type;
	}
	public void setType(String type ){
		this.type = type;
	}
	

}
