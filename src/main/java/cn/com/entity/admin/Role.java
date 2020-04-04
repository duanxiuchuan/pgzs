package cn.com.entity.admin;

import cn.com.entity.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 角色
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Entity
@Table(name = "sys_role")
@Data
@Accessors(chain = true)
public class Role extends BaseEntity<Role> implements Serializable {
	private static final long serialVersionUID = -3029339934629261718L;
	@Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer isBuiltIn;

    @Transient
    private String checked;


}
