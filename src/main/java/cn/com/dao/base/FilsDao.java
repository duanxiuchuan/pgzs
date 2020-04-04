package cn.com.dao.base;

import cn.com.entity.base.Files;
import org.beetl.sql.core.annotatoin.Sql;

public interface FilsDao extends BaseDao<Files> {
    @Sql("update sys_file set biz_type = ?, biz_id = ? where file_batch_id = ?")
    public void updateBatchIdInfo(String bizType, String bizId, String fileBatchId);
}
