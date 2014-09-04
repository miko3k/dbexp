package org.deletethis.exp.db.generic;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.deletethis.exp.db.source.SourceException;
import org.deletethis.exp.db.source.SourceValue;

/**
 *
 * @author miko
 */
public class SourceValueImpl implements SourceValue {
    private final int rowidx;
    private final int colidx;
    private final SourceCursorImpl parent;

    public SourceValueImpl(int rowidx, int colidx, SourceCursorImpl parent) {
        this.rowidx = rowidx;
        this.colidx = colidx;
        this.parent = parent;
    }
    
    private ResultSet rs() {
        if(parent.getRowCount() != rowidx) {
            throw new IllegalStateException("stale value!");
        }
        return parent.getResultSet();
    }
    
    @Override
    public String getString() {
        try {
            return rs().getString(colidx);
        } catch (SQLException ex) {
            throw new SourceException(ex);
        }
    }

    @Override
    public Date getDate() {
        try {
            return rs().getTimestamp(colidx);
        } catch (SQLException ex) {
            throw new SourceException(ex);
        }
    }

    @Override
    public BigDecimal getNumber() {
        try {
            return rs().getBigDecimal(colidx);
        } catch (SQLException ex) {
            throw new SourceException(ex);
        }
    }
    
    @Override
    public byte [] getBlob() {
        try {
            return rs().getBytes(colidx);
        } catch (SQLException ex) {
            throw new SourceException(ex);
        }        
    }

    @Override
    public boolean isNull() {
        try {
            return rs().getObject(colidx) == null;
        } catch (SQLException ex) {
            throw new SourceException(ex);
        }
    }
    
}
