package cn.com.service.admin.impl;

import cn.com.dao.base.DictDao;
import cn.com.entity.base.Dict;
import cn.com.service.admin.DictService;
import cn.com.utils.JsonListUtil;
import cn.com.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典
 *
 * @author LiDaDa
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {
    @Autowired
    private DictDao dictDao;

    @Override
    public List<Dict> findAll(PageQuery query) {
        return dictDao.findAll(query);
    }

    @Override
    public Dict findCoreDict(String type, String value) {
        List<Dict> list;
        String dictList =  redisService.get(type);
        if (StringUtils.isNotEmpty(dictList) && !"null".equals(dictList)) {
            list = JsonListUtil.jsonToList(dictList, Dict.class);
        } else {
            list = dictDao.findByType(type);
        }
        redisService.set(type, JSONObject.toJSON(list).toString());
        if (list == null) {
            return null;
        }
        for (Dict dict : list) {
            if (dict.getValue().equals(value)) {
                return dict;
            }
        }
        return null;
    }

    @Override
    public Dict findDictByName(String type, String name) {
        return dictDao.findDictByName(type, name);
    }

    @Override
    public List<Dict> findByDicType(String type) {
        Query<Dict> query = sqlManager.query(Dict.class);
        List<Dict> list = query.andEq("type", type).select();
        return list;
    }

    @Override
    public void update(Dict dict) {
        dictDao.updateTemplateById(dict);
    }

    @Override
    public int deleteByDictId(Long dictId) {
        return dictDao.deleteByDictId(dictId);
    }

    @Override
    public List<Dict> findByType(String type) {
         List<Dict> dictList = dictDao.findByType(type);
         return dictList;
    }
}
