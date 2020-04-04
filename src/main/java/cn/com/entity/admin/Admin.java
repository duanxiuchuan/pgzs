package cn.com.entity.admin;

import cn.com.annotation.Dict;
import cn.com.common.constant.DictConstantType;
import cn.com.entity.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 管理员
 *
 * @author LiDaDa
 * @date 2018-08-17
 */
@Entity
@Table(name = "sys_admin")
@Data
@Accessors(chain = true)
public class Admin extends BaseEntity<Admin> implements Serializable {

    private static final long serialVersionUID = -8944380467491291433L;

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String password;
    private String realName;
    private Date birth;
    private Integer isEnable;
    private String phone;
    private String openId;
    @Dict(type = DictConstantType.ADMIN_GENDER_TYPE)
    private Integer gender;
    private String email;
    private String avatar;
    private Long deptId;
    private Integer isBuiltIn;


    private boolean delFlag = true;

    @Transient
    private String createName;
    @Transient
    private String modifyName;
    @Transient
    private String deptName;


    /**
     * 是否内置
     */
    public enum BuiltIn {
        /**
         * 否
         */
        no,
        /**
         * 是
         */
        yes
    }

    /**
     * 是否启用
     */
    public enum Enable {
        /**
         * 是
         */
        yes,
        /**
         * 否
         */
        no
    }
}
