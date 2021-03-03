package cn.itheima.health.service;

import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service
 * @ClassName: CheckItemService
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/19 10:29
 * @Version: 1.0
 */
public interface CheckItemService {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    void deleteByid(int id)throws HealthException;

    CheckItem findById(int id);

    void update(CheckItem checkItem);
}
