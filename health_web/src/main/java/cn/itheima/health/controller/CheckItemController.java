package cn.itheima.health.controller;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.entity.Result;
import cn.itheima.health.exception.HealthException;
import cn.itheima.health.pojo.CheckItem;
import cn.itheima.health.service.CheckItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: CheckItemController
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/19 8:59
 * @Version: 1.0
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
    }


    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     *
     * @return: null
     **/
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult =  checkItemService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);

    }

    @PostMapping("/deleteByid")
    public Result deleteByid(int id){
        checkItemService.deleteByid(id);
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    
    /***
     *通过id查询
     * @return: null
     **/
    @GetMapping("findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

}
