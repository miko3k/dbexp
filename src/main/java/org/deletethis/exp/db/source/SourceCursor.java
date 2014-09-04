package org.deletethis.exp.db.source;

/**
 *
 * @author miko
 */
public interface SourceCursor {
    boolean next();
    SourceValue getColumn(int index);
}
