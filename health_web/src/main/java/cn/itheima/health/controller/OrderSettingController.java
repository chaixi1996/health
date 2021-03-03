package cn.itheima.health.controller;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.entity.Result;
import cn.itheima.health.pojo.OrderSetting;
import cn.itheima.health.service.OrderSettingService;
import cn.itheima.health.utlis.POIUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: OrderSettingController
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/27 9:08
 * @Version: 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /***
     *文件上传
     * @param excelFile:
     * @return: cn.itheima.health.entity.Result
     **/
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        //解析
        try {
            List<String[]> excelfile = POIUtils.readExcel(excelFile);
            //转换
            List<OrderSetting> orderSettings = new ArrayList<OrderSetting>();
            OrderSetting os = null;
            SimpleDateFormat sdf= new SimpleDateFormat(POIUtils.DATE_FORMAT);
            for (String[] arr : excelfile) {
                //每个数组arr代表一行记录
                Date orderDate = sdf.parse(arr[0]);
                int number = Integer.valueOf(arr[1]);
                os = new OrderSetting(orderDate,number);
                orderSettings.add(os);
            }
            //调用服务
            orderSettingService.add(orderSettings);
            //返回
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        //调用服务
        List<Map<String,Integer>> list = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
    }

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }
}
