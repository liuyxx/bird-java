package com.bird.service.common.grid.executor;

import com.bird.service.common.grid.GridClassContainer;
import com.bird.service.common.grid.GridDefinition;
import com.bird.service.common.grid.exception.GridException;
import com.bird.service.common.grid.query.PagedListQuery;
import com.bird.service.common.grid.query.PagedResult;

import java.util.Map;

/**
 * @author liuxx
 * @since 2021/1/22
 */
public class GridExecutorFactory {

    private final GridClassContainer container;
    private final Map<DialectType,IGridExecutor> gridExecutors;

    public GridExecutorFactory(GridClassContainer container,IGridExecutorLoader gridExecutorLoader) {
        this.container = container;
        this.gridExecutors = gridExecutorLoader.loadExecutors();
    }

    /**
     * 列表查询
     *
     * @param gridName 表格名称
     * @param query    查询条件
     * @return 查询结果
     */
    public PagedResult<Map<String, Object>> listPaged(String gridName, PagedListQuery query) {
        GridDefinition gridDefinition = this.container.getGridDefinition(gridName);
        IGridExecutor gridExecutor = this.gridExecutor(gridDefinition);
        return gridExecutor.listPaged(gridDefinition, query);
    }

    /**
     * 新增
     *
     * @param gridName 表格名称
     * @param model    实体内容
     * @return 主键id
     */
    public Object insert(String gridName, Map<String, Object> model) {
        GridDefinition gridDefinition = this.container.getGridDefinition(gridName);
        IGridExecutor gridExecutor = this.gridExecutor(gridDefinition);
        return gridExecutor.add(gridDefinition, model);
    }

    /**
     * 编辑
     *
     * @param gridName 表格名称
     * @param model    实体内容
     * @return 主键id
     */
    public Object update(String gridName, Map<String, Object> model) {
        GridDefinition gridDefinition = this.container.getGridDefinition(gridName);
        IGridExecutor gridExecutor = this.gridExecutor(gridDefinition);
        return gridExecutor.edit(gridDefinition, model);
    }

    /**
     * 编辑
     *
     * @param gridName 表格名称
     * @param id       主键id
     */
    public void delete(String gridName, String id) {
        GridDefinition gridDefinition = this.container.getGridDefinition(gridName);
        IGridExecutor gridExecutor = this.gridExecutor(gridDefinition);
        gridExecutor.delete(gridDefinition, id);
    }

    /**
     * 获取表格查询执行器
     *
     * @param gridDefinition 表格描述符
     * @return 执行器
     */
    private IGridExecutor gridExecutor(GridDefinition gridDefinition) {
        if (gridDefinition == null) {
            throw new GridException("未找到对应的表格定义");
        }
        DialectType dialectType = gridDefinition.getDialectType();
        IGridExecutor gridExecutor = gridExecutors.get(dialectType);
        if(gridExecutor == null){
            throw new GridException("不支持的表格处理器");
        }
        return gridExecutor;
    }
}