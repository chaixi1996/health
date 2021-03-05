package cn.itheima.health.service.impl;

import cn.itheima.health.dao.MemberDao;
import cn.itheima.health.pojo.Member;
import cn.itheima.health.service.MemberService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.service.impl
 * @ClassName: MenberServiceImpl
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/3/6 1:01
 * @Version: 1.0
 */
@Service(interfaceClass = MemberService.class)
public class MenberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
