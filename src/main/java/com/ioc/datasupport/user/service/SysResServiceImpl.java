package com.ioc.datasupport.user.service;

import com.ioc.datasupport.user.domain.SysRes;
import com.ioc.datasupport.user.dto.ResInfo;
import com.ioc.datasupport.user.mapper.SysResMapper;
import com.ioc.datasupport.user.service.SysResService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单资源表 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2020-07-22
 */
@Service
public class SysResServiceImpl extends ServiceImpl<SysResMapper, SysRes> implements SysResService {

    @Override
    public List<ResInfo> getResInfos() {
        List<SysRes> sysResList = this.list();
        if (CollectionUtils.isEmpty(sysResList)) {
            return Collections.emptyList();
        }

        List<ResInfo> resInfoList = sysResList.stream().map(ResInfo::new).collect(Collectors.toList());
        Map<Long, ResInfo> resInfoMap = resInfoList.stream().collect(Collectors.toMap(ResInfo::getResId, item -> item));
        // 转换成树结构
        for (Iterator<ResInfo> iterator = resInfoList.iterator(); iterator.hasNext();) {
            ResInfo resInfo = iterator.next();
            if (resInfo.getParentId() == 0L) {
                continue;
            }

            ResInfo parent = resInfoMap.get(resInfo.getParentId());
            if (parent == null) {
                iterator.remove();
                continue;
            }

            parent.getSubResList().add(resInfo);
            iterator.remove();
        }

        return resInfoList;
    }
}
