package cn.itheima.health.dao;

import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.CheckGroup;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.dao
 * @ClassName: CheckGroupDao.xml
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/21 11:59
 * @Version: 1.0
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param("checkGroupid") Integer checkGroupid,@Param("checkitemId") Integer checkitemId);

    Page<CheckGroup> findPage(String queryString);

    CheckGroup findById(int id);

    List<Integer> findCheckItemByCheckGroupId(int id);

    void update(CheckGroup checkGroup);

    void deleteCheckGroupCheckItem(Integer id);

    int findSetmealCountByCheckGroupId(int id);

    void deleteById(int id);

    List<CheckGroup> findAll();

}
