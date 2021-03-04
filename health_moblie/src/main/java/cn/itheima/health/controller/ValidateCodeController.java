package cn.itheima.health.controller;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.constant.RedisMessageConstant;
import cn.itheima.health.entity.Result;
import cn.itheima.health.utlis.SMSUtils;
import cn.itheima.health.utlis.ValidateCodeUtils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: ValidateCodeController
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/3/4 22:00
 * @Version: 1.0
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /***
     *体检预约发送验证码
     * @param telephone:
     * @return: cn.itheima.health.entity.Result
     **/
    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        Jedis jedis = jedisPool.getResource();
        //先从redis中获取看是否存在
        String key = RedisMessageConstant.SENDTYPE_ORDER + ":" + telephone;
        String codeInRedis = jedis.get(key);
        if (null != codeInRedis) {
            //不为空，发发送过了
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }
        //不存在
        //生成验证码
        String validatecode = ValidateCodeUtils.generateValidateCode(6) + "";
        //调用SMSutis发送短息
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validatecode);
            //发送成功存入redis
            jedis.setex(key,10*60,validatecode);
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
    }


}
