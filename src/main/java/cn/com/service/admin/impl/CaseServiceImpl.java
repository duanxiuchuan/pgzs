package cn.com.service.admin.impl;

import cn.com.dao.admin.CaseDao;
import cn.com.entity.admin.Case;
import cn.com.service.admin.CaseService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaseServiceImpl extends BaseServiceImpl<Case> implements CaseService {

    @Autowired
    private CaseDao caseDao;
    @Override
    public PageQuery<Case> findPage(PageQuery<Case> query) {
        PageQuery<Case> ret = caseDao.findPage(query);
        queryListAfter(ret.getList());
        return ret;
    }
}
