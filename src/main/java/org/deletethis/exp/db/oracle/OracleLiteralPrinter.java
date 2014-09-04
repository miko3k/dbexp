package org.deletethis.exp.db.oracle;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.deletethis.exp.Slice;
import org.deletethis.exp.db.generic.LiteralPrinterBase;
import org.deletethis.exp.db.printer.Printer;


/**
 * OracleLiteralPrinter.
 * 
 * For BLOB columns it usually returns empty_blob(), and additional
 * methods must be used to print PL/SQL block actual blob.
 * 
 * @author miko
 */
public class OracleLiteralPrinter extends LiteralPrinterBase {
    private Map<String, byte []> blobColumns;
    
    public void startRow() { blobColumns = new HashMap<>(); }
    
    private boolean isSpecial(char c) {
        return c < 32 || c == '\'' || c == '\\' || c == '&';
    }
    
    protected void printEscapedString(Printer out, String raw)
    {
        StringBuilder sb = new StringBuilder();
        boolean hasSomething = false;
        int length = raw.length();
        
        for(int i =0; i< length; ++i) {
            char c = raw.charAt(i);
            
            if(isSpecial(c)) {
                if(sb.length() > 0) {
                    out.printQuoted(sb.toString());
                    sb.setLength(0);
                }
                if(hasSomething) {
                    out.printAtoms("||");
                }
                    
                out.printAtoms("chr", "(", String.valueOf((int)c), ")");
            } else {
                if(sb.length() == 0 && hasSomething) {
                    out.printAtoms("||");
                }
                sb.append(c);
            }
            
            hasSomething = true;
        }
        if(sb.length() > 0) {
            out.printQuoted(sb.toString());
        } else if(!hasSomething) {
            out.printQuoted("");
        }
    }
    

    @Override
    protected void printClob(Printer out, String name, String value)  {
        if(value.length() < 4000) {
            printString(out, name, value);
            return;
        }
        
        for(Slice s: Slice.create(value.length(), 4000)) {
            String substring = value.substring(s.start, s.length+s.start);
            if(s.start > 0) {
                out.printAtoms("||");
            }
            out.printAtoms("to_clob", "(");
            printEscapedString(out, substring);
            out.printAtoms(")");
        }
    }

    @Override
    protected void printString(Printer out, String name, String value) {
        printEscapedString(out, value);
    }

    @Override
    protected void printDate(Printer out, String name, Date value) {
        //'yyyymmddhhmiss';
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(value);
        
        String lit = String.format("%04d%02d%02d%02d%02d%02d",
                gc.get(Calendar.YEAR),
                gc.get(Calendar.MONTH) - Calendar.JANUARY + 1,
                gc.get(Calendar.DAY_OF_MONTH),
                gc.get(Calendar.HOUR_OF_DAY),
                gc.get(Calendar.MINUTE),
                gc.get(Calendar.SECOND));
        
        out.printAtoms("to_date", "(");
        out.printQuoted(lit);
        out.printAtoms(",");
        out.printQuoted("yyyymmddhh24miss");
        out.printAtoms(")");
    }

    @Override
    protected void printNumber(Printer out, String name, BigDecimal bd) {
        out.printAtoms(bd.toPlainString());
    }

    @Override
    protected void printBlob(Printer out, String name, byte[] blob)  {
        if(blob.length < 1900) {
            out.printQuoted(DatatypeConverter.printHexBinary(blob));
        } else {
            out.printAtoms("empty_blob()");
            blobColumns.put(name, blob);
        }
    }
    
    public Map<String, byte[]> getBlobs() { return blobColumns; }
    public boolean hasBlobs() { return !blobColumns.isEmpty(); }
}
