package cn.com.entity.admin;

import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 用户 - 角色中间表
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Entity
@Table(name = "sys_admin_role")
@Data
@Accessors(chain = true)
public class AdminRole implements Serializable {
	private static final long serialVersionUID = -155303170707592266L;
	@Id
    @GeneratedValue
    private Long roleId;
    @Id
    @GeneratedValue
    private Long adminId;


}
