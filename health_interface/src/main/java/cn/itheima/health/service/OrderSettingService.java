package cn.itheima.health.service;

import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service
 * @ClassName: OrderSettingService
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/27 9:25
 * @Version: 1.0
 */
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettings) throws HealthException;

    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    void editNumberByDate(OrderSetting orderSetting);
}
