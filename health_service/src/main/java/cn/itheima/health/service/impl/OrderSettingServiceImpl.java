package cn.itheima.health.service.impl;

import cn.itheima.health.dao.OrderSettingDao;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.OrderSetting;
import cn.itheima.health.service.OrderSettingService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service.impl
 * @ClassName: OrderSettingServiceImpl
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/27 9:25
 * @Version: 1.0
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<OrderSetting> orderSettings) throws HealthException {
        //遍历
        if (orderSettings != null) {
            for (OrderSetting orderSetting : orderSettings) {
                //通过日期查询预约设置信息
                OrderSetting od = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                //存在
                if (null != od) {
                    //判断已预约是否大于将要更新也预约数量
                    if (od.getReservations() > orderSetting.getNumber()) {
                        //大于报错
                        throw new HealthException(orderSetting.getOrderDate() + "可预约数不能小于预约数");
                    } else {
                        //小于要更新可预约
                        orderSettingDao.updateNumber(orderSetting);
                    }
                } else {
                    //不存在添加
                    orderSettingDao.add(orderSetting);
                }

            }
        }

    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        //拼接开始日期
        String startDate = month + "-1";
        //结束日期
        String endDate = month + "-31";
        Map<String,String> map= new HashMap();
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        return orderSettingDao.getOrderSettingByMonth(map);
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //通过日期判断预约是否存在
        OrderSetting os = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //存在
        if (null != os) {
            //判断已经预约的人数是否大于要更新的最大可预约人数，reverations 》 传递进来的number数量，则不更新，要报错
            if (orderSetting.getNumber() < os.getReservations()) {
                //已经预约的人数高于最大预约人数，不允许
                throw  new HealthException(os.getOrderDate() + "中：可预约数不能小于已预约数");
            }else {
                //reverations <= 传递的number数量，则要更新最大的可预约数量
                orderSettingDao.updateNumber(orderSetting);
            }
        }else {
            //不存在
            //添加预约设置信息
            orderSettingDao.add(orderSetting);
        }

    }
}
