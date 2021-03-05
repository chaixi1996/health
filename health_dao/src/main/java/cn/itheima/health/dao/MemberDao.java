package cn.itheima.health.dao;

import cn.itheima.health.pojo.Member;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.dao
 * @ClassName: MemberDao
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/3/5 22:21
 * @Version: 1.0
 */
public interface MemberDao {
    /***
     *查询所有
     * @return: java.util.List<cn.itheima.health.pojo.Member>
     **/
    List<Member> findAll();
    /***
     *分页查询
     * @return: java.util.List<cn.itheima.health.pojo.Member>
     **/
    Page<Member> selectByCondition(String queryString);
    /***
     *添加会员
     **/
    void add(Member member);
    /***
     *通过id删除
     * @param id: 
     * @return: void
     **/
    void deleteById(Integer id);
    /***
     *通过id查询
     * @param id: 
     * @return: cn.itheima.health.pojo.Member
     **/
    Member findById(Integer id);
    /***
     *通过手机号码查询
     * @param telephone: 
     * @return: cn.itheima.health.pojo.Member
     **/
    Member findByTelephone(String telephone);
    /***
     *修改会员
     * @param member: 
     * @return: void
     **/
    void edit(Member member);
    /***
     *报表查询
     * 统计到某个日期为止，会员总数量
     * @param date:
     * @return: java.lang.Integer
     **/
    Integer findMemberCountBeforeDate(String date);
    /***
     *报表查询
     * 统计某天新增会员数
     * @param date:
     * @return: java.lang.Integer
     **/
    Integer findMemberCountByDate(String date);
    /***
     *报表查询
     * 统计到某个日期新增会员
     * @param date:
     * @return: java.lang.Integer
     **/
    Integer findMemberCountAfterDate(String date);
    /***
     *报表查询
     * 总会员数
     * @return: java.lang.Integer
     **/
    Integer findMemberTotalCount();
}
