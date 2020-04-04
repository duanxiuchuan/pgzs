package cn.com.entity.admin;

import cn.com.entity.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 部门
 *
 * @author LiDaDa
 */
@Entity
@Table(name = "sys_dept")
@Data
@Accessors(chain = true)
public class Dept extends BaseEntity<Dept> implements Serializable {
	private static final long serialVersionUID = 5557356616451194869L;
	@Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long parentId;


}
