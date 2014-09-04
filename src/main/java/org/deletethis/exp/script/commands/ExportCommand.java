package org.deletethis.exp.script.commands;

import java.util.ArrayList;
import java.util.List;
import org.deletethis.exp.db.source.SourceCursor;
import org.deletethis.exp.db.source.SourceSql;
import org.deletethis.exp.db.source.ColumnSchema;
import org.deletethis.exp.db.source.TableSchema;
import org.deletethis.exp.script.Command;
import org.deletethis.exp.script.ExecutionState;

/**
 *
 * @author miko
 */
public class ExportCommand implements Command {
    private final String table;
    private final String restOfSql;

    public ExportCommand(String table, String restOfSql) {
        this.table = table;
        this.restOfSql = restOfSql;
    }

    @Override
    public void execute(ExecutionState state) {
        SourceSql src = state.getSourceSql();
        
        TableSchema tableSchema = src.getTableSchema(table);
        List<String> names = new ArrayList<>();
        for(ColumnSchema cs: tableSchema.getColumns()) {
            names.add(cs.getName());
        }
        
        SourceCursor cursor = src.selectWholeTable(tableSchema.getTableName(), names, restOfSql);
        while(cursor.next()) {
            state.getDestinationSql().printInsert(state, table, tableSchema.getColumns(), cursor);
        }
    }
}        
