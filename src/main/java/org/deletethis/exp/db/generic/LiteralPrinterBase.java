package org.deletethis.exp.db.generic;

import java.math.BigDecimal;
import java.util.Date;
import org.deletethis.exp.db.printer.Printer;
import org.deletethis.exp.db.source.ColumnType;
import org.deletethis.exp.db.source.SourceValue;

/**
 *
 * @author miko
 */
public abstract class LiteralPrinterBase implements LiteralPrinter {
    abstract protected void printClob(Printer out, String name, String value);
    abstract protected void printString(Printer out, String name, String value);
    abstract protected void printDate(Printer out, String name, Date value);
    abstract protected void printNumber(Printer out, String name, BigDecimal bd);
    abstract protected void printBlob(Printer out, String name, byte[] blob);

    @Override
    public void printLiteral(Printer out, String name, ColumnType type, SourceValue o) {
        if (o.isNull()) {
            out.printAtoms("null");
            return;
        }

        switch (type) {
            case CLOB:
                printClob(out, name, o.getString());
                break;
                
            case DATE:
                printDate(out, name, o.getDate());
                break;
                
            case NUMBER:
                printNumber(out, name, o.getNumber());
                break;
                
            case STRING:
                printString(out, name, o.getString());
                break;
                
            case BLOB:
                printBlob(out, name, o.getBlob());
                break;
                
            default:
                throw new IllegalArgumentException("unsupported column: " + type);
        }
    }
}
