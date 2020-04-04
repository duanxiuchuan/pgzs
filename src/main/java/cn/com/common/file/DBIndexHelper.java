package cn.com.common.file;

import cn.com.dao.base.FilsDao;
import cn.com.entity.base.Files;

import java.util.List;

/**
 * 从数据库搜索文档信息，对应core_file,和core_file_tag,目前先忽略tag功能
 *
 * @author xiandafu
 */

public class DBIndexHelper {

    public FilsDao filsDao;

    public DBIndexHelper(FilsDao filsDao) {
        this.filsDao = filsDao;
    }

    public Files getFileItemByPath(String path) {
        Files t = new Files();
        t.setPath(path);
        Files dbData = filsDao.templateOne(t);
        return dbData;
    }

    public Files getFileItemById(Long id) {
        Files dbData = filsDao.unique(id);
        return dbData;
    }

    public void createFileItem(Files files, List<FileTag> tags) {
        filsDao.insert(files, true);
        if (tags == null || tags.isEmpty()) {
            return;
        }
        Long fileId = files.getId();
        for (FileTag tag : tags) {
            tag.setFileId(fileId);
        }
        filsDao.getSQLManager().insertBatch(FileTag.class, tags);
    }


    public List<Files> queryByUserId(Long userId, List<FileTag> tags) {
        List<Files> dbDatas = filsDao.createQuery().lambda().andEq(Files::getUserId, userId).select();
        return dbDatas;
    }

    public List<Files> queryByBiz(String bizType, String bizId) {
        Files template = new Files();
        template.setBizType(bizType);
        template.setBizId(bizId);
        List<Files> dbDatas = filsDao.template(template);
        return dbDatas;
    }

    public List<Files> queryByBatchId(String batchId) {
        Files template = new Files();
        template.setFileBatchId(batchId);
        List<Files> dbDatas = filsDao.template(template);
        return dbDatas;
    }
}
