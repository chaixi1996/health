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
    /**
     * 通过日期查询预约设置信息
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新可预约数
     * @param os
     */
    void updateNumber(OrderSetting os);

    /**
     * 添加预约设置
     * @param os
     */
    void add(OrderSetting os);

    /**
     * 通过月份查询预约设置信息
     * @param map
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(Map<String, String> map);

    /**
     * 更新已预约人数
     */
    void editReservationsByOrderDate(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);
}
