package cn.com.service.admin.impl;

import cn.com.annotation.Dict;
import cn.com.common.constant.DataConstant;
import cn.com.common.message.I18nMessage;
import cn.com.common.redis.RedisService;
import cn.com.dao.base.BaseDao;
import cn.com.entity.admin.Admin;
import cn.com.entity.base.BaseEntity;
import cn.com.service.admin.BaseService;
import cn.com.service.admin.DictService;
import cn.com.utils.JsonUtil;
import cn.com.utils.StringUtils;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

/**
 * @param <T>
 * @author LiDaDa
 */
public class BaseServiceImpl<T extends BaseEntity<T>> implements BaseService<T> {

    @Autowired
    private BaseDao<T> baseDao;
    @Autowired
    private DictService dictService;
    @Autowired
    protected SQLManager sqlManager;
    @Resource
    protected RedisService redisService;
    @Resource
    protected I18nMessage i18nMessage;

    @Override
    public Long saveModel(T t) {
        return baseDao.insertReturnKey(t).getLong();
    }

    @Override
    public int updateModel(T t) {
        return baseDao.updateTemplateById(t);
    }

    @Override
    public int deleteModel(Object o) {
        return baseDao.deleteById(o);
    }

    @Override
    public T findById(Object object) {
        T t = baseDao.single(object);
        queryEntityAfter(t);
        return t;
    }

    @Override
    public int deleteById(Object object) {
        return baseDao.deleteById(object);
    }

    @Override
    public T save(T entity, Admin current) {
        entity.setCreateDate(new Date());
        entity.setCreator(current.getId());
        entity.setModifyDate(new Date());
        entity.setModifier(current.getId());
        entity.setVersion(0L);
        return entity;
    }

    @Override
    public T update(T entity, Admin current) {
        entity.setModifyDate(new Date());
        entity.setModifier(current.getId());
        return entity;
    }

    @Override
    public List<T> findAll() {
        return baseDao.all();
    }

    @Override
    public List<T> findAll(Class<T> tClass) {
        Query<T> query = sqlManager.query(tClass);
        return query.andEq(DataConstant.DEL_FLAG, true).select();
    }

    @Override
    public void queryListAfter(List list) {
        for (Object bean : list) {
            queryEntityAfter(bean);
        }
    }

    @Override
    public void queryEntityAfter(Object bean) {
        if (bean == null) {
            return;
        }

        if (!(bean instanceof TailBean)) {
            throw new RuntimeException("指定的pojo" + bean.getClass() + " 不能获取数据字典，需要继承TailBean");
        }

        TailBean ext = (TailBean) bean;
        Class c = ext.getClass();
        do {
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Dict.class)) {
                    field.setAccessible(true);
                    Dict dict = field.getAnnotation(Dict.class);

                    try {
                        String display = "";
                        Object fieldValue = field.get(ext);
                        //新增 对应多个value值
                        if(fieldValue.toString().contains(",")){
                            String[] values = fieldValue.toString().split(",");
                            for (String value: values) {
                                if (value != null) {
                                    cn.com.entity.base.Dict dbDict;
                                    String dbDictStr = redisService.get(dict.type() + value.toString());
                                    if (StringUtils.isNotEmpty(dbDictStr) && !"null".equals(dbDictStr)) {
                                        dbDict = JsonUtil.getObject(dbDictStr, cn.com.entity.base.Dict.class);
                                    } else {
                                        dbDict = dictService.findCoreDict(dict.type(), value.toString());
                                    }
                                    redisService.set(dict.type() + value.toString(), JsonUtil.getJson(dbDict));
                                    display += dbDict != null ? dbDict.getName()+"," : null;
                                }
                            }
                        }else if (fieldValue != null) {
                            cn.com.entity.base.Dict dbDict;
                            String dbDictStr = redisService.get(dict.type() + fieldValue.toString());
                            if (StringUtils.isNotEmpty(dbDictStr) && !"null".equals(dbDictStr)) {
                                dbDict = JsonUtil.getObject(dbDictStr, cn.com.entity.base.Dict.class);
                            } else {
                                dbDict = dictService.findCoreDict(dict.type(), fieldValue.toString());
                            }
                            redisService.set(dict.type() + fieldValue.toString(), JsonUtil.getJson(dbDict));
                            display = dbDict != null ? dbDict.getName() : null;
                        }
//                        ext.set(field.getName(), display);
                        field.set(ext,display);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            c = c.getSuperclass();
        } while (c != TailBean.class);
    }

    /**
     * 获取当前注入泛型T的类型
     *
     * @return 具体类型
     */
    private Class<T> getCurrentEntityClassz() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    /**
     * 返回主键
     */
    @Override
    public Long saveModelReturnKey(T t) {
        return Long.valueOf(baseDao.insertReturnKey(t).getKey().toString());
    }
}
