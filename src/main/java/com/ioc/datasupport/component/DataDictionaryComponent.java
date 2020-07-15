package com.ioc.datasupport.component;

import com.openjava.framework.sys.domain.SysCode;
import com.openjava.framework.sys.service.SysCodeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

/**
 * 数据字典组件
 *
 * @author: lsw
 * @Date: 2020/7/15 16:00
 */
@Component
public class DataDictionaryComponent {

    @Resource
    private SysCodeService sysCodeService;

    @Resource
    private DataDictionaryComponent dataDictionaryComponent;

    /** 数据湖列的数据类型 */
    public static final String DL_COLUMN_DATA_TYPE = "dl.column.datatype";

    @Cacheable(value = "dataDictionary", key = "#dictionaryKey")
    public Map<String, SysCode> getDictionayMap(String dictionaryKey) {
        Map<String, SysCode> codeMap = sysCodeService.getCodeMap(dictionaryKey);
        if (CollectionUtils.isEmpty(codeMap)) {
            codeMap = Collections.emptyMap();
        }

        return codeMap;
    }

    /**
     * 获取字典值
     *
     * @param dictionaryKey 字典key
     * @param code          字典code
     * @return              字典值
     */
    public String getSysCodeVal(String dictionaryKey, String code) {
        return this.getSysCodeVal(dictionaryKey, code, null);
    }

    /**
     * 获取字典值
     *
     * @param dictionaryKey 字典key
     * @param code          字典code
     * @param defaultVal    默认值
     * @return              字典值
     */
    public String getSysCodeVal(String dictionaryKey, String code, String defaultVal) {
        Map<String, SysCode> dictionayMap = dataDictionaryComponent.getDictionayMap(dictionaryKey);
        SysCode sysCode = dictionayMap.get(code);
        // 存在字典，返回字典值
        if (sysCode != null) {
            return sysCode.getCodevalue();
        }

        // 不存在字典，有默认值，返回默认值
        if (!StringUtils.isEmpty(defaultVal)) {
            return defaultVal;
        }

        return "";
    }
}
