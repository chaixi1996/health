package cn.itheima.health.controller;

import cn.itheima.health.entity.Result;
import cn.itheima.health.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: ExceptionHandler
 * @Author: ChaiXi
 * @Description: 全局异常处理
 * @Date: 2021/2/21 10:17
 * @Version: 1.0
 */
@RestControllerAdvice
public class HealthExceptionAdvice  {

    /***
     *info:打印日志，记录流程性的问题
     * debug：记录一些重要的数据，id，orderid，userid
     * error：记录异常信息（堆栈）代替e.printStackTrace();
     **/
    private static final Logger log= LoggerFactory.getLogger(HealthException.class);
    /***
     *ExceptionHandler 用来捕获异常
     * @param e:
     * @return: cn.itheima.health.entity.Result
     **/
    @ExceptionHandler(HealthException.class)
    public Result handleHealthException(HealthException e){
        return new Result(false,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error("发生异常",e);
        return new Result(false,"操作异常，请联系管理员");
    }
}
