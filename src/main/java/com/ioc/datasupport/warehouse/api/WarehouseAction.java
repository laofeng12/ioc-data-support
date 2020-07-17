package com.ioc.datasupport.warehouse.api;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ioc.datasupport.common.MbpTablePageImpl;
import com.ioc.datasupport.common.PublicConstant;
import com.ioc.datasupport.dataprovider.dto.ColumnInfo;
import com.ioc.datasupport.dataprovider.dto.TableInfo;
import com.ioc.datasupport.dataprovider.result.AggregatePageResult;
import com.ioc.datasupport.dataprovider.result.JdbcTemplateAggResult;
import com.ioc.datasupport.datalake.domain.DlRescataDatabase;
import com.ioc.datasupport.warehouse.dto.ColumnPermInfo;
import com.ioc.datasupport.warehouse.dto.QueryDataDTO;
import com.ioc.datasupport.datalake.service.DlRescataDatabaseService;
import com.ioc.datasupport.warehouse.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.ljdp.component.result.DataApiResponse;
import org.ljdp.secure.annotation.Security;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
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
@RequestMapping(PublicConstant.URL_V1  + "warehouse")
public class WarehouseAction {

    @Resource
    private DlRescataDatabaseService dlRescataDatabaseService;

    @Resource
    private WarehouseService warehouseService;

    @ApiOperation(value = "仓库列表", notes = "仓库列表", nickname = "dbInfos")
    @Security(session = false)
    @GetMapping(value = "/dbInfos")
    public DataApiResponse<DlRescataDatabase> getDbInfos(){
        List<DlRescataDatabase> rescataDataBaseList = dlRescataDatabaseService.getRescataDataBaseList();
        DataApiResponse<DlRescataDatabase> resp = new DataApiResponse<>();
        resp.setRows(rescataDataBaseList);

        return resp;
    }

    @ApiOperation(value = "仓库列表展示", notes = "仓库列表展示", nickname = "dbInfosShow")
    @Security(session = false)
    @GetMapping(value = "/dbInfosShow")
    public DataApiResponse<DlRescataDatabase> getDbInfosShow(){
        List<DlRescataDatabase> rescataDataBaseList = dlRescataDatabaseService.getRescataDataBasesShow();
        DataApiResponse<DlRescataDatabase> resp = new DataApiResponse<>();
        resp.setRows(rescataDataBaseList);

        return resp;
    }

    @ApiOperation(value = "物理表展示", notes = "物理表展示", nickname = "tableList")
    @Security(session = false)
    @GetMapping(value = "/tableList/{dbId}")
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
    @GetMapping(value = "/tablePage/{dbId}")
    public MbpTablePageImpl<TableInfo> getTableList(@PathVariable Long dbId, @ApiIgnore Page<TableInfo> page) throws Exception {
        return warehouseService.getTablePage(dbId, page);
    }

    @ApiOperation(value = "列信息", notes = "列信息", nickname = "columns")
    @Security(session = false)
    @GetMapping(value = "/columns/{dbId}/{tableName}")
    public DataApiResponse<ColumnInfo> getColumns(@PathVariable Long dbId, @PathVariable String tableName) throws Exception {
        List<ColumnInfo> columnInfos = warehouseService.getTableColumn(dbId, tableName);
        DataApiResponse<ColumnInfo> resp = new DataApiResponse<>();
        resp.setRows(columnInfos);

        return resp;
    }

    @ApiOperation(value = "表数据", notes = "表数据", nickname = "tableData")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "size", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
        @ApiImplicitParam(name = "current", value = "页码", required = false, dataType = "int", paramType = "query"),
    })
    @Security(session = false)
    @GetMapping(value = "/tableData/{dbId}/{tableName}")
    public DataApiResponse<AggregatePageResult> getTableData(@PathVariable Long dbId, @PathVariable String tableName, @ApiIgnore Page<TableInfo> page) throws Exception {
        DataApiResponse<AggregatePageResult> resp = new DataApiResponse<>();
        AggregatePageResult tableData = warehouseService.getTableData(dbId, tableName, page);
        resp.setData(tableData);

        return resp;
    }

    /*---- 实际用户查询 ----*/

    @ApiOperation(value = "用户拥有的物理表", notes = "用户拥有的物理表", nickname = "userTableList")
    @Security(session = true)
    @GetMapping(value = "/userTableList/{dbId}")
    public DataApiResponse<TableInfo> getUserTableList(@PathVariable Long dbId) throws Exception {
        List<TableInfo> tableList = warehouseService.getUserTableList(dbId);
        DataApiResponse<TableInfo> resp = new DataApiResponse<>();
        resp.setRows(tableList);

        return resp;
    }

    @ApiOperation(value = "用户列信息", notes = "用户列信息", nickname = "userColumns")
    @Security(session = true)
    @GetMapping(value = "/userColumns/{tableId}")
    public DataApiResponse<ColumnPermInfo> getUserColumns(@PathVariable Long tableId) throws Exception {
        List<ColumnPermInfo> permInfos = warehouseService.getUserTableColumn(tableId);
        DataApiResponse<ColumnPermInfo> resp = new DataApiResponse<>();
        resp.setRows(permInfos);

        return resp;
    }

    @ApiOperation(value = "查询数据", notes = "查询数据", nickname = "userColumns")
    @Security(session = true)
    @PostMapping(value = "/queryData")
    public DataApiResponse<JdbcTemplateAggResult> queryData(@Valid @RequestBody QueryDataDTO queryDataDTO) throws Exception {
        JdbcTemplateAggResult result = warehouseService.queryData(queryDataDTO);
        DataApiResponse<JdbcTemplateAggResult> resp = new DataApiResponse<>();
        resp.setData(result);

        return resp;
    }

}
