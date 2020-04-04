package cn.com.entity.base;

import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 文件管理
 */
@Entity
@Table(name = "sys_file")
public class Files extends BaseEntity<Files> {
    @Id
    @GeneratedValue
    private Long id;
    // 文件名称
    private String name;
    // 路径
    private String path;
    // 业务ID
    private String bizId;
    // 上传人id
    private Long userId;
    // 创建时间
    private Date createTime;
    private Long orgId;
    private String bizType;

    private String fileBatchId;

    public Files() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 文件名称
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 文件名称
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 路径
     * 
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * 路径
     * 
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 业务ID
     * 
     * @return
     */
    public String getBizId() {
        return bizId;
    }

    /**
     * 业务ID
     * 
     * @param bizId
     */
    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    /**
     * 上传人id
     * 
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 上传人id
     * 
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 创建时间
     * 
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * 
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getFileBatchId() {
        return fileBatchId;
    }

    public void setFileBatchId(String fileBatchId) {
        this.fileBatchId = fileBatchId;
    }
    
    
}
