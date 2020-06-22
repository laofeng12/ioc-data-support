package com.ioc.datasupport.warehouse.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.common.MbpTablePageImpl;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
import com.ioc.datasupport.warehouse.domain.DlRescataDatabase;
import com.ioc.datasupport.warehouse.service.DlRescataDatabaseService;
import com.ioc.datasupport.warehouse.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.ljdp.component.result.DataApiResponse;
import org.ljdp.secure.annotation.Security;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lsw
 * @since 2020-06-18
 */
@Api(tags = "仓库")
@RestController
@RequestMapping("dsp/warehouse")
public class WarehouseAction {

    @Resource
    private DlRescataDatabaseService dlRescataDatabaseService;

    @Resource
    private WarehouseService warehouseService;

    @ApiOperation(value = "仓库列表", notes = "仓库列表", nickname = "dbInfos")
    @Security(session = false)
    @RequestMapping(value = "/dbInfos", method = RequestMethod.GET)
    public DataApiResponse<DlRescataDatabase> getDbInfos(){
        List<DlRescataDatabase> rescataDataBaseList = dlRescataDatabaseService.getRescataDataBaseList();
        DataApiResponse<DlRescataDatabase> resp = new DataApiResponse<>();
        resp.setRows(rescataDataBaseList);

        return resp;
    }

    @ApiOperation(value = "仓库列表展示", notes = "仓库列表展示", nickname = "dbInfosShow")
    @Security(session = false)
    @RequestMapping(value = "/dbInfosShow", method = RequestMethod.GET)
    public DataApiResponse<DlRescataDatabase> getDbInfosShow(){
        List<DlRescataDatabase> rescataDataBaseList = dlRescataDatabaseService.getRescataDataBasesShow();
        DataApiResponse<DlRescataDatabase> resp = new DataApiResponse<>();
        resp.setRows(rescataDataBaseList);

        return resp;
    }

    @ApiOperation(value = "物理表展示", notes = "物理表展示", nickname = "tableList")
    @Security(session = false)
    @RequestMapping(value = "/tableList/{dbId}", method = RequestMethod.GET)
    public DataApiResponse<TableInfo> getTableList(@PathVariable Long dbId) throws Exception {
        List<TableInfo> tableList = warehouseService.getTableList(dbId);
        DataApiResponse<TableInfo> resp = new DataApiResponse<>();
        resp.setRows(tableList);

        return resp;
    }

    @ApiOperation(value = "物理表分页", notes = "物理表分页", nickname = "tablePage")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "size", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "current", value = "页码", required = false, dataType = "int", paramType = "query"),
    })
    @Security(session = false)
    @RequestMapping(value = "/tablePage/{dbId}", method = RequestMethod.GET)
    public MbpTablePageImpl<TableInfo> getTableList(@PathVariable Long dbId, @ApiIgnore Page<TableInfo> page) throws Exception {
        return warehouseService.getTablePage(dbId, page);
    }

    // 查询物理表的数据 TODO 后期要考虑：脱敏、加密

}
