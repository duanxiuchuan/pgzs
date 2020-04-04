package cn.com.entity.base;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.annotatoin.Version;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @param <M>
 * @author LiDaDa
 */
@Data
@Accessors(chain = true)
public class BaseEntity<M> extends TailBean implements Serializable {

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    private Long creator;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;
    private Long modifier;
    @Version
    private Long version;

    @JsonAnyGetter
    @JsonAnySetter
    @Override
    public Map<String, Object> getTails() {
        return super.getTails();
    }


}
