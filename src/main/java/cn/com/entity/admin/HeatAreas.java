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
@Table(name="pgzs.heat_areas")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class HeatAreas extends BaseEntity<HeatAreas> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String areasId ;
	private String address ;
	private String remark ;
	private String status ;
	private String title ;
	
	public HeatAreas() {
	}

	@AssignID("uuid")
	@Column(length = 32)
	public String getAreasId(){
		return  areasId;
	}
	public void setAreasId(String areasId ){
		this.areasId = areasId;
	}
	
	public String getAddress(){
		return  address;
	}
	public void setAddress(String address ){
		this.address = address;
	}
	
	public String getRemark(){
		return  remark;
	}
	public void setRemark(String remark ){
		this.remark = remark;
	}
	
	public String getStatus(){
		return  status;
	}
	public void setStatus(String status ){
		this.status = status;
	}
	
	public String getTitle(){
		return  title;
	}
	public void setTitle(String title ){
		this.title = title;
	}
	

}
