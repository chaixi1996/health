package cn.itheima.health.service.impl;

import cn.itheima.health.dao.MemberDao;
import cn.itheima.health.dao.OrderDao;
import cn.itheima.health.dao.OrderSettingDao;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.Member;
import cn.itheima.health.pojo.Order;
import cn.itheima.health.pojo.OrderSetting;
import cn.itheima.health.service.OrderService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service.impl
 * @ClassName: OrderServiceImpl
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/3/5 21:47
 * @Version: 1.0
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Order submit(Map<String, String> paraMap) {
        //1，通过日期查询预约设置是否存在	t_orderetting
        String orderD = paraMap.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = null;
        try {
            orderDate = sdf.parse(orderD);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HealthException("日期格式错误");
        }
        OrderSetting os = orderSettingDao.findByOrderDate(orderDate);
        if (null == os) {
            //	不存在就报错
            throw new HealthException("所选日期不能预约");
        }
        //	存在，是否预约满，
        if (os.getReservations() >= os.getNumber()) {
            //已满报错
            throw new HealthException("预约已经爆满，请稍后再试");
        }
        //2，判断是否为会员，通过手机号码	t_member
        String telephone = paraMap.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if (null == member) {
            //	非会员
            //		添加会员，获取id
            member = new Member();
            member.setIdCard(paraMap.get("idCard"));
            member.setName("name");
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setSex(paraMap.get("sex"));

            //添加会员
            memberDao.add(member);
        }
        //3，判断是否重复预约，通过member_id	t_order
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.valueOf(paraMap.get("setmealId")));
        List<Order> orderList = orderDao.findByCondition(order);
        //	重复报错
        if (null != orderList && orderList.size() > 0) {
            throw new HealthException("不能重复预约");
        }
        //	没重复
        //	可预约，添加订单
        order.setOrderType(paraMap.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.add(order);
        //4，更新已经预约的数量	t_ordersetting
        orderSettingDao.editReservationsByOrderDate(os);
        return order;
    }
}
