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
@Table(name="pgzs.style")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Data
@Entity
@Accessors(chain = true)
public class Style extends BaseEntity<Style> implements Serializable {

	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 32)
	private String styleId ;
	private String detail ;
	@Dict(type = DictConstantType.ADMIN_FUCA_TYPE)
	private String fuca ;
	@Dict(type = DictConstantType.ADMIN_SPACE_TYPE)
	private String space ;
	private String status ;
	@Dict(type = DictConstantType.ADMIN_STYLE_TYPE)
	private String style ;
	private String title;
	private String remark;
    private String cover;

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


}
