package cn.com.entity.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务
 *
 * @author LiDaDa
 */
@Entity
@Table(name = "job_task")
@Data
@Accessors(chain = true)
public class JobTask implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String group;
    private String cron;
    private Date date;
    private Integer type;
    private String desc;
    private Date startime;
    private Date endtime;
    private String runtime;
    private String info;
    private String code;
    private String method;
    private Integer status;
    private String prefix;
    private Integer isValid;
    
    public enum IsValid {
    	/** 暂停 **/
    	stop
    	/** 启动 **/
    	,start
    }
}
