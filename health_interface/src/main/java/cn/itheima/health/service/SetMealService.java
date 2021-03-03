package cn.itheima.health.service;

import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service
 * @ClassName: SetMealService
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/23 10:48
 * @Version: 1.0
 */
public interface SetMealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findpage(QueryPageBean queryPageBean);

    Setmeal findById(int id);

    List<Integer> findCheckgroupIdsBySetMealId(int id);

    void update(Setmeal setmeal, Integer[] checkgroupIds);

    void deleteByid(int id) throws HealthException;

    List<String> findImgAll();

    List<Setmeal> findAll();

    Setmeal findByDetailId(int id);
}
