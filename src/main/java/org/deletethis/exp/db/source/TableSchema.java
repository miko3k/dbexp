package org.deletethis.exp.db.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author miko
 */
public class TableSchema {
    private final String tableName;
    private final List<ColumnSchema> columns;

    public TableSchema(String tableName, List<ColumnSchema> columns) {
        this.tableName = tableName;
        this.columns = Collections.unmodifiableList(new ArrayList<>(columns));
    }

    public String getTableName() {
        return tableName;
    }

    public List<ColumnSchema> getColumns() {
        return columns;
    }
    
    @Override
    public String toString()
    {
        return tableName + columns;
    }
}
