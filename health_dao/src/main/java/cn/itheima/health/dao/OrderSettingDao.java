package cn.itheima.health.dao;

import cn.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.dao
 * @ClassName: OrderSettingDao
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/27 9:28
 * @Version: 1.0
 */
public interface OrderSettingDao {
    OrderSetting findByOrderDate(Date orderDate);

    void updateNumber(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<Map<String, Integer>> getOrderSettingByMonth(Map<String, String> map);

    void editNumberByOrderDate(OrderSetting orderSetting);
}
