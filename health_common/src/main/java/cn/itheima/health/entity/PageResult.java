package cn.itheima.health.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: health_param
 * @Package: cn.itheima.health.entity
 * @ClassName: PageResult
 * @Author: ChaiXi
 * @Description:
 * @Date: 2021/2/18 13:00
 * @Version: 1.0
 */
public class PageResult<T> implements Serializable {
    private Long total;//总记录数
    private List rows;//当前页结果
    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }
}
