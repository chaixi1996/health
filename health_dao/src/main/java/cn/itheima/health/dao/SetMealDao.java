package cn.itheima.health.dao;

import cn.itheima.health.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.dao
 * @ClassName: SetMealDao
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/23 10:49
 * @Version: 1.0
 */
public interface SetMealDao {
    void add(Setmeal setmeal);

    void addSetMealCheckGroupIds(@Param("setmealId") Integer setmealId,@Param("checkgroupIds") Integer checkgroupId);

    Page<Setmeal> findByCondition(String queryString);

    Setmeal findById(int id);

    List<Integer> findCheckgroupIdsBySetMealId(int id);

    void update(Setmeal setmeal);

    void deleteSetmealCheckgroup(Integer id);

    int findOrderCountBySetmealId(int id);

    void deketeById(int id);

    List<String> findImgAll();


    List<Setmeal> findAll();

    Setmeal findByDetailId(int id);
}
