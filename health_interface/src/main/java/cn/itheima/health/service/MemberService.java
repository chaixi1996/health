package cn.itheima.health.service;

import cn.itheima.health.pojo.Member;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service
 * @ClassName: LoginService
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/3/6 0:58
 * @Version: 1.0
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);
}
