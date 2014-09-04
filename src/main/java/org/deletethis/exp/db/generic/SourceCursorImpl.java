package org.deletethis.exp.db.generic;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.deletethis.exp.db.source.SourceCursor;
import org.deletethis.exp.db.source.SourceException;
import org.deletethis.exp.db.source.SourceValue;

/**
 *
 * @author miko
 */
public class SourceCursorImpl implements SourceCursor {
    final private ResultSet resultSet;
    private int rowcount;

    public SourceCursorImpl(ResultSet resultSet) {
        this.resultSet = resultSet;
        this.rowcount = 0;
    }
    
    @Override
    public boolean next() {
        rowcount++;
        try {
            return resultSet.next();
        } catch (SQLException ex) {
            throw new SourceException(ex);
        }
    }

    @Override
    public SourceValue getColumn(int index) {
        return new SourceValueImpl(rowcount, index+1, this);
    }
    
    public int getRowCount() { return rowcount; }
    public ResultSet getResultSet() { return resultSet; }
    
}
