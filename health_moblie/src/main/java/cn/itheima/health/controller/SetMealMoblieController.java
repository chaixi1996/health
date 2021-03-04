package cn.itheima.health.controller;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.entity.Result;
import cn.itheima.health.pojo.Setmeal;
import cn.itheima.health.service.SetMealService;
import cn.itheima.health.utlis.QiNiuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: SetMealMoblieController
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/28 14:00
 * @Version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealMoblieController {
    @Reference
    private SetMealService setMealService;

    @GetMapping("/getSetmeal")
    public Result getSetmeal() {
        //查询所以
        List<Setmeal> list = setMealService.findAll();
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
    }

    @PostMapping("/findByDetailId")
    public Result findByDetailId(int id) {
        Setmeal setmeal = setMealService.findByDetailId(id);
        //拼接图片完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmeal);
    }

    @PostMapping("/findById")
    public Result findById(int id){
        //套餐信息
        Setmeal setmeal = setMealService.findById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
