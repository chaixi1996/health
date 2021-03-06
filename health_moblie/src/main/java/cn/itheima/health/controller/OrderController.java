package cn.itheima.health.controller;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.constant.RedisMessageConstant;
import cn.itheima.health.entity.Result;
import cn.itheima.health.pojo.Order;
import cn.itheima.health.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: OrderController
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/3/5 21:13
 * @Version: 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     *体检预约
     * @param paraMap:
     * @return: cn.itheima.health.entity.Result
     **/
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String> paraMap){
        //验证码校验
        Jedis jedis = jedisPool.getResource();
        //获取手机号码
        String telephone = paraMap.get("telephone");
        //redis存入的key
        String key = RedisMessageConstant.SENDTYPE_ORDER+":"+telephone;
        //redis中的验证码
        String codeInredis = jedis.get(key);
        //判断redis中的数据是否为空
        if (StringUtils.isEmpty(codeInredis)) {
            return new Result(false,"请重新获取验证码");
        }
        //前端传递的验证码
        String validateCode = paraMap.get("validateCode");
        if (!codeInredis.equals(validateCode)) {
             return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //redis中移除验证码，测试注释
        //jedis.del(key);
        //预约成功页面展示需要id
        //设置预约类型
        paraMap.put("orderType",Order.ORDERTYPE_WEIXIN);
        Order order = orderService.submit(paraMap);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    /**
     *select m.name member, s.name setmeal, o.orderDate, o.orderType
     * from t_order o,t_setmeal s,t_member m where s.id=o.setmeal_id and
     * o.member_id=m.id and o.id=18
      *预约成功方法
       **/
    @GetMapping("/findById")
    public Result findById(int id){
        Map<String, Object> orderDetail = orderService.findOrderDetail(id);
        return new Result(true,MessageConstant.ORDER_SUCCESS,orderDetail);
    }
}
