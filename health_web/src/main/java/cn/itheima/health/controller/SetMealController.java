package cn.itheima.health.controller;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.entity.Result;
import cn.itheima.health.pojo.CheckGroup;
import cn.itheima.health.pojo.Setmeal;
import cn.itheima.health.service.SetMealService;
import cn.itheima.health.utlis.QiNiuUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: SetMealController
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/23 10:47
 * @Version: 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Reference
    private SetMealService setMealService;

    /***
     *上传图片
     * @param imgFile:
     * @return: cn.itheima.health.entity.Result
     **/
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //获取原图片后缀名
        String originalFilename = imgFile.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成唯一文件名，拼接后缀名
        String filename = UUID.randomUUID() + extension;
        //调用七牛上传文件
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), filename);
            //返回数据到页面
            Map<String, String> map = new HashMap<>();
            map.put("imgName", filename);
            map.put("domain", QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setMealService.add(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @PostMapping("findpage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult = setMealService.findpage(queryPageBean);
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_FAIL,pageResult);
    }

    @GetMapping("/findById")
    public Result findById(int id){
        //套餐信息
        Setmeal setmeal = setMealService.findById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("setmeal",setmeal);
        map.put("domain",QiNiuUtils.DOMAIN);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,map);
    }

    @GetMapping("/findCheckgroupIdsBySetMealId")
    public Result findCheckgroupIdsBySetMealId(int id){
        List<Integer> ids = setMealService.findCheckgroupIdsBySetMealId(id);
        return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,ids);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setMealService.update(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.UPDATE_SETMEAL_SUCCESS);
    }

    @PostMapping("/deleteByid")
    public Result deleteByid(int id){
        setMealService.deleteByid(id);
        return  new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

}
