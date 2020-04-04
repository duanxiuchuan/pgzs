package cn.com.entity.admin;


import cn.com.entity.base.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @Author: Li Sir
 * @Date: 2018/12/28 14:52
 * @Description: 区域类
 *    君不见高堂明镜悲白发，朝如青丝暮成雪
 */

@Entity
@Table(name = "busi_area")
@Data
@Accessors(chain=true)
public class Area extends BaseEntity<Area> {
  @Id
  @GeneratedValue
  private Long id;
  private Long sort;
  private String fullName;
  private Long grade;
  private String name;
  private String treePath;
  private Long parentId;

  @Transient
  private String checked;


}
