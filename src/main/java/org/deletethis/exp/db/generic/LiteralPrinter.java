package org.deletethis.exp.db.generic;

import org.deletethis.exp.db.printer.Printer;
import org.deletethis.exp.db.source.ColumnType;
import org.deletethis.exp.db.source.SourceValue;

/**
 *
 * @author miko
 */
public interface LiteralPrinter {
    public void printLiteral(Printer out, String colName, ColumnType type, SourceValue value);
}
