package com.alibaba.csp.sentinel.dashboard.repository.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.SentinelMetricsEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.service.SentinelMetricsService;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * @Date 2023/3/15
 * @Time 17:22
 * @Author Ealvis
 * @Description Not Description
 */
@Component
public class MysqlMetricsRepository implements MetricsRepository<MetricEntity>{

    private static final Logger LOG = LoggerFactory.getLogger(MysqlMetricsRepository.class);

    @Autowired
    private SentinelMetricsService sentinelMetricsService;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    /**
     * Save the metric to the storage repository.
     *
     * @param entity metric data to save
     */
    @Override
    public void save(MetricEntity entity) {
        if (entity == null || StringUtil.isBlank(entity.getApp())) {
            return;
        }
        readWriteLock.writeLock().lock();
        try {
            LOG.info("========> save -> entity: {}", entity.toString());
            SentinelMetricsEntity metrics = SentinelMetricsEntity.setSentinelMetrics(entity);
            metrics.insert();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * Save all metrics to the storage repository.
     *
     * @param metrics metrics to save
     */
    @Override
    public void saveAll(Iterable<MetricEntity> metrics) {
        if (metrics == null) {
            return;
        }
        readWriteLock.writeLock().lock();
        try {
            LOG.info("========> saveAll -> metrics: {}", metrics.toString());
            List<SentinelMetricsEntity> metricsIns = new ArrayList<>();
            metrics.forEach(e -> metricsIns.add(SentinelMetricsEntity.setSentinelMetrics(e)));
            sentinelMetricsService.saveBatch(metricsIns);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * Get all metrics by {@code appName} and {@code resourceName} between a period of time.
     *
     * @param app       application name for Sentinel
     * @param resource  resource name
     * @param startTime start timestamp
     * @param endTime   end timestamp
     * @return all metrics in query conditions
     */
    @Override
    public List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
        List<MetricEntity> results = new ArrayList<>();
        if (StringUtil.isBlank(app)) {
            return results;
        }
        readWriteLock.readLock().lock();
        try {
            QueryWrapper<SentinelMetricsEntity> qw = new QueryWrapper<>();
            qw.eq("app", app);
            qw.eq("resource", resource);
            qw.ge("timestamp", new Date(startTime));
            qw.le("timestamp", new Date(endTime));
            qw.orderByAsc("timestamp");
            List<SentinelMetricsEntity> sentinelMetrics = sentinelMetricsService.list(qw);
            return sentinelMetrics.stream().map(SentinelMetricsEntity::setMetricEntity).collect(Collectors.toList());
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    /**
     * List resource name of provided application name.
     *
     * @param app application name
     * @return list of resources
     */
    @Override
    public List<String> listResourcesOfApp(String app) {
        List<String> results = new ArrayList<>();
        if (StringUtil.isBlank(app)) {
            return results;
        }

        readWriteLock.readLock().lock();
        try {
            results = sentinelMetricsService.getBaseMapper().selectResourceByApp(app);
            return results;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
