package cn.com.service.admin.impl;

import cn.com.annotation.Log;
import cn.com.common.file.DBIndexHelper;
import cn.com.common.file.FileItem;
import cn.com.common.file.FileTag;
import cn.com.common.file.LocalFileItem;
import cn.com.dao.base.FilsDao;
import cn.com.entity.base.Files;
import cn.com.service.admin.FileService;
import cn.com.utils.DateUtils;
import cn.com.utils.UUIDUtil;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 一个本地文件系统，管理临时文件和用户文件
 *
 */
public class FileServiceImpl implements FileService {
    @Log
    private Logger logger;
    public DBIndexHelper dbHelper = null;
    String root = null;

    public FileServiceImpl(ApplicationContext ctx, String root) {
        this.root = root;
        dbHelper = new DBIndexHelper(ctx.getBean(FilsDao.class));
    }

    @Override
    public FileItem loadFileItemByPath(String path) {
        Files files = dbHelper.getFileItemByPath(path);
        if (files != null) {
            return getFileItem(files);
        }
        LocalFileItem item = new LocalFileItem(root);
        item.setPath(path);
        item.setName(parseTempFileName(path));
        item.setTemp(true);
        return item;
    }

    @Override
    public FileItem createFileTemp(String name) {
        FileItem item = new LocalFileItem(root);
        String fileName = "temp" + File.separator + name + "." + this.suffix();
        item.setPath(fileName);
        item.setName(name);
        item.setTemp(true);
        return item;
    }

    @Override
    public FileItem createFileItem(String name, String bizType, String bizId, Long userId, Long orgId, String fileBatchId, List<FileTag> tags) {
        Files coreFile = new Files();
        coreFile.setBizId(bizId);
        coreFile.setBizType(bizType);
        coreFile.setUserId(userId);
        coreFile.setOrgId(orgId);
        coreFile.setName(name);
        coreFile.setCreateTime(new Date());
        coreFile.setFileBatchId(fileBatchId);
        String dir = DateUtils.now("yyyyMMdd");
        File dirFile = new File(root + File.separator + dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String fileName = name + "." + UUIDUtil.uuid();
        String path = dir + File.separator + fileName;
        coreFile.setPath(path);
        //目前忽略tags
        dbHelper.createFileItem(coreFile, tags);
        return this.getFileItem(coreFile);

    }


    private String suffix() {
        // TODO,改成唯一算法
        return DateUtils.now("yyyyMMddhhmm") + "-" + UUIDUtil.uuid();
    }

    private String parseTempFileName(String path) {
        File file = new File(path);
        String name = file.getName();
        //去掉最后的临时标记
        int index = name.lastIndexOf(".");
        return name.substring(0, index);
    }

    protected FileItem getFileItem(Files file) {
        LocalFileItem item = new LocalFileItem(root);
        item.setName(file.getName());
        item.setPath(file.getPath());
        item.setBizId(file.getBizId());
        item.setBizType(file.getBizType());
        item.setId(file.getId());
        item.setOrgId(file.getOrgId());
        item.setId(file.getId());
        return item;
    }

    protected List<FileItem> getFileItem(List<Files> files) {
        List<FileItem> items = new ArrayList<>(files.size());
        for (Files file : files) {
            items.add(this.getFileItem(file));
        }
        return items;

    }


    @Override
    public FileItem getFileItemById(Long id) {
        return this.getFileItem(dbHelper.getFileItemById(id));
    }

    @Override
    public List<FileItem> queryByUserId(Long userId, List<FileTag> tags) {
        return this.getFileItem(dbHelper.queryByUserId(userId, tags));
    }

    @Override
    public List<FileItem> queryByBiz(String bizType, String bizId) {
        return this.getFileItem(dbHelper.queryByBiz(bizType, bizId));
    }

    @Override
    public List<FileItem> queryByBatchId(String fileBatchId) {
        return this.getFileItem(dbHelper.queryByBatchId(fileBatchId));
    }

    @Override
    public void removeFile(Long id, String fileBatchId) {
        Files file = dbHelper.getFileItemById(id);
        if (!file.getFileBatchId().equals(fileBatchId)) {
            return;
        }

        FileItem item = this.getFileItem(file);
        boolean success = item.delete();
        if (!success) {
            logger.error("删除文件失败 " + file.getName() + ",id=" + file.getId() + " path=" + file.getPath());
            throw new RuntimeException("删除文件失败 " + file.getName());
        }
        dbHelper.filsDao.deleteById(id);
        return;


    }

    @Override
    public void updateFile(String fileBatchId, String bizType, String bizId) {
        dbHelper.filsDao.updateBatchIdInfo(bizType, bizId, fileBatchId);
    }

    @Override
    public FileItem getFileItemById(Long id, String fileBatchId) {
        Files file = dbHelper.getFileItemById(id);
        if (!file.getFileBatchId().equals(fileBatchId)) {
            return null;
        }
        return this.getFileItem(file);
    }


}
