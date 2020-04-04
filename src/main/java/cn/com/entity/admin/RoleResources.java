package cn.com.entity.admin;

import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 角色 - 权限中间表
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Entity
@Table(name = "sys_role_resources")
@Data
@Accessors(chain = true)
public class RoleResources implements Serializable {
	private static final long serialVersionUID = 4079612974937355120L;
	@Id
    private Long roleId;
    @Id
    @Column(name = "res_id")
    private Long resId;

}
