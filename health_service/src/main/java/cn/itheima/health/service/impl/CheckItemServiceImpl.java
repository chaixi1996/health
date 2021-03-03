package cn.itheima.health.service.impl;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.dao.CheckItemDao;
import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.CheckItem;
import cn.itheima.health.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service.impl
 * @ClassName: CheckItemServiceImpl
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/19 9:34
 * @Version: 1.0
 * 发布发到zookeeper中的接口
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll() {

        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            //不为空。有查询条件
            //拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //紧接着查询语句会被分页
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());
        //包装到PageResult中再返回
        //1，解耦 2，序列化 3，page对象过多
        return new PageResult<CheckItem>(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteByid(int id) throws HealthException {
        //判断项是否被检查组使用
        int count = checkItemDao.findCountByCheckitemId(id);
        //判断是否为空
        if (count > 0){
            //报错
            throw  new HealthException("该检查项已经被使用了，不能删除");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(int id) {

        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
