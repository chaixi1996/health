package cn.itheima.health.service;

import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.Order;

import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service
 * @ClassName: OrderService
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/3/5 21:29
 * @Version: 1.0
 */
public interface OrderService {
    /***
     *  预约提交
     * @param paraMap:
     * @return: cn.itheima.health.pojo.Order
     **/
    Order submit(Map<String, String> paraMap) throws HealthException;

    Map<String, Object> findOrderDetail(int id);
}
