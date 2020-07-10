package com.ioc.datasupport.warehouse.service;

import com.google.common.collect.Lists;
import com.ioc.datasupport.component.JasyptComponent;
import com.ioc.datasupport.warehouse.domain.DlRescataDatabase;
import com.ioc.datasupport.warehouse.mapper.DlRescataDatabaseMapper;
import com.ioc.datasupport.warehouse.param.DlRescataDatabaseParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-06-18
 */
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class DlRescataDatabaseServiceImpl extends ServiceImpl<DlRescataDatabaseMapper, DlRescataDatabase> implements DlRescataDatabaseService {

    @Resource
    private JasyptComponent jasyptComponent;

    @Resource
    private DlRescataDatabaseService dlRescataDatabaseService;

    @Override
    @Cacheable(sync = true)
    public List<DlRescataDatabase> getRescataDataBaseList() {
        DlRescataDatabaseParam<DlRescataDatabase> param = new DlRescataDatabaseParam<>();
        ArrayList<Long> idList = Lists.newArrayList( 3L, 5L, 7L, 9L);
        param.setIn_databaseId(idList);
        List<DlRescataDatabase> dbList = this.list(param.genQueryWrapper());
        dbList.forEach(item -> {
            if (!StringUtils.isBlank(item.getDatabaseJsonInfo())) {
                item.setDatabaseJsonInfo(jasyptComponent.decrypt(item.getDatabaseJsonInfo()));
            }
        });

        return dbList;
    }

    @Override
    public List<DlRescataDatabase> getRescataDataBasesShow() {
        List<DlRescataDatabase> rescataDataBaseList = dlRescataDatabaseService.getRescataDataBaseList();
        List<DlRescataDatabase> dbList = new LinkedList<>();
        for (DlRescataDatabase database : rescataDataBaseList) {
            DlRescataDatabase db = new DlRescataDatabase();
            db.setDatabaseId(database.getDatabaseId());
            db.setDatabaseName(database.getDatabaseName());
            db.setRepositoryType(database.getRepositoryType());
            db.setDatabaseType(database.getDatabaseType());

            dbList.add(db);
        }

        return dbList;
    }

    @Override
    public DlRescataDatabase getRescataDataBaseById(@NotNull Long id) {
        List<DlRescataDatabase> rescataDataBaseList = dlRescataDatabaseService.getRescataDataBaseList();
        for (DlRescataDatabase dlRescataDatabase : rescataDataBaseList) {
            if (Objects.equals(dlRescataDatabase.getDatabaseId(), id)) {
                return dlRescataDatabase;
            }
        }

        return null;
    }
}
