package org.deletethis.exp.db.dest;

import java.math.BigInteger;
import java.util.List;
import org.deletethis.exp.db.printer.PoliteAppendable;
import org.deletethis.exp.db.source.ColumnSchema;
import org.deletethis.exp.db.source.SourceCursor;

/**
 *
 * @author miko
 */
public interface DestinationSql {
    void printInsert(PoliteAppendable appendable, String table, 
            List<ColumnSchema> columns, SourceCursor row);
    
    void setSequenceValue(PoliteAppendable appendable, String seqenceName, BigInteger value);
}
