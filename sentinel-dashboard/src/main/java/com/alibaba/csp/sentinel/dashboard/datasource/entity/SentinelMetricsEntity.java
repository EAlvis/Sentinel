package com.alibaba.csp.sentinel.dashboard.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @Date 2023/3/15
 * @Time 17:19
 * @Author Ealvis
 * @Description Not Description
 */
@TableName("sentinel_metrics")
public class SentinelMetricsEntity extends Model<SentinelMetricsEntity> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private Date gmtModified;

    /**
     * 应用名称
     */
    private String app;

    /**
     * 监控信息的时间戳
     */
    private Date timestamp;

    /**
     * 资源名
     */
    private String resource;

    /**
     * 通过qps
     */
    @TableField("pass_qps")
    private Long passQps;

    /**
     * 成功qps
     */
    @TableField("success_qps")
    private Long successQps;

    /**
     * 限流qps
     */
    @TableField("block_qps")
    private Long blockQps;

    /**
     * 异常qps
     */
    @TableField("exception_qps")
    private Long exceptionQps;

    /**
     * 所有successQps的rt的和
     */
    private Double rt;

    /**
     * 本次聚合的总条数
     */
    private Integer count;

    /**
     * 资源的hashCode
     */
    @TableField("resource_code")
    private Integer resourceCode;

    public static SentinelMetricsEntity setSentinelMetrics(MetricEntity entity) {
        SentinelMetricsEntity sentinelMetricsEntity = new SentinelMetricsEntity();
        BeanUtils.copyProperties(entity,sentinelMetricsEntity);
        return sentinelMetricsEntity;
    }

    public static MetricEntity setMetricEntity(SentinelMetricsEntity e) {
        MetricEntity entity = new MetricEntity();
        BeanUtils.copyProperties(e,entity);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Long getPassQps() {
        return passQps;
    }

    public void setPassQps(Long passQps) {
        this.passQps = passQps;
    }

    public Long getSuccessQps() {
        return successQps;
    }

    public void setSuccessQps(Long successQps) {
        this.successQps = successQps;
    }

    public Long getBlockQps() {
        return blockQps;
    }

    public void setBlockQps(Long blockQps) {
        this.blockQps = blockQps;
    }

    public Long getExceptionQps() {
        return exceptionQps;
    }

    public void setExceptionQps(Long exceptionQps) {
        this.exceptionQps = exceptionQps;
    }

    public Double getRt() {
        return rt;
    }

    public void setRt(Double rt) {
        this.rt = rt;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(Integer resourceCode) {
        this.resourceCode = resourceCode;
    }
}
