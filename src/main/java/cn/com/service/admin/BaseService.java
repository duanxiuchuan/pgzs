package cn.com.service.admin;

import cn.com.entity.admin.Admin;

import java.util.List;

/**
 * 基础类
 *
 * @author LiDaDa
 * @param <T>
 */
public interface BaseService<T> {

    /**
     * 保存
     *
     * @param t
     * @return
     */
    public Long saveModel(T t);

    /**
     * 更新实体
     *
     * @param t 实体
     * @return
     */
    public int updateModel(T t);

    /**
     * 删除实体
     *
     * @param o
     * @return
     */
    public int deleteModel(Object o);

    /**
     * 查询所有
     *
     * @return
     */
    public List<T> findAll();

    /**
     * 查询未删除的数据列表(del_flag=true)
     *
     * @param tClass
     * @return
     */
    public List<T> findAll(Class<T> tClass);

    /**
     * 通过id查询实体
     *
     * @param object
     * @return
     */
    public T findById(Object object);

    /**
     * 保存实体
     *
     * @param entity
     * @param current
     * @return
     */
    public T save(T entity, Admin current);

    /**
     * 修改实体
     *
     * @param entity
     * @param current
     * @return
     */
    public T update(T entity, Admin current);

    /**
     * 通过id删除数据
     *
     * @param object
     * @return
     */
    public int deleteById(Object object);

    /**
     * 查询集合
     *
     * @param list
     */
    public void queryListAfter(List list);

    /**
     * 查询实体
     *
     * @param bean
     */
    public void queryEntityAfter(Object bean);
    /**
     *  保存实体返回主键
     */
    public Long saveModelReturnKey(T t);
}
