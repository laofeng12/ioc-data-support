package com.ioc.datasupport.warehouse.service;

import com.ioc.datasupport.warehouse.domain.DlRescataDatabase;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lsw
 * @since 2020-06-18
 */
public interface DlRescataDatabaseService extends IService<DlRescataDatabase> {

    /** 获取解密后的数据库信息 */
    List<DlRescataDatabase> getRescataDataBaseList();

    List<DlRescataDatabase> getRescataDataBasesShow();

    DlRescataDatabase getRescataDataBaseById(Long id);

    Integer getRepositoryType(Long dbId);
}
