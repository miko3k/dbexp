package org.deletethis.exp.db.oracle;

import org.deletethis.exp.db.printer.LineWrapper;
import org.deletethis.exp.db.printer.PoliteAppendable;
import org.deletethis.exp.db.printer.PrinterBase;

/**
 *
 * @author miko
 */
public class OraclePrinter extends PrinterBase {
    private final LineWrapper out;
    private static final int INDENT_SIZE = 2;
    private boolean wasWord;
    
    public OraclePrinter(PoliteAppendable out, int maxwidth) {
        this.out = new LineWrapper(out, maxwidth, INDENT_SIZE);
        this.wasWord = false;
    }

    @Override
    protected void printAtom(String str) {
        char c = str.charAt(0);
        boolean isWord = Character.isAlphabetic(c) || Character.isDigit(c);
        
        if(isWord) {
            if(wasWord) {
                out.space();
            }
            out.chunk(str);
        } else {
            switch(str) {
                case ",": out.chunk(str); out.space(); break;
                default: out.chunk(str); break;
            }
        }
        wasWord = isWord;
    }

    @Override
    public void printQuoted(String str) {
        if(wasWord) {
            out.space();
            wasWord = false;
        }
        
        if(str.isEmpty()) {
            out.chunk("''");
            return;
        }
        
        if(str.length()+2 < out.maxline()/2) {
            // do not wrap short strings
            if(out.available() < str.length()+2) {
                out.newline();
            }
            out.chunk("'" + str + "'");
            return;
        }
        
        
        int start = 0;
        while(true) {
            int rem = str.length()-start;
            if(rem <= 0)
                return;

            if(start > 0)
                out.chunk("||");
            
            int avail = out.available();
            if(avail < 3) {
                out.newline();
                avail = out.available();
            }
            if(avail < 3) {
                throw new IllegalStateException("avail = " + avail);
            }
                
            if(rem > avail - 2) {
                rem = avail - 2;
            }

            out.chunk("'" + str.substring(start, start+rem) + "'");
            
            start += rem;
            
        }
    }

    @Override
    public void newline() {
        wasWord = false;
        out.newline();
    }

    @Override
    public void indent() {
        out.indent();
    }

    @Override
    public void unindent() {
        out.unindent();
    }
}
