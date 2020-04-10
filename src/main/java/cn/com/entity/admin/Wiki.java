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
@Table(name="pgzs.wiki")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Data
@Accessors(chain = true)
@Entity
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
	private String title ;
	@Dict(type = DictConstantType.ADMIN_WIKI_TYPE)
	private String type ;
	private String detail;

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
	

}
