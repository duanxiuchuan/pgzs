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
@Table(name="pgzs.heat_areas")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Data
@Accessors(chain = true)
@Entity
public class HeatAreas extends BaseEntity<HeatAreas> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String areasId ;
	@Dict(type = DictConstantType.ADMIN_AREAS_TYPE)
	private String address ;
	private String remark ;
	private String status ;
	private String title ;
	private String detail;
	private String name;
    private String cover;

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
}
