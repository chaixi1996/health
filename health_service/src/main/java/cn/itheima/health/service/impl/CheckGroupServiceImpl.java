package cn.itheima.health.service.impl;

import cn.itheima.health.dao.CheckGroupDao;
import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.CheckGroup;
import cn.itheima.health.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service.impl
 * @ClassName: CheckGroupServiceImpl
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/21 11:58
 * @Version: 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组的id
        Integer checkGroupid = checkGroup.getId();
        //遍历选中的检查项的id
        if (null != checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupid,checkitemId);
            }
        }

    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //分页插件使用
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断与条件查询
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(int id) {

        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemByCheckGroupId(int id) {

        return checkGroupDao.findCheckItemByCheckGroupId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组信息
        checkGroupDao.update(checkGroup);
        //删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //添加新关系
        if (null != checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }
    /***
     *  删除
     * @param id:
     * @return: void
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByid(int id) throws HealthException {
        //判断是否被套餐表使用
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        //使用了，则报错
        if (count > 0) {
            throw new HealthException("该检查组已经被套餐使用了，不能删除");
        }
        //没使用，则删除
        //先删除检查组合检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //在删除检查组
        checkGroupDao.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
