package cn.itheima.health.dao;

import cn.itheima.health.pojo.CheckItem;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.dao
 * @ClassName: CheckItemDao
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/19 9:39
 * @Version: 1.0
 */
public interface CheckItemDao {
    List<CheckItem> findAll();

    void add(CheckItem checkItem);

    Page<CheckItem> findPage(String queryString);

    int findCountByCheckitemId(int id);

    void deleteById(int id);

    CheckItem findById(int id);

    void update(CheckItem checkItem);
}
