package cn.itheima.health.job;

import cn.itheima.health.service.SetMealService;
import cn.itheima.health.utlis.QiNiuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.job
 * @ClassName: ClearImgJob
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/26 9:56
 * @Version: 1.0
 */
@Component
public class ClearImgJob {
    @Reference
    private SetMealService setMealService;
    /***
     *  图片清除
     * @return: void
     **/
    public void clearImg(){
        //查出7牛上的图片
        List<String> img7Qiu = QiNiuUtils.listFile();
        //查出数据库中的所有图片
        List<String> imgInDb = setMealService.findImgAll();
        //7-数 剩下就是删除
        //相同对象才会移除
        imgInDb.remove(img7Qiu);
        //删除7上的垃圾
        QiNiuUtils.removeFiles(img7Qiu.toArray(new String[]{}));
    }
}
