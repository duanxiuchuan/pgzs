package cn.com.entity.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 字典
 *
 * @author
 */
@Entity
@Table(name = "sys_dict")
@Data
@Accessors(chain = true)
public class Dict extends BaseEntity<Dict> implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String value;
    private String type;
    private String typeName;
    private Integer sort;
    private Integer delFlag;
    private String remark;

    @Transient
    private String createName;
    @Transient
    private String modifyName;


}
