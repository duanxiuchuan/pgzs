package cn.com.service.admin.impl;

import cn.com.dao.admin.CaseDao;
import cn.com.entity.admin.Case;
import cn.com.service.admin.CaseService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void add(Case cass) {
        caseDao.insert(cass);
    }

    @Override
    public void update(Case cass) {
        caseDao.updateTemplateById(cass);
    }

    @Override
    public void deleteByCaseId(String caseId) {
        caseDao.deleteByCaseId(caseId);
    }

    @Override
    public List<Case> findAllByStatus() {
        return caseDao.findAllByStatus();
    }
}
