package com.alibaba.csp.sentinel.dashboard.datasource.mapper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.SentinelMetricsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Date 2023/3/15
 * @Time 17:27
 * @Author Ealvis
 * @Description Not Description
 */
@Mapper
public interface SentinelMetricsMapper extends BaseMapper<SentinelMetricsEntity> {

    /**
     * List resource name of provided application name.
     *
     * @param app application name
     * @return list of resources
     */
    List<String> selectResourceByApp(@Param("app") String app);
}
