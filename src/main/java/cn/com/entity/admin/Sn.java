package cn.com.entity.admin;

import cn.com.entity.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author: Li Sir
 * @Date: 2019/1/14 10:45
 * @Description: 君不见高堂明镜悲白发，朝如青丝暮成雪
 */
@Entity
@Table(name = "busi_sn")
@Data
@Accessors(chain = true)
public class Sn extends BaseEntity<Sn> {

    @Id
    @GeneratedValue
    private Long id;
    private Long lastValue;
    private Integer type;

    public enum Type{
        product
        ,infoMatch
        ,order
    }
}
