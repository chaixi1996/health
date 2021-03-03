package cn.itheima.health.exception;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.exception
 * @ClassName: HealthException
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/21 9:54
 * @Version: 1.0
 */
public class HealthException extends RuntimeException {
    public HealthException(String message){
        super(message);
    }
}
