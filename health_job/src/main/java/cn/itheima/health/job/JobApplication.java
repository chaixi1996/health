package cn.itheima.health.job;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.job
 * @ClassName: JobApplication
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/26 10:19
 * @Version: 1.0
 */
public class JobApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext cpa = new ClassPathXmlApplicationContext("classpath:spring-jobs.xml");
        System.in.read();

    }
}
