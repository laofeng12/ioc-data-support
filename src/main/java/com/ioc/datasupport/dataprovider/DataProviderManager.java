package com.ioc.datasupport.dataprovider;

import com.ioc.datasupport.dataprovider.annotation.ProviderName;
import com.ioc.datasupport.dataprovider.dto.DatasourceInfo;
import org.ljdp.component.exception.APIException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Service
public class DataProviderManager implements ApplicationContextAware {
    private static Logger LOG = LoggerFactory.getLogger(DataProviderManager.class);

    private static Map<String, Class<? extends SqlProvider>> providers = new HashMap<>(16);

    private static ApplicationContext applicationContext;

    static {
        //初始化，反射加载类到map供后面生成实例
        Set<Class<?>> classSet = new Reflections("com.ioc.datasupport.dataprovider").getTypesAnnotatedWith(ProviderName.class);
        for (Class c : classSet) {
            if (!c.isAssignableFrom(SqlProvider.class)) {
                providers.put(((ProviderName) c.getAnnotation(ProviderName.class)).name(), c);
            } else {
                LOG.error("\n[SqlProviderManager]自定义SqlProvider需要继承org.cboard.dataprovider.SqlProvider");
            }
        }
    }

    /**
     * 获取sql提供工具
     *
     * @param datasourceInfo 数据库信息
     * @return DataProvider
     * @throws Exception 异常
     */
    public static DataProvider getDataProvider(DatasourceInfo datasourceInfo) throws Exception {
        Integer dbType = datasourceInfo.getDatabaseType();
        String type = getTypeByDbType(dbType);
        Class c = providers.get(type);
        if(c == null){
            throw new APIException(10001, "没有此数据库类型对应的SqlProvider：" + datasourceInfo.getDatabaseType());
        }

        ProviderName providerName = (ProviderName) c.getAnnotation(ProviderName.class);
        if (!providerName.name().equals(type)) {
            throw new APIException(500, "providerName不相等");
        }

        DataProvider provider = (DataProvider) c.newInstance();
        provider.setDatasource(datasourceInfo);

        /*if (applicationContext != null) {
            //  所以说，为啥要装载bean？
//            applicationContext.getAutowireCapableBeanFactory().autowireBean(provider);
        }*/

        return provider;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataProviderManager.applicationContext = applicationContext;
    }

    /**
     * 获取注解类型，根据数据库类型
     *
     * @param dbType 数据库类型
     * @return       注解类型
     */
    private static String getTypeByDbType(Integer dbType) {
        String type = "none";
        switch (dbType) {
            case 1:
                type = "hive";
                break;
            case 2:
                type = "postgreSql";
                break;
            default:
                break;
            /*case 0:
                type = "oracle";
                break;
            case 1:
                type = "mysqlNew";
                break;
            case 2:
                type = "mysqlOld";
                break;
            case 3:
                type = "postgreSql";
                break;
            case 4:
                type = "hive";
                break;
            case 5:
                type = "sqlserver";
                break;
            case 6:
                type = "huawei-hive";
                break;
            case 7:
                type = "gaussDB";
                break;
            default:
                break;*/
        }

        return type;
    }
}
