package cn.itheima.health.service.impl;

import cn.itheima.health.dao.SetMealDao;
import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.Setmeal;
import cn.itheima.health.service.SetMealService;
import cn.itheima.health.utlis.QiNiuUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service
 * @ClassName: SetMealServiceImpl
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/23 10:48
 * @Version: 1.0
 */
@Service(interfaceClass = SetMealService.class)
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setMealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setMealDao.addSetMealCheckGroupIds(setmealId, checkgroupId);
            }
        }
    }

    @Override
    public PageResult<Setmeal> findpage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<Setmeal> page = setMealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Override
    public Setmeal findById(int id) {
        return setMealDao.findById(id);
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetMealId(int id) {

        return setMealDao.findCheckgroupIdsBySetMealId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //更新套餐
        setMealDao.update(setmeal);
        //删除旧关系
        setMealDao.deleteSetmealCheckgroup(setmeal.getId());
        //添加新关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setMealDao.addSetMealCheckGroupIds(setmeal.getId(), checkgroupId);
            }
        }

    }

    @Override
    public void deleteByid(int id) throws HealthException {
        //判断是否被订单使用
        int count = setMealDao.findOrderCountBySetmealId(id);
        //使用了报错
        if (count > 0) {
            throw new HealthException("套餐被检查组使用了，不能删除");
        }
        //未使用
        //删除套餐与检查组的关系
        setMealDao.deleteSetmealCheckgroup(id);
        //再删除套餐
        setMealDao.deketeById(id);
    }

    @Override
    public List<String> findImgAll() {

        return setMealDao.findImgAll();
    }

    @Override

    public List<Setmeal> findAll() {
        List<Setmeal> setmealList = setMealDao.findAll();
        //拼接完整路径
        setmealList.forEach(setmeal -> {
            setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        });
        //生成列表的静态文件
        generateSetmeal(setmealList);
        return setmealList;
    }

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${out_put_path}")
    private String out_put_path;

    private void genereteHtml(String templateName, Map<String, Object> setmealmap, String filename) {
        //获取模板
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            Template template = configuration.getTemplate("mobile_setmeal.ftl");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            //填充模板
            template.process(setmealmap, bw);
            //关闭
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();





        }
    }

    private void generateSetmealDatals(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            //实例检查组与检查项信息
            Setmeal byDetailId = setMealDao.findByDetailId(setmeal.getId());
            //设置完整的图片路径
            byDetailId.setImg(setmeal.getImg());
            //构建数据
            Map<String, Object> setmealMap = new HashMap<>();
            setmealMap.put("setmeal", byDetailId);
            String templateName = "mobile_setmeal_detail.ftl";
            //配置完整路径
            String fileName = String.format("%s/setmeal_%d.html", out_put_path, byDetailId.getId());
            genereteHtml(templateName, setmealMap, fileName);
        }
    }

    /***
     *
     * @param setmealList: 
     * @return: void
     **/
    private void generateSetmeal(List<Setmeal> setmealList) {
        Map<String, Object> setmealmap = new HashMap<>();
        setmealmap.put("setmealList", setmealList);
        //构建输出
        String output = out_put_path + "mobile_setmeal.html";
        genereteHtml("mobile_setmeal.ftl", setmealmap, output);
    }

    @Override
    public Setmeal findByDetailId(int id) {

        return setMealDao.findByDetailId(id);
    }
}
