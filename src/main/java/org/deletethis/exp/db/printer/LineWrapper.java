package org.deletethis.exp.db.printer;

/**
 *
 * @author miko
 */
public class LineWrapper {
    final private PoliteAppendable out;
    final private int maxwidth;
    final private int indentsize;
    
    private int linesize;
    private int spaces;
    private int indent;

    public LineWrapper(PoliteAppendable out, int maxwidth, int indentsize) {
        this.out = out;
        this.maxwidth = maxwidth;
        this.indentsize = indentsize;
        this.linesize = 0;
        this.spaces = 0;
        this.indent = 0;
    }
    
    public void chunk(String chunk) {
        int len = chunk.length();
        
        if(len > maxwidth - indent) {
            throw new IllegalArgumentException("chunk too long:" + chunk);
        }
        
        if(linesize + spaces + len > maxwidth) {
            newline();
        }
        if(linesize == 0) {
            spaces = 0;
            for(;linesize < indent; ++linesize) {
                out.append(' ');
            }
        }
        
        for(;spaces > 0; --spaces) {
            out.append(' ');
            linesize++;
        }
        out.append(chunk);
        linesize+=len;
    }
    
    public void space() {
        if(linesize > 0)
            spaces = 1;
    }
    
    public void newline() {
        out.newline();
        linesize = 0;
        spaces = 0;
    }
    
    public void indent() {
        indent += indentsize;
    }
    
    public void unindent() {
        indent -= indentsize;
        if(indent < 0)
            indent = 0;
    }
    
    public int available() {
        if(linesize == 0) {
            return maxwidth - indent;
        } else {
            return maxwidth - linesize - spaces;
        }
    }
    
    public int maxline() {
        return maxwidth - indent;
    }
}
