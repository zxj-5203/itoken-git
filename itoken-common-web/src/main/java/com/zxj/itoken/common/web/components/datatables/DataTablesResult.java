package com.zxj.itoken.common.web.components.datatables;

import com.zxj.itoken.common.dto.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Bootstrap Datatables 结果集；
 *
 * @author zxj
 * @date 2020/06/28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataTablesResult extends BaseResult implements Serializable {
    private int draw;
    private int recordsTotal;  // 记录总数
    private int recordsFiltered;
    private String error;
}
