package com.example.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类
 *
 */
@Data
public class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 创建者 */
    @TableField(exist = false)
    private String createBy;

    /** 创建时间 */
    @TableField(exist = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;


    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

    @TableField(exist = false)
    private Integer pageNum;

    @TableField(exist = false)
    private Integer pageSize;
}
