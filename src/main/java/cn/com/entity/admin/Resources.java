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
 * 权限资源
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Entity
@Table(name = "sys_resources")
@Data
@Accessors(chain = true)
public class Resources extends BaseEntity<Resources> implements Serializable {
	private static final long serialVersionUID = -4902229514826354759L;
	@Id
    @GeneratedValue
    private Long id;
    private String name;
    private String url;
    private Integer type;
    private Long parentId;
    private Integer isEnable;
    private Integer sort;

    /**
     * 是否选中
     */
    @Transient
    private String checked;
    /**
     * 是否有下级
     */
    @Transient
    private String hasChildren;

    /**
     * 类型
     */
    public enum Type {
        /**
         * 一级菜单
         */
        zero,
        /**
         * 二级菜单
         */
        one,
        /**
         * 按钮菜单
         */
        two
    }
    
}
