package cn.itheima.health.controller;

import cn.itheima.health.constant.MessageConstant;
import cn.itheima.health.entity.PageResult;
import cn.itheima.health.entity.QueryPageBean;
import cn.itheima.health.entity.Result;
import cn.itheima.health.pojo.CheckGroup;
import cn.itheima.health.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.javafx.logging.PulseLogger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.controller
 * @ClassName: CheckGroupController
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/21 11:57
 * @Version: 1.0
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    /***
     *  添加
     * @param checkGroup:
     * @param checkitemIds:
     * @return: cn.itheima.health.entity.Result
     **/
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        // 调用服务添加
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    /***
     *  分页查询
     * @param queryPageBean:
     * @return: cn.itheima.health.entity.Result
     **/
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
       PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /***
     *通过id查询
     * @return: null
     **/
    @GetMapping("/findById")
    public Result findById(int id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }
    /***
     *  根据检查项id查询检查组id
     * @param id:
     * @return: cn.itheima.health.entity.Result
     **/
    @GetMapping("/findCheckItemByCheckGroupId")
    public Result findCheckItemByCheckGroupId(int id){
        //查询选中的id
        List<Integer> list = checkGroupService.findCheckItemByCheckGroupId(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
    /***
     * 修改
     * @param checkGroup:
     * @param checkitemIds:
     * @return: cn.itheima.health.entity.Result
     **/
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        // 调用服务修改
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    /***
     *  删除
     **/
    @PostMapping("/deleteByid")
    public  Result deleteByid(int id){
        checkGroupService.deleteByid(id);
        return  new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
    @GetMapping("findAll")
    public Result findAll(){
        List<CheckGroup> list = checkGroupService.findAll();
        return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
}
