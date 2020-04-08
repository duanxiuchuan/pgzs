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
@Table(name="pgzs.style")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Style extends BaseEntity<Style> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String styleId ;
	private String detail ;
	private String fuca ;
	private String space ;
	private String status ;
	private String style ;
	private String title;
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Style() {
	}

	@AssignID("uuid")
	@Column(length = 32)
	public String getStyleId(){
		return  styleId;
	}
	public void setStyleId(String styleId ){
		this.styleId = styleId;
	}
	
	public String getDetail(){
		return  detail;
	}
	public void setDetail(String detail ){
		this.detail = detail;
	}

	public String getFuca() {
		return fuca;
	}

	public void setFuca(String fuca) {
		this.fuca = fuca;
	}

	public String getSpace(){
		return  space;
	}
	public void setSpace(String space ){
		this.space = space;
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
	

}
