package org.deletethis.exp.db.generic;

import java.util.List;
import org.deletethis.exp.db.printer.Printer;
import org.deletethis.exp.db.source.ColumnSchema;
import org.deletethis.exp.db.source.SourceCursor;

/**
 *
 * @author miko
 */
public class DestinationSqlUtil {
    public static void printInsert(Printer printer, LiteralPrinter literalPrinter, 
            String table, List<ColumnSchema> columns, SourceCursor cursor) {
        printer.printAtoms("insert", "into", table, "(");
        printer.indent();
        boolean first = true;
        for (ColumnSchema cs : columns) {
            if (first) {
                first = false;
            } else {
                printer.printAtoms(",");
            }
            printer.printAtoms(cs.getName());
        }
        printer.unindent();
        printer.printAtoms(")", "values", "(");
        printer.indent();
        first = true;
        for (int i = 0; i < columns.size(); ++i) {
            if (first) {
                first = false;
            } else {
                printer.printAtoms(",");
            }
            ColumnSchema c = columns.get(i);
            literalPrinter.printLiteral(printer, c.getName(), c.getType(), cursor.getColumn(i));
        }
        printer.unindent();
        printer.printAtoms(")");
    }

}
