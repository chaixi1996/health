package cn.itheima.health.controller;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.constant.RedisMessageConstant;
import cn.itheima.health.entity.Result;
import cn.itheima.health.pojo.Member;
import cn.itheima.health.service.MemberService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: LoginController
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/3/6 0:45
 * @Version: 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/check")
    public Result check(@RequestBody Map<String, String> paraMap, HttpServletResponse response){
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
        jedis.del(key);
        jedis.close();

        //会员判断
        Member member = memberService.findByTelephone(telephone);
        if (null == member) {
            //添加会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }
        //跟踪用户行为
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        //存活时间30天
        cookie.setMaxAge(30*24*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
