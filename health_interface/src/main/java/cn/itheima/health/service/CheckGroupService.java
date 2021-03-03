package cn.itheima.health.service;

import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service
 * @ClassName: CheckGroupService
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/21 11:58
 * @Version: 1.0
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    CheckGroup findById(int id);

    List<Integer> findCheckItemByCheckGroupId(int id);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteByid(int id)throws HealthException;

    List<CheckGroup> findAll();

}
