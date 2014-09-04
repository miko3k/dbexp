package org.deletethis.exp.db.source;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author miko
 */
public interface SourceSql {
    TableSchema getTableSchema(String table);
    SourceCursor selectWholeTable(String tableName, List<String> columns, 
            String restOfSql);
    BigInteger getSequenceValue(String sequenceName);
}
